package com.dogsitter.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
public class DogSitterController extends BaseFormController {
	
	
	@RequestMapping(value = "/dogsitter/{citta}/{address}/{id}", method = RequestMethod.GET)
    protected ModelAndView dogSitterGet(final HttpServletRequest request, final HttpServletResponse response){
    		/*,@PathVariable("citta") String citta, 
    		@PathVariable("address") String address,
    		@PathVariable("id") long id)*/ 
		try{
			System.out.println("sono in DogSitterController.dogSitterGet GET");

			String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]);
	        
	        if (getDogSitterManager().get(id) != null){
				request.setAttribute("dogSitter", getDogSitterManager().get(id));
				
				String descrizione = StringUtils.abbreviate(getDogSitterManager().get(id).getDescrizione(), Constants.MAX_DESCRIZIONE_TAG_DESCRIPTION);
				descrizione = StringUtils.lowerCase(descrizione);
				descrizione = StringUtils.capitalize(descrizione);
				request.setAttribute("descrizioneCorta", descrizione);
				request.setAttribute("nomeCognome", getDogSitterManager().get(id).getUser().getFullName());
				request.setAttribute("indirizzoMail", getDogSitterManager().get(id).getUser().getEmail());
				long idUser = getDogSitterManager().get(id).getUser().getId();
				long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGSITTER_ROLE).getId();
				request.setAttribute("images", getImageManager().listImageUserTipoServizio(idUser, idTipo));
				request.setAttribute("imagesShareFacebook", getImageManager().getImmaginePrefeita(idUser, idTipo));
	        	return new ModelAndView("dogsitterView");
	        	
	        }else{
	        	System.out.println("DogSitterController.dogSitterGet Risorsa non trovata ");
	        	return new ModelAndView("redirect:/404");
	        }
	        
		}catch(Exception exc){
			System.out.println("exception dogSitterGet");
			return new ModelAndView("redirect:/home");
		}

    }
	
	
	/*
	@ModelAttribute("dogSitter")
    protected DogSitter loadDogSitter(final HttpServletRequest request) throws Exception {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        //String citta = parts[2];
	        //String titol = parts[3]; 
	        String id = parts[4]; 
	        if (getDogSitterManager().get(Long.parseLong(id)) != null){
	        	return getDogSitterManager().get(Long.parseLong(id));
	        }else{
	        	return null;
	        }
		}catch(Exception exc){
			System.out.println("exception dogSitter");
			return null;
		}
    }
	
	@ModelAttribute("descrizioneCorta")
    protected String descrizioneCorta(final HttpServletRequest request) throws Exception {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        //String citta = parts[2];
	        //String titol = parts[3]; 
	        String id = parts[4]; 

	        if (getDogSitterManager().get(Long.parseLong(id)) != null){
	        	String descrizione = StringUtils.abbreviate(getDogSitterManager().get(Long.parseLong(id)).getDescrizione(), Constants.MAX_DESCRIZIONE_TAG_DESCRIPTION);
				descrizione = StringUtils.lowerCase(descrizione);
				descrizione = StringUtils.capitalize(descrizione);
	        	return descrizione;
	        	
	        }else{
	        	return null;
	        }
		}catch(Exception exc){
			System.out.println("exception dogSitter");
			return null;
		}
    }
	
	@ModelAttribute("nomeCognome")
    protected String loadNomeCognome(final HttpServletRequest request) throws Exception {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        //String citta = parts[2];
	        //String titol = parts[3]; 
	        String id = parts[4]; 
	        if (getDogSitterManager().get(Long.parseLong(id)) != null){
	        	return getDogSitterManager().get(Long.parseLong(id)).getUser().getFullName();
	        }else{
	        	return null;
	        }
		}catch(Exception exc){
			System.out.println("exception nomeCognome");
			return null;
		}
    }
	
	@ModelAttribute("indirizzoMail")
    protected String indirizzoMail(final HttpServletRequest request) throws Exception {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        //String citta = parts[2];
	        //String titol = parts[3]; 
	        String id = parts[4]; 
	        if (getDogSitterManager().get(Long.parseLong(id)) != null){
	        	return getDogSitterManager().get(Long.parseLong(id)).getUser().getEmail();
	        }else{
	        	return null;
	        }
		}catch(Exception exc){
			System.out.println("exception indirizzoMail");
			return null;
		}
    }

	@ModelAttribute("images")
    protected List<Image> loadImages(final HttpServletRequest request) throws Exception {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        //String citta = parts[2];
	        //String titol = parts[3]; 
	        String id = parts[4];
	        if (getDogSitterManager().get(Long.parseLong(id)) != null){
	        	long idUser = getDogSitterManager().get(Long.parseLong(id)).getUser().getId();
	        	long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGSITTER_ROLE).getId();
	            return getImageManager().listImageUserTipoServizio(idUser, idTipo);
	        }else{
	        	return null;
	        }
		}catch(Exception exc){
			System.out.println("exception images");
			return null;
		}
    }
	*/


}
