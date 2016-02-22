package com.dogsitter.webapp.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dogsitter.Constants;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.User;
import com.dogsitter.service.CaneManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/home-utente*")
public class HomeController extends BaseFormController {

	private CaneManager caneManager;
	
	@Autowired
	public void setCaneManager(CaneManager caneManager) {
		this.caneManager = caneManager;
	}


	/*
    @ModelAttribute("user")
    protected User loadUser(final HttpServletRequest request) {
        final String userId = request.getParameter("id");
        if (isFormSubmission(request) && StringUtils.isNotBlank(userId)) {
            return getUserManager().getUser(userId);
        }
        return new User();
    }
	 */
	
	
	@ModelAttribute("fullName")
	protected String loadFullName(final HttpServletRequest request) {
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
        return user.getFullName();
    }
    
	
    @ModelAttribute("dogSitter")
    protected DogSitter loadDogSitter(final HttpServletRequest request) {
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
		DogSitter dogSitter = getDogSitterManager().getDogSitterByUser(user.getId());
        return dogSitter;
    }
    
    
    @ModelAttribute("dogHost")
    protected DogHost loadDogHost(final HttpServletRequest request) {
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
		DogHost dogHost = getDogHostManager().getDogHostByUser(user.getId());
        return dogHost;
    }
    
    
    @ModelAttribute("adozione")
    protected Adozione loadAdozione(final HttpServletRequest request) {
    	
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
		Adozione adozione = getAdozioneManager().getAdozioneByUser(user.getId());
        return adozione;
    }
    
    
    @ModelAttribute("associazione")
    protected Associazione loadAssociazione(final HttpServletRequest request) {
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
		Associazione associazione = getAssociazioneManager().getAssociazioneByUser(user.getId());
        return associazione;
    }
    
    
    @ModelAttribute("caneAssociazione")
    protected boolean loadCaneAssociazione(final HttpServletRequest request) {
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
    	Associazione associazione = getAssociazioneManager().getAssociazioneByUser(user.getId());
		if (associazione != null){
			try {
				if(caneManager.getCaneByAssociazione(associazione.getId()).size() > 0){
					return true;
				}else{
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}else{
			return false;
		}
    }
    
    
    @ModelAttribute("caneAdozione")
    protected boolean loadCane(final HttpServletRequest request) {
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
		Adozione adozione = getAdozioneManager().getAdozioneByUser(user.getId());
		if (adozione != null){
			try {
				if(caneManager.getCaneByAdozione(adozione.getId()).size() > 0){
					return true;
				}else{
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}else{
			return false;
		}

    }
    
    
    @ModelAttribute("imageDogSitterPref")
    protected boolean loadImagesDogSitter(final HttpServletRequest request) {
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
    	long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGSITTER_ROLE).getId();
    	boolean imagePref = getImageManager().controllaImmaginePreferitaExistHome(user.getId(), idTipo );
        return imagePref;
    }
    
    
    @ModelAttribute("imageDogHostPref")
    protected boolean loadImagesDogHost(final HttpServletRequest request) {
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
    	long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGHOST_ROLE).getId();
    	boolean imagePref = getImageManager().controllaImmaginePreferitaExistHome(user.getId(), idTipo );
        return imagePref;
    }
    
    
    @ModelAttribute("imageAdozionePref")
    protected boolean loadImagesAdozione(final HttpServletRequest request) {
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
    	long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ADOZIONE_ROLE).getId();
    	boolean imagePref = getImageManager().controllaImmaginePreferitaExistHome(user.getId(), idTipo );
        return imagePref;
    }

    
    @ModelAttribute("imageAssociazionePref")
    protected boolean loadImagesAssociazione(final HttpServletRequest request) {
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
    	long idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ASSOCIAZIONE_ROLE).getId();
    	boolean imagePref = getImageManager().controllaImmaginePreferitaExistHome(user.getId(), idTipo );
        return imagePref;
    }
    

    @RequestMapping(method = RequestMethod.GET)
    protected String showHome(final HttpServletRequest request, final HttpServletResponse response) {
    	System.out.println("sono in HomeController.showHome GET");
        return "home-utente"; 
    }

    


}
