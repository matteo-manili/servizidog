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
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
public class AdozioneController extends BaseFormController {
	
	
	private CaneManager caneManager;
	@Autowired
	public void setCaneManager(CaneManager caneManager) {
		this.caneManager = caneManager;
	}
	
	
	@RequestMapping(value = "/adozione/{citta}/{titolo}/{id}", method = RequestMethod.GET)
    protected ModelAndView adozioneGet(final HttpServletRequest request, final HttpServletResponse response){
		try{
			System.out.println("sono in AdozioneController.adozioneGet GET");
			
			String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]);
			
	    	if (getAdozioneManager().get(id) != null){
				String descrizione = StringUtils.abbreviate(getAdozioneManager().get(id).getDescrizione(), Constants.MAX_DESCRIZIONE_TAG_DESCRIPTION);
				descrizione = StringUtils.lowerCase(descrizione);
				descrizione = StringUtils.capitalize(descrizione);
				request.setAttribute("descrizioneCorta", descrizione);
				request.setAttribute("nomeCognome", getAdozioneManager().get(id).getUser().getFullName());
				request.setAttribute("indirizzoMail", getAdozioneManager().get(id).getUser().getEmail());
				request.setAttribute("adozione", getAdozioneManager().get(id));
				CaneListContainer clc = new CaneListContainer(caneManager.getCaneByAdozione(id));
				request.setAttribute("caneListContainer", clc);
				long idUser = getAdozioneManager().get(id).getUser().getId();
				long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ADOZIONE_ROLE).getId();
				request.setAttribute("images", getImageManager().listImageUserTipoServizio(idUser, idTipo));
				request.setAttribute("imagesShareFacebook", getImageManager().getImmaginePrefeita(idUser, idTipo));
				return new ModelAndView("adozioneView");
	        	
	        }else{
	        	System.out.println("AdozioneController.adozioneGet Risorsa non trovata ");
	        	return new ModelAndView("redirect:/404");
	        }
	    	
		}catch (NumberFormatException nfe){
			System.out.println("NumberFormatException AdozioneController.adozioneGet"); 
			return new ModelAndView("redirect:/home");
			
		}catch(Exception exc){
			System.out.println("Exception adozioneGet");
			return new ModelAndView("redirect:/home");
		}

    }
	
	
	
	/*
	@ModelAttribute("descrizioneCorta")
    protected String descrizioneCorta(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]); 

	        if (getAdozioneManager().get(id) != null){
	        	String descrizione = StringUtils.abbreviate(getAdozioneManager().get(id).getDescrizione(), Constants.MAX_DESCRIZIONE_TAG_DESCRIPTION);
				descrizione = StringUtils.lowerCase(descrizione);
				descrizione = StringUtils.capitalize(descrizione);
	        	return descrizione;
	        	
	        }else{
	        	return null;
	        }
		}catch (NumberFormatException nfe){
			System.out.println("NumberFormatException AdozioneController.descrizioneCorta"); 
			//nfe.printStackTrace();
			response.setContentType("text/html");
			return null;
	    }catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }
	

	@ModelAttribute("nomeCognome")
    protected String loadNomeCognome(final HttpServletRequest request, final HttpServletResponse response) {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]);  
	    	if (getAdozioneManager().get(id) != null){
	    		return getAdozioneManager().get(id).getUser().getFullName();
	        }else{
	        	return null;
	        }
		}catch (NumberFormatException nfe){
			System.out.println("NumberFormatException AdozioneController.loadNomeCognome"); 
			//nfe.printStackTrace();
			response.setContentType("text/html");
			return null;
	    }catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }
	
	@ModelAttribute("indirizzoMail")
    protected String indirizzoMail(final HttpServletRequest request, final HttpServletResponse response){
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]); 
	        if (getAdozioneManager().get(id) != null){
	        	return getAdozioneManager().get(id).getUser().getEmail();
	        }else{
	        	return null;
	        }
		}catch (NumberFormatException nfe){
			System.out.println("NumberFormatException AdozioneController.indirizzoMail"); 
			//nfe.printStackTrace();
			response.setContentType("text/html");
			return null;
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }
	
	@ModelAttribute("adozione")
    protected Adozione loadAdozione(final HttpServletRequest request, final HttpServletResponse response) {
		try{
	        String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]); 
	        
	        
	        if (getAdozioneManager().get(id) != null){
	        	return getAdozioneManager().get(id);
	        }else{
	        	return null;
	        }
		}catch (NumberFormatException nfe){
			System.out.println("NumberFormatException AdozioneController.loadAdozione"); 
			//nfe.printStackTrace();
			response.setContentType("text/html");
			return null;
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }
	
	
	@ModelAttribute("caneListContainer")
    protected CaneListContainer loadCane(final HttpServletRequest request, final HttpServletResponse response) {
		try{
			String string = request.getPathInfo();
	        String[] parts = string.split("/");
	        long id = Long.parseLong(parts[4]); 
	        if (getAdozioneManager().get(id) != null){
	        	CaneListContainer clc = new CaneListContainer(caneManager.getCaneByAdozione(id));
	    		return clc;
	        }else{
	        	return null;
	        }
		}catch (NumberFormatException nfe){
			System.out.println("NumberFormatException AdozioneController.loadCane"); 
			//nfe.printStackTrace();
			response.setContentType("text/html");
			return null;
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }
	
	@ModelAttribute("images")
    protected List<Image> loadImages(final HttpServletRequest request, final HttpServletResponse response) {
		try{
	        String stringPathInfo = request.getPathInfo();
	        String[] parts = stringPathInfo.split("/");
	        long id = Long.parseLong(parts[4]); 
	        if (getAdozioneManager().get(id) != null){
	        	long idUser = getAdozioneManager().get(id).getUser().getId();
	        	long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ADOZIONE_ROLE).getId();
	            return getImageManager().listImageUserTipoServizio(idUser, idTipo);
	        }else{
	        	return null;
	        }
	    }catch (NumberFormatException nfe){
			//nfe.printStackTrace();
	    	response.setContentType("text/html");
			return null;
		}catch(Exception exc){
	    	exc.printStackTrace();
			return null;
		}
        
    }
*/


}
