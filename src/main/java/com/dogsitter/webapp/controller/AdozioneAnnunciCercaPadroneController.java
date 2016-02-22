package com.dogsitter.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dogsitter.Constants;


/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
public class AdozioneAnnunciCercaPadroneController extends BaseFormController {
	
	
	
	
	@RequestMapping(value = "/adozione-annunci/{citta}/{address}/{id}", method = RequestMethod.GET)
    protected ModelAndView adozioneAnnuncioGet(final HttpServletRequest request, final HttpServletResponse response) {
		 try{
			System.out.println("sono in DogSitterAnnunciKijijiController.adozioneAnnuncioGet GET");
			String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]);
	        
	        if (getAnnunciCercaPadroneAdozioneManager().get(id) != null){
				request.setAttribute("adozioneAnnuncio", getAnnunciCercaPadroneAdozioneManager().get(id));
				
				String descrizione = StringUtils.abbreviate(getAnnunciCercaPadroneAdozioneManager().get(id).getDescrizione(), Constants.MAX_DESCRIZIONE_TAG_DESCRIPTION);
				descrizione = StringUtils.lowerCase(descrizione);
				descrizione = StringUtils.capitalize(descrizione);
				request.setAttribute("descrizioneCorta", descrizione);
	        	return new ModelAndView("adozioneAnnuncioView");
	        	
	        }else{
	        	System.out.println("adozioneAnnuncioGet Risorsa non trovata ");
	        	//response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        	//response.sendError(HttpServletResponse.SC_NOT_FOUND);
	        	//throw new ResourceNotFoundException("Risorsa non trovata");
	        	//return new ModelAndView("404");
	        	return new ModelAndView("redirect:/404");
	        }
	       
		 }catch(Exception exc){
			System.out.println("exception adozioneAnnuncioGet");
			return new ModelAndView("redirect:/home");
		 }
		

    }
	
	



}
