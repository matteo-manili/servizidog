package com.dogsitter.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dogsitter.Constants;


/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
public class DogSitterAnnunciKijijiController extends BaseFormController {
	
	
	@RequestMapping(value = "/dogsitter-annunci/{citta}/{address}/{id}", method = RequestMethod.GET)
    protected ModelAndView dogSitterGet(final HttpServletRequest request, final HttpServletResponse response){
    		/*,@PathVariable("citta") String citta, 
    		@PathVariable("address") String address,
    		@PathVariable("id") long id)*/
		try{
			System.out.println("sono in DogSitterAnnunciKijijiController.dogSitterGet GET");
			String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]);
	        
	        if (getAnnunciKijijiDogSitterManager().get(id) != null){
				request.setAttribute("dogSitterAnnuncio", getAnnunciKijijiDogSitterManager().get(id));
				String descrizione = StringUtils.abbreviate(getAnnunciKijijiDogSitterManager().get(id).getDescrizione(), Constants.MAX_DESCRIZIONE_TAG_DESCRIPTION);
				descrizione = StringUtils.lowerCase(descrizione);
				descrizione = StringUtils.capitalize(descrizione);
				request.setAttribute("descrizioneCorta", descrizione);
	        	return new ModelAndView("dogsitterAnnuncioView");
	        }else{
	        	System.out.println("DogSitterAnnunciKijijiController.dogSitterGet Risorsa non trovata ");
	        	return new ModelAndView("redirect:/404");
	        }
	        
		}catch(Exception exc){
			System.out.println("exception dogSitterGet");
			return new ModelAndView("redirect:/home");
		}

    }
	
	



}
