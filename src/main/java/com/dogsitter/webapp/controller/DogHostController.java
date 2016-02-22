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
public class DogHostController extends BaseFormController {
	

	@RequestMapping(value = "/doghost/{citta}/{address}/{id}", method = RequestMethod.GET)
    protected ModelAndView dogHostGet(final HttpServletRequest request, final HttpServletResponse response){
		try{
			System.out.println("sono in DogHostController.dogHostGet GET");
			
			String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]);
	        
	    	if (getDogHostManager().get(id) != null){
				request.setAttribute("dogHost", getDogHostManager().get(id));
				String descrizione = StringUtils.abbreviate(getDogHostManager().get(id).getDescrizione(), Constants.MAX_DESCRIZIONE_TAG_DESCRIPTION);
				descrizione = StringUtils.lowerCase(descrizione);
				descrizione = StringUtils.capitalize(descrizione);
				request.setAttribute("descrizioneCorta", descrizione);
				request.setAttribute("nomeCognome", getDogHostManager().get(id).getUser().getFullName());
				request.setAttribute("indirizzoMail", getDogHostManager().get(id).getUser().getEmail());
				long idUser = getDogHostManager().get(id).getUser().getId();
				long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGHOST_ROLE).getId();
				request.setAttribute("images", getImageManager().listImageUserTipoServizio(idUser, idTipo));
				request.setAttribute("imagesShareFacebook", getImageManager().getImmaginePrefeita(idUser, idTipo));
	        	return new ModelAndView("doghostView");
	        }else{
	        	System.out.println("DogHostController.dogHostGet Risorsa non trovata ");
	        	return new ModelAndView("redirect:/404");
	        }
		}catch(Exception exc){
			return new ModelAndView("redirect:/home");
		}
    }
	
	
	
	/*
	@ModelAttribute("dogHost")
    protected DogHost loadDogHost(final HttpServletRequest request) {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        String id = parts[4]; 
	
	        if (getDogHostManager().get(Long.parseLong(id)) != null){
	        	return getDogHostManager().get(Long.parseLong(id));
	        }else{
	        	return null;
	        }
		}catch(Exception exc){
			return null;
		}
    }
	
	@ModelAttribute("descrizioneCorta")
    protected String descrizioneCorta(final HttpServletRequest request) throws Exception {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        String id = parts[4]; 

	        if (getDogHostManager().get(Long.parseLong(id)) != null){
	        	String descrizione = StringUtils.abbreviate(getDogHostManager().get(Long.parseLong(id)).getDescrizione(), Constants.MAX_DESCRIZIONE_TAG_DESCRIPTION);
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
    protected String loadNomeCognome(final HttpServletRequest request) {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        String id = parts[4]; 
	
	        if (getDogHostManager().get(Long.parseLong(id)) != null){
	        	return getDogHostManager().get(Long.parseLong(id)).getUser().getFullName();
	        }else{
	        	return null;
	        }
		}catch(Exception exc){
			return null;
		}
    }
	
	@ModelAttribute("indirizzoMail")
    protected String indirizzoMail(final HttpServletRequest request) {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        String id = parts[4]; 
	        
	        if (getDogHostManager().get(Long.parseLong(id)) != null){
	        	return getDogHostManager().get(Long.parseLong(id)).getUser().getEmail();
	        }else{
	        	return null;
	        }
		}catch(Exception exc){
			return null;
		}

    }

	@ModelAttribute("images")
    protected List<Image> loadImages(final HttpServletRequest request) {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        String id = parts[4]; 
	        
	        if (getDogHostManager().get(Long.parseLong(id)) != null){
	        	long idUser = getDogHostManager().get(Long.parseLong(id)).getUser().getId();
	        	long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGHOST_ROLE).getId();
	            return getImageManager().listImageUserTipoServizio(idUser, idTipo);
	        }else{
	        	return null;
	        }
	    }catch(Exception exc){
			return null;
		}
    }
	*/

}