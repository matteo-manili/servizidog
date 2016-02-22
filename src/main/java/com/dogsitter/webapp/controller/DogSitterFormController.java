package com.dogsitter.webapp.controller;



import java.util.Date;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dogsitter.Constants;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.User;
import com.dogsitter.service.CreaSitemapManager;
import com.dogsitter.webapp.util.CreaUrlSlugify;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/dogsitterform*") //la dogsitterform.jsp
public class DogSitterFormController extends BaseFormController {
	
	
	private CreaSitemapManager creaSitemapManager;
	
	@Autowired
	public void setCreaSitemapManager(CreaSitemapManager creaSitemapManager) {
		this.creaSitemapManager = creaSitemapManager;
	}


	public DogSitterFormController() {
		setCancelView("redirect:/home-utente");
        setSuccessView("redirect:/home-utente");
	}

	
	@RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("dogSitter") final DogSitter dogSitterMod, final BindingResult errors, 
    		final HttpServletRequest request, final HttpServletResponse response){
		System.out.println("DogSitterFormController POST");
		final Locale locale = request.getLocale();
		
		try {
	        if (request.getParameter("cancel") != null) {
	                return getCancelView();
	        }
	
			if (validator != null) { // validator is null during testing
	    		validator.validate(dogSitterMod, errors);
	            if (errors.hasErrors() ) { // don't validate when deleting
	            	System.out.println("validazione errata");
	            	return "dogsitterform";
	            }
	        }
        
    		User user = getUserManager().getUserByUsername(request.getRemoteUser());
    		DogSitter dogSitter = getDogSitterManager().getDogSitterByUser(user.getId());
    		//salvo gli oggetti gestiti fuori dal form, con ajax
    		dogSitterMod.setDataInizioDisponib(dogSitter.getDataInizioDisponib());
    		dogSitterMod.setDataFineDisponib(dogSitter.getDataFineDisponib());
    		
    		if (dogSitter.getTariffa() == null || dogSitter.getTariffa().equals("")){
    			dogSitterMod.setTariffa(Constants.PREZZO_MEDIO_DOGSITTER);
    		}else{
    			dogSitterMod.setTariffa(dogSitter.getTariffa());
    		}
    		
    		dogSitterMod.setUser(user);
    		dogSitterMod.setTitolo(user.getFirstName());
    		
    		//creo la url profilo in base al titolo
    		dogSitterMod.setUrlProfilo( CreaUrlSlugify.creaUrl("dogsitter", dogSitterMod.getAddress(), dogSitterMod.getId()));
    		
    		dogSitterMod.setUltimaModifica(new Date());
    		getDogSitterManager().saveDogSitter(dogSitterMod);
    		
    		//creo la Sitemap.xml
            //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
            
        	saveMessage(request, getText("user.saved", locale));
            return "dogsitterform";
            
    	}catch (final Exception e) {
    		e.printStackTrace();
    		saveError(request, getText("errors.save", locale));
            return "dogsitterform";
        }
    }
	


    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView showForm(final HttpServletRequest request, final HttpServletResponse response) {
    	System.out.println("DogSitterFormController GET");
    	final Locale locale = request.getLocale();
    	try{
    		if(request.getRemoteUser() != null){
		    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
		    	request.setAttribute("dogSitter", getDogSitterManager().getDogSitterByUser(user.getId()));
		        return new ModelAndView("dogsitterform");
    		}else{
    			return new ModelAndView("redirect:/login");
    		}
    	}catch(Exception exc){
    		exc.printStackTrace();
    		saveError(request, getText("errors.save", locale));
    		return new ModelAndView("dogsitterform");
    	}
    }
    
    


}
