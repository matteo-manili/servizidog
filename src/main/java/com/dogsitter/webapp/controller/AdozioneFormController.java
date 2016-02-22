package com.dogsitter.webapp.controller;

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

import com.dogsitter.model.Adozione;
import com.dogsitter.model.User;
import com.dogsitter.service.CaneManager;
import com.dogsitter.service.CreaSitemapManager;
import com.dogsitter.util.CaneListContainer;
import com.github.slugify.Slugify;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
public class AdozioneFormController extends BaseFormController {
	
	
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

	public AdozioneFormController() {
		setCancelView("redirect:/home-utente");
        setSuccessView("redirect:/adozioneform"); 
        setSuccessCaniAdozione("redirect:/adozioneform#inserisciCane");
	}

	@ModelAttribute("adozione")
    protected Adozione loadAdozione(final HttpServletRequest request) {
		System.out.println("sono in AdozioneFormController.loadAdozione");

		User user = getUserManager().getUserByUsername(request.getRemoteUser());
		return this.getAdozioneManager().getAdozioneByUser(user.getId());
    }
	
	@ModelAttribute("caneListContainer")
    protected CaneListContainer loadCane(final HttpServletRequest request) {
		System.out.println("sono in AdozioneFormController.loadCane");
		
		User user = getUserManager().getUserByUsername(request.getRemoteUser());
		Adozione adozione = getAdozioneManager().getAdozioneByUser(user.getId());
		CaneListContainer clc = new CaneListContainer(caneManager.getCaneByAdozione(adozione.getId()));
		
		return clc;
    }
	
	
	@RequestMapping(value="/adozioneCaneform", method= RequestMethod.POST)
    public String editcaneListContainer(
    		@ModelAttribute("caneListContainer") CaneListContainer caneListContainer, 
    		@ModelAttribute("adozione") final Adozione adozioneMod,
    		final HttpServletRequest request, ModelMap map, HttpSession session) {
		
		System.out.println("sono in AdozioneFormController.editcaneListContainer POST");
		final Locale locale = request.getLocale();
		
		try{
			if(caneListContainer == null){
				System.out.println("canelist container is NULL");
			}
			User user = getUserManager().getUserByUsername(request.getRemoteUser());
			Adozione adozione = getAdozioneManager().getAdozioneByUser(user.getId());
	
			int numeroCani = caneManager.salvaCani(caneListContainer, adozione);
	        
	        //salvo la adiozione e aggiorno la ultima modifica per la Sitemap.xml
			adozioneMod.setUltimaModifica(new Date());

			getAdozioneManager().saveAdozione(adozioneMod);

			//rigenero la Sitemap.xml
	        //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
	        
	
	        if (numeroCani > 1)
	        	saveMessage(request, "Cani salvati con successo");
	        	
	        if (numeroCani == 1)
	        	saveMessage(request, "Cane salvato con successo");
	
	        if (numeroCani == 0)
	        	saveMessage(request, getText("user.saved", locale));
	        
	
	        return getSuccessCaniAdozione();
	        
		}catch (Exception e) {
	 		e.printStackTrace();
	 		saveError(request, getText("errors.save", locale));
	 		return getSuccessCaniAdozione();
	     }
    }
	

	@RequestMapping(value = "/adozioneform", method = RequestMethod.POST)
    public String onSubmit(
    		@ModelAttribute("adozione") final Adozione adozioneMod, 
    		final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response, HttpSession session){
		System.out.println("sono in AdozioneFormController.onSubmit POST");

        if (request.getParameter("cancel") != null) {
                return getCancelView();
        }
       
		if (validator != null) { // validator is null during testing
    		validator.validate(adozioneMod, errors);
            if (errors.hasErrors() ) { // don't validate when deleting
            	System.out.println("validazione errata");
            	return "adozioneform"; 
            }
        }
        
        final Locale locale = request.getLocale();
    	try {
    		
    		//User user = getUserManager().getUserByUsername(request.getRemoteUser());

    		//Adozione adozione = getAdozioneManager().getAdozioneByUser(user.getId());
    		//salvo gli oggetti gestiti fuori dal form, con ajax
    		//vedremo quali sono......
    		
    		//adozioneMod.setUser(user);
    		
    		//creo la url profilo in base all'indirizzo
    		Slugify slugify = new Slugify();
    		String urlSlug = null;
        	urlSlug = slugify.slugify("adozione") +"/"+
        				slugify.slugify(adozioneMod.getAddress().getCity()) +"/"+ 
        				slugify.slugify(adozioneMod.getAddress().getAddress()) +"/"+ 
        				slugify.slugify(adozioneMod.getId().toString());
    		adozioneMod.setUrlProfilo(urlSlug);
    		
    		/*
    		String telefono = adozioneMod.getTelefono();
    		telefono = String.format("%s.%s.%s.%s", 
    				telefono.substring(0, 3), 
    				telefono.substring(3, 5),
    				telefono.substring(5, 7),
    				telefono.substring(7, 10));
    		adozioneMod.setTelefono(telefono);
    		*/
    		//salvo la adiozione e aggiorno la ultima modifica per la Sitemap.xml
    		adozioneMod.setUltimaModifica(new Date());
    		
    		getAdozioneManager().saveAdozione(adozioneMod);
    		//rigenero la Sitemap.xml
            //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
        	} 
    	
	        catch (final Exception e) {
        		e.printStackTrace();
                return "adozioneform";
            }
    	
    	saveMessage(request, getText("user.saved", locale));
    	
        return getSuccessView();
    }
	
	

	@RequestMapping(value = "/adozioneform", method = RequestMethod.GET)
    protected ModelAndView showForm(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap)
            throws Exception {
    	System.out.println("sono in AdozioneFormController.showForm GET");
    	final Locale locale = request.getLocale();
    	try{
    		if(request.getRemoteUser() != null){
		    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
		    	request.setAttribute("peloList", MapPeloList(locale));
		    	request.setAttribute("sessoList", MapSessoList(locale));
		    	request.setAttribute("tagliaList", MapTagliaList(locale));
		        return new ModelAndView("adozioneform");
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
