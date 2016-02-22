package com.dogsitter.webapp.controller;


import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dogsitter.Constants;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.User;
import com.dogsitter.service.CreaSitemapManager;
import com.dogsitter.webapp.util.CreaUrlSlugify;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/doghostform*") //la doghostform.jsp
public class DogHostFormController extends BaseFormController {

	
	private CreaSitemapManager creaSitemapManager;
	
	@Autowired
	public void setCreaSitemapManager(CreaSitemapManager creaSitemapManager) {
		this.creaSitemapManager = creaSitemapManager;
	}
	
	public DogHostFormController() {
		setCancelView("redirect:/home-utente");
        setSuccessView("redirect:/home-utente");
	}

	
	@RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("dogHost") final DogHost dogHostMod, final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response) {
		System.out.println("DogHostFormController POST");
		final Locale locale = request.getLocale();

        if (validator != null) { // validator is null during testing
    		validator.validate(dogHostMod, errors);
            if (errors.hasErrors() ) { // don't validate when deleting
            	System.out.println("validazione errata");
            	return "doghostform";
            }
        }

    	try {

			User user = getUserManager().getUserByUsername(request.getRemoteUser());
			
			DogHost dogHost = getDogHostManager().getDogHostByUser(user.getId());
			//salvo gli oggetti gestiti fuori dal form, con ajax
			dogHostMod.setDataInizioDisponib(dogHost.getDataInizioDisponib());
			dogHostMod.setDataFineDisponib(dogHost.getDataFineDisponib());
			dogHostMod.setTariffa(dogHost.getTariffa());
			if (dogHost.getTariffa() == null || dogHost.getTariffa().equals("")){
				dogHostMod.setTariffa(Constants.PREZZO_MEDIO_DOGHOST);
    		}else{
    			dogHostMod.setTariffa(dogHost.getTariffa());
    		}
			
			dogHostMod.setMetriQuadrati(dogHost.getMetriQuadrati()); 
			dogHostMod.setTerrazza(dogHost.isTerrazza());
			dogHostMod.setGiardino(dogHost.isGiardino());
			dogHostMod.setAnimaliPresenti(dogHost.isAnimaliPresenti());
			
			dogHostMod.setUser(user);
			dogHostMod.setTitolo(user.getFirstName());
			
			//creo la url profilo in base al titolo
			dogHostMod.setUrlProfilo(CreaUrlSlugify.creaUrl("doghost", dogHostMod.getAddress(), dogHostMod.getId()));
			
			dogHostMod.setUltimaModifica(new Date());
			getDogHostManager().saveDogHost(dogHostMod);
			
			//creo la Sitemap.xml
	        //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
	        
	    	saveMessage(request, getText("user.saved", locale)); 
	        return "doghostform";
		
    	}catch (final Exception e) {
     		e.printStackTrace();
     		saveError(request, getText("errors.save", locale));
            return "doghostform";
         }


    }
	
	
	@RequestMapping(method = RequestMethod.GET)
    protected ModelAndView showForm(final HttpServletRequest request, final HttpServletResponse response) {
    	System.out.println("DogHostFormController GET");
    	final Locale locale = request.getLocale();
    	try{
    		if(request.getRemoteUser() != null){
		    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
		    	request.setAttribute("dogHost", getDogHostManager().getDogHostByUser(user.getId()));
		        return new ModelAndView("doghostform");
    		}else{
    			return new ModelAndView("redirect:/login");
    		}
	        
    	}catch(Exception exc){
    		exc.printStackTrace();
    		saveError(request, getText("errors.save", locale));
    		return new ModelAndView("doghostform");
    	}
    }
	
	

}
