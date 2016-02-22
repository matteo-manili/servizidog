package com.dogsitter.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dogsitter.Constants;
import com.dogsitter.service.CaneManager;
import com.dogsitter.util.CaneListContainer;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@Controller
public class AssociazioneController extends BaseFormController {

	
	private CaneManager caneManager;
	@Autowired
	public void setCaneManager(CaneManager caneManager) {
		this.caneManager = caneManager;
	}
	
	
	@RequestMapping(value = "/associazione/{citta}/{titolo}/{id}", method = RequestMethod.GET)
    protected ModelAndView associazioneGet(final HttpServletRequest request, final HttpServletResponse response) {
		try{
			System.out.println("sono in AssociazioneController.associazioneGet GET");
			
			String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]);
			
	    	if (getAssociazioneManager().get(id) != null){
				String descrizione = StringUtils.abbreviate(getAssociazioneManager().get(id).getDescrizione(), Constants.MAX_DESCRIZIONE_TAG_DESCRIPTION);
				descrizione = StringUtils.lowerCase(descrizione);
				descrizione = StringUtils.capitalize(descrizione);
				request.setAttribute("descrizioneCorta", descrizione);
				request.setAttribute("nomeCognome", getAssociazioneManager().get(id).getUser().getFullName());
				getAssociazioneManager().get(id).getUser().getEmail();
				request.setAttribute("indirizzoMail", getAssociazioneManager().get(id).getUser().getEmail());
				request.setAttribute("associazione", getAssociazioneManager().get(id));
				request.setAttribute("caneListContainer", new CaneListContainer(caneManager.getCaneByAssociazione(id)));
				long idUser = getAssociazioneManager().get(id).getUser().getId();
				long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ASSOCIAZIONE_ROLE).getId();
				request.setAttribute("images", getImageManager().listImageUserTipoServizio(idUser, idTipo));
				request.setAttribute("imagesShareFacebook", getImageManager().getImmaginePrefeita(idUser, idTipo));
	        	return new ModelAndView("associazioneView");
	        }else{
	        	System.out.println("AssociazioneController.associazioneGet GET Risorsa non trovata ");
	        	return new ModelAndView("redirect:/404");
	        }
	    	
		}catch(Exception exc){
			System.out.println("exception AssociazioneController associazioneGet GET");
			return new ModelAndView("redirect:/home");
		}
    }
	
	
	
	/*
	@ModelAttribute("descrizioneCorta")
    protected String descrizioneCorta(final HttpServletRequest request) throws Exception {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        String id = parts[4]; 

	        if (getAssociazioneManager().get(Long.parseLong(id)) != null){
	        	String descrizione = StringUtils.abbreviate(getAssociazioneManager().get(Long.parseLong(id)).getDescrizione(), Constants.MAX_DESCRIZIONE_TAG_DESCRIPTION);
				descrizione = StringUtils.lowerCase(descrizione);
				descrizione = StringUtils.capitalize(descrizione);
	        	return descrizione;
	        	
	        }else{
	        	return null;
	        }
		}catch(Exception exc){
			System.out.println("exception AssociazioneController descrizioneCorta");
			exc.printStackTrace();
			return null;
		}
    }
	
	@ModelAttribute("nomeCognome")
    protected String loadNomeCognome(final HttpServletRequest request) {
		try{
        String string = request.getPathInfo();
        String[] parts = string.split("/");
        String id = parts[4]; 
        if (getAssociazioneManager().get(Long.parseLong(id)) != null){
        	return getAssociazioneManager().get(Long.parseLong(id)).getUser().getFullName();
        }else{
        	return null;
        }
	  }catch(Exception exc){
		  	System.out.println("exception AssociazioneController nomeCognome");
		  	exc.printStackTrace();
			return null;
		}
    }
	
	@ModelAttribute("indirizzoMail")
    protected String indirizzoMail(final HttpServletRequest request) {
		try{
        String string = request.getPathInfo();
        String[] parts = string.split("/");
        String id = parts[4]; 
        if (getAssociazioneManager().get(Long.parseLong(id)) != null){
        	return getAssociazioneManager().get(Long.parseLong(id)).getUser().getEmail();
        }else{
        	return null;
        }
	  }catch(Exception exc){
		  	System.out.println("exception AssociazioneController indirizzoMail");
		  	exc.printStackTrace();
			return null;
		}
        
    }
	
	@ModelAttribute("associazione")
    protected Associazione loadAssociazione(final HttpServletRequest request) {
		try{
        String string = request.getPathInfo();
        String[] parts = string.split("/");
        String id = parts[4]; 
        if (getAssociazioneManager().get(Long.parseLong(id)) != null){
        	return getAssociazioneManager().get(Long.parseLong(id));
        }else{
        	return null;
        }
	  }catch(Exception exc){
		  System.out.println("exception AssociazioneController associazione");
		  exc.printStackTrace();
		  return null;
		}
    }
	
	@ModelAttribute("caneListContainer")
    protected CaneListContainer loadCane(final HttpServletRequest request) {
		try{
		String string = request.getPathInfo();
        String[] parts = string.split("/");
        String id = parts[4]; 
        if (getAssociazioneManager().get(Long.parseLong(id)) != null){
        	CaneListContainer clc = new CaneListContainer(caneManager.getCaneByAssociazione(Long.parseLong(id)));
    		return clc;
        }else{
        	return null;
        }
	  }catch(Exception exc){
		  System.out.println("exception AssociazioneController caneListContainer");
		  exc.printStackTrace();
		  return null;
		}
    }
	
	@ModelAttribute("images")
    protected List<Image> loadImages(final HttpServletRequest request) {
		try{
	        String stringPathInfo = request.getPathInfo();
	        String[] parts = stringPathInfo.split("/");
	        String id = parts[4]; 
	        if (getAssociazioneManager().get(Long.parseLong(id)) != null){
	        	long idUser = getAssociazioneManager().get(Long.parseLong(id)).getUser().getId();
	        	long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ASSOCIAZIONE_ROLE).getId();
	        	
	            return getImageManager().listImageUserTipoServizio(idUser, idTipo);
	        }else{
	        	return null;
	        }
	    }catch(Exception exc){
	    	System.out.println("exception AssociazioneController images");
	    	exc.printStackTrace();
			return null;
		}
    }
    */
    
    
}
