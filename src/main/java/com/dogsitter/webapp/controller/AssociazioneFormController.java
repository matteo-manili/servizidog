package com.dogsitter.webapp.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dogsitter.model.Associazione;
import com.dogsitter.model.Cane;
import com.dogsitter.model.User;
import com.dogsitter.service.CaneManager;
import com.dogsitter.service.CreaSitemapManager;
import com.dogsitter.util.CaneListContainer;
import com.dogsitter.webapp.util.CreaUrlSlugify;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
public class AssociazioneFormController extends BaseFormController {
	
	
	
	
	private CreaSitemapManager creaSitemapManager;
	@Autowired
	public void setCreaSitemapManager(CreaSitemapManager creaSitemapManager) {
		this.creaSitemapManager = creaSitemapManager;
	}
	
	private CaneManager caneManager;
	@Autowired
	public void setCaneManager(CaneManager caneManager) {
		this.caneManager = caneManager;
	}

	public AssociazioneFormController() {
		setCancelView("redirect:/home-utente");
        setSuccessView("redirect:/associazioneform"); 
        setSuccessCaniAssociazione("redirect:/associazioneform#inserisciCane");
	}
	
	
	@ModelAttribute("associazione")
    protected Associazione loadAssociazione(final HttpServletRequest request) {
		User user = getUserManager().getUserByUsername(request.getRemoteUser());
		return this.getAssociazioneManager().getAssociazioneByUser(user.getId());
    }
	
	@ModelAttribute("caneListContainer")
    protected CaneListContainer loadCane(final HttpServletRequest request) {
		User user = getUserManager().getUserByUsername(request.getRemoteUser());
		Associazione associazione = getAssociazioneManager().getAssociazioneByUser(user.getId());
		CaneListContainer clc;
		try {
			clc = new CaneListContainer(caneManager.getCaneByAssociazione(associazione.getId()));
			return clc;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return clc = new CaneListContainer(new ArrayList<Cane>());
		}
    }
	
	
	@RequestMapping(value="/associazioneCaneform", method= RequestMethod.POST)
    public String editcaneListContainer(
    		@ModelAttribute("caneListContainer") CaneListContainer caneListContainer, 
    		@ModelAttribute("associazione") final Associazione associazioneMod,
    		final HttpServletRequest request, ModelMap map, HttpSession session) {
		
		System.out.println("AssociazioneFormController (associazioneCaneform) POST");
		final Locale locale = request.getLocale();
		
		try{
			if(caneListContainer == null){
				System.out.println("canelist container is NULL");
			}
			User user = getUserManager().getUserByUsername(request.getRemoteUser());
			Associazione associazione = getAssociazioneManager().getAssociazioneByUser(user.getId());

			int numeroCani = caneManager.salvaCani(caneListContainer, associazione);
	        
	        //salvo la adiozione e aggiorno la ultima modifica per la Sitemap.xml
			associazioneMod.setUltimaModifica(new Date());
			getAssociazioneManager().saveAssociazione(associazioneMod);
			//rigenero la Sitemap.xml
	        //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
	        
	        if (numeroCani > 1)
	        	saveMessage(request, "Cani salvati con successo");
	        	
	        if (numeroCani == 1)
	        	saveMessage(request, "Cane salvato con successo");
	
	        if (numeroCani == 0)
	        	saveMessage(request, getText("user.saved", locale));
	        
	
	        return getSuccessCaniAssociazione();
		}catch (Exception e) {
     		e.printStackTrace();
     		saveError(request, getText("errors.save", locale));
     		return getSuccessCaniAssociazione();
         }

    }

	
	@RequestMapping(value = "/associazioneform", method = RequestMethod.POST)
    public String onSubmit(
    		@ModelAttribute("associazione") final Associazione associazioneMod, 
    		final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response, HttpSession session){
		System.out.println("AssociazioneFormController POST");

        if (request.getParameter("cancel") != null) {
                return getCancelView();
        }
       
		if (validator != null) { // validator is null during testing
    		validator.validate(associazioneMod, errors);
            if (errors.hasErrors() ) { // don't validate when deleting
            	System.out.println("validazione errata");
            	//return "associazioneform";
            	return getSuccessView();
            }
        }
        
        final Locale locale = request.getLocale();
    	try {
    		
    		//URI uri = new URI(associazioneMod.getWebsite());
    		//associazioneMod.setWebsite(uri.getHost()+uri.getPath()+uri.getQuery());
    		
    		/*
    		System.out.println(uri.getAuthority());
    		System.out.println(uri.getFragment());
    		System.out.println(uri.getHost()+uri.getPath());
    		System.out.println(uri.getPath());
    		System.out.println(uri.getQuery());
    		System.out.println(uri.getRawAuthority());
    		System.out.println(uri.getRawFragment());
    		System.out.println(uri.getRawPath());
    		System.out.println(uri.getRawQuery());
    		System.out.println(uri.getRawUserInfo());
    		 */
    		
    		//creo la url profilo in base all'indirizzo
    		associazioneMod.setUrlProfilo( CreaUrlSlugify.creaUrl("associazione", associazioneMod.getAddress(), associazioneMod.getId()));
    		
    		//salvo la adiozione e aggiorno la ultima modifica per la Sitemap.xml
    		associazioneMod.setUltimaModifica(new Date());
    		getAssociazioneManager().saveAssociazione(associazioneMod);
    		
    		//rigenero la Sitemap.xml
            //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
            
            saveMessage(request, getText("user.saved", locale));
            
            return getSuccessView();
            
    	}catch (URISyntaxException e1){
    		e1.printStackTrace();
    		saveError(request, getText("errors.save", locale));
     		return getSuccessView();
    	}
    	catch (final Exception e) {
     		e.printStackTrace();
     		saveError(request, getText("errors.save", locale));
     		return getSuccessView();
         }
    }
	
	
	
	/*
	@ModelAttribute("sessoList")
	protected Map MapSessoList(HttpServletRequest request) {
		Locale locale = request.getLocale();
		return this.MapSessoList(locale);
	}
	
	@ModelAttribute("tagliaList")
	protected Map MapTagliaList(HttpServletRequest request) {
		Locale locale = request.getLocale();
		return this.MapTagliaList(locale);
	}
	
	@ModelAttribute("peloList")
	protected Map MapPeloList(HttpServletRequest request) {
		Locale locale = request.getLocale();
		return this.MapPeloList(locale);
	}
	*/
	

	@RequestMapping(value = "/associazioneform", method = RequestMethod.GET)
    protected ModelAndView showForm(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap)
            throws Exception {
    	System.out.println("AssociazioneFormController GET");
    	final Locale locale = request.getLocale();
    	try{
    		if(request.getRemoteUser() != null){
		    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
		    	request.setAttribute("peloList", MapPeloList(locale));
		    	request.setAttribute("sessoList", MapSessoList(locale));
		    	request.setAttribute("tagliaList", MapTagliaList(locale));
		        return new ModelAndView("associazioneform");
    		}else{
    			return new ModelAndView("redirect:/login");
    		}
	        
    	}catch(Exception exc){
    		exc.printStackTrace();
    		saveError(request, getText("errors.save", locale));
    		return new ModelAndView("redirect:/home-utente");
    	}
    }
	
	


}
