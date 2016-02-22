package com.dogsitter.webapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.dogsitter.Constants;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.LabelValue;
import com.dogsitter.model.TipoRuoliServiceDog;
import com.dogsitter.model.User;
import com.dogsitter.service.CaneManager;
import com.dogsitter.service.CreaSitemapManager;
import com.dogsitter.service.TipoRuoliServiceDogManager;
import com.dogsitter.service.UserExistsException;
import com.dogsitter.service.UserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Implementation of <strong>SimpleFormController</strong> that interacts with
 * the {@link UserManager} to retrieve/persist values to the database.
 *
 * <p><a href="UserFormController.java.html"><i>View Source</i></a>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/userform*")
public class UserFormController extends BaseFormController {

	
	private CaneManager caneManager;
	@Autowired
	public void setCaneManager(CaneManager caneManager) {
		this.caneManager = caneManager;
	}
	
	
	private CreaSitemapManager creaSitemapManager;
	@Autowired
	public void setCreaSitemapManager(CreaSitemapManager creaSitemapManager) {
		this.creaSitemapManager = creaSitemapManager;
	}

	
    private TipoRuoliServiceDogManager tipoRuoliServiceDogManager;
    @Autowired
    public void setTipoRuoliServiceDogManager(TipoRuoliServiceDogManager tipoRuoliServiceDogManager) {
		this.tipoRuoliServiceDogManager = tipoRuoliServiceDogManager;
	}

	public UserFormController() {
        setCancelView("redirect:/home-utente");
        setSuccessView("redirect:/home-utente");
        //setSuccessCheckBoxServizi("redirect:/userform#offriservizio");
        setSuccessCheckBoxServizi("redirect:/home-utente");
        
        setEliminaProfilo("redirect:/login");
    }



    /**
     * Load user object from db before web data binding in order to keep properties not populated from web post.
     * 
     * @param request
     * @return
     */
    @ModelAttribute("user")
    protected User loadUser(final HttpServletRequest request) {
        final String userId = request.getParameter("id");
        if (isFormSubmission(request) && StringUtils.isNotBlank(userId)) {
            return getUserManager().getUser(userId);
        }
        return new User();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("user") final User user, final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {
    	final Locale locale = request.getLocale();
    	boolean deleteAccount = false;
    	
    	if (request.getParameter("delete") != null && request.getParameter("delete").equals("ok")) {
        	System.out.println("ELIMINAZIONE ACCOUNT");
        	deleteAccount = true;
        }

        if (request.getParameter("cancel") != null) {
        	return getCancelView();
        }
        
        if (validator != null) { // validator is null during testing
            validator.validate(user, errors); 
            if (errors.hasErrors()) { // don't validate when deleting
                return "userform";
            }
        }
        
   		if (request.getParameter("ckRuoliServiziOk") != null || deleteAccount == true) {

   			user.getTipoRuoli().clear();
	
	        TipoRuoliServiceDog tipoRuoli = null;
	        String[] results = request.getParameterValues("ckRuoliServizi");
	        if(results == null || deleteAccount == true){
	        	results = new String[]{"VUOTO"};
	        }
	         
	        List<String> serviziCheck = Arrays.asList(results); 
	        
	    	if(serviziCheck.contains(Constants.DOGSITTER_ROLE)){
	    		System.out.println("DOGSITTER_ROLE CECCATO");
	    		
	    		if (this.getDogSitterManager().getDogSitterByUser(user.getId()) == null){
	            	DogSitter dogSitter = new DogSitter();
	            	dogSitter.setUser(user);
	            	this.getDogSitterManager().saveDogSitter(dogSitter);
	            	saveMessage(request, getText("Servizio Dog Sitter creato", user.getFullName(), locale));
	    		}
	        	//popolo il menu utente servizi....
	    		tipoRuoli = tipoRuoliServiceDogManager.getTipoRuoliServiceDogDaoByName(Constants.DOGSITTER_ROLE);
	    		user.addtipoRuoli(tipoRuoli);
				
	    	}else{
	    		if (this.getDogSitterManager().getDogSitterByUser(user.getId()) != null){
	            	DogSitter dogSitter = getDogSitterManager().getDogSitterByUser(user.getId());
	            	getDogSitterManager().removeDogSitter(dogSitter.getId());
	            	saveMessage(request, getText("Servizio Dog Sitter Eliminato", user.getFullName(), locale));
	            	
	            	//Aggiorno la sitemap.xml con questo dogsitter in meno
	            	//creo la Sitemap.xml
	                //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
	    		}
	    		System.out.println("DOGSITTER_ROLE NON CECCATO");	
	        	//tolgo dal menu utente servizi....
	        	user.getTipoRuoli().remove(Constants.DOGSITTER_ROLE);
	    		
	    	}
	    	
	    	if(serviziCheck.contains(Constants.DOGHOST_ROLE)){
	    		System.out.println("DOGHOST_ROLE CECCATO ");
	    		
	    		if (this.getDogHostManager().getDogHostByUser(user.getId()) == null){
	            	DogHost dogHost = new DogHost();
	            	dogHost.setUser(user);
	            	this.getDogHostManager().saveDogHost(dogHost);
	            	saveMessage(request, getText("Servizio Dog Host creato", user.getFullName(), locale));
	    		}

	    		
	    		//popolo il menu utente servizi....
	    		tipoRuoli = tipoRuoliServiceDogManager.getTipoRuoliServiceDogDaoByName(Constants.DOGHOST_ROLE);
	    		user.addtipoRuoli(tipoRuoli);
	    	}else{
	    		if (this.getDogHostManager().getDogHostByUser(user.getId()) != null){
	            	DogHost dogHost = getDogHostManager().getDogHostByUser(user.getId());
	            	getDogHostManager().removeDogHost(dogHost.getId());
	            	saveMessage(request, getText("Servizio Dog Host Eliminato", user.getFullName(), locale));
	            	
	            	//Aggiorno la sitemap.xml con questo doghost in meno
	            	//creo la Sitemap.xml
	                //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
	    		}
	    		System.out.println("DOGHOST_ROLE NON CECCATO");	
	    		//tolgo dal menu utente servizi....
	        	user.getTipoRuoli().remove(Constants.DOGHOST_ROLE);
	    	}
	    	
	    	
	    	
	    	if(serviziCheck.contains(Constants.ADOZIONE_ROLE)){
	    		System.out.println("ADOZIONE_ROLE CECCATO");
	    		
	    		if (this.getAdozioneManager().getAdozioneByUser(user.getId()) == null){
	            	Adozione adozione = new Adozione();
	            	adozione.setUser(user);
	            	this.getAdozioneManager().saveAdozione(adozione);
	            	saveMessage(request, getText("Adozione creato", user.getFullName(), locale));
	    		}
	        	//popolo il menu utente servizi....
	    		tipoRuoli = tipoRuoliServiceDogManager.getTipoRuoliServiceDogDaoByName(Constants.ADOZIONE_ROLE);
	    		user.addtipoRuoli(tipoRuoli);
				
	    	}else{
	    		if (this.getAdozioneManager().getAdozioneByUser(user.getId()) != null){
	            	Adozione adozione = getAdozioneManager().getAdozioneByUser(user.getId());
	            	
	            	//elimino i cani annessi
	            	caneManager.eliminaCaniByAdozione(adozione.getId());
	            	
	            	//elimino la adozione
	            	getAdozioneManager().removeAdozione(adozione.getId());

	            	
	            	saveMessage(request, getText("Adozione Eliminato", user.getFullName(), locale));
	            	
	            	//Aggiorno la sitemap.xml con questo dogsitter in meno
	            	//creo la Sitemap.xml
	                //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
	    		}
	    		System.out.println("ADOZIONE_ROLE NON CECCATO");	
	        	//tolgo dal menu utente servizi....
	        	user.getTipoRuoli().remove(Constants.ADOZIONE_ROLE);
	    		
	    	}

	    	
	    	if(serviziCheck.contains(Constants.ASSOCIAZIONE_ROLE)){
	    		System.out.println("ASSOCIAZIONE_ROLE CECCATO ");
	    		
	    		if (this.getAssociazioneManager().getAssociazioneByUser(user.getId()) == null){
	            	Associazione associazione = new Associazione();
	            	associazione.setUser(user);
	            	this.getAssociazioneManager().saveAssociazione(associazione);
	            	saveMessage(request, getText("Servizio Associazione creato", user.getFullName(), locale));
	    		}

	    		//popolo il menu utente servizi....
	    		tipoRuoli = tipoRuoliServiceDogManager.getTipoRuoliServiceDogDaoByName(Constants.ASSOCIAZIONE_ROLE);
	    		user.addtipoRuoli(tipoRuoli);
	    	}else{
	    		if (this.getAssociazioneManager().getAssociazioneByUser(user.getId()) != null){
	            	Associazione associazione = getAssociazioneManager().getAssociazioneByUser(user.getId());
	            	
	            	//elimino i cani annessi
	            	caneManager.eliminaCaniByAssociazione(associazione.getId());
	            	
	            	//elimino la adozione
	            	getAssociazioneManager().removeAssociazione(associazione.getId());
	            	
	            	saveMessage(request, getText("Servizio Associazione Eliminato", user.getFullName(), locale));
	            	
	            	//Aggiorno la sitemap.xml con questo doghost in meno
	            	//creo la Sitemap.xml
	                //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
	    		}
	    		System.out.println("ASSOCIAZIONE_ROLE NON CECCATO");	
	    		//tolgo dal menu utente servizi....
	        	user.getTipoRuoli().remove(Constants.ASSOCIAZIONE_ROLE);
	    	}
	    	
	    	//mi serve per il menu
	        //getServletContext().setAttribute(Constants.RUOLI_SERVIZI_UTENTE, user.getTipoRuoliServiceDogListPerMenu());
	        request.getSession().setAttribute(Constants.RUOLI_SERVIZI_UTENTE, user.getTipoRuoliServiceDogListPerMenu());
	        
       }


        try {
        	
        	
        	if (deleteAccount == false){
        		getUserManager().saveUser(user);
        		
        	}else{
        		//getUserManager().removeUser(user.getId().toString());
        		
        		user.setEnabled(false);
        		//user.setAccountLocked(true);
        		//user.setAccountExpired(true);
        		//user.setCredentialsExpired(true);
        		
        		getUserManager().saveUser(user);

        		request.getSession().invalidate();
        		if (request.getSession(false) != null) {
        			request.getSession().invalidate();
        		}
        		
        		//roba di logout.jsp
        		Cookie terminate = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, null);
        		String contextPath = request.getContextPath();
        		terminate.setPath(contextPath != null && contextPath.length() > 0 ? contextPath : "/");
        		terminate.setMaxAge(0);
        		response.addCookie(terminate);
        		
                saveMessage(request, getText("user.deletedAccout", /* user.getFullName(),*/ locale ));
               
                return getEliminaProfilo();
        	}
            
        } catch (final AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (final UserExistsException e) {
            errors.rejectValue("username", "errors.existing.user",
                    new Object[] { user.getUsername(), user.getEmail() }, "duplicate user");
            //response.setIntHeader("Refresh", 5);
            return "userform";
        }
        
        saveMessage(request, getText("user.saved", user.getFullName(), locale)); 
        if (request.getParameter("ckRuoliServiziOk") != null) {
        	return getSuccessCheckBoxServizi();
        }else{
            return getSuccessView();
        }     

    }

    
    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected User showForm(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	
    	System.out.println("sono in showForm GET di UserFormController");
    	
        // If not an administrator, make sure user is not trying to add or edit another user
        if (!request.isUserInRole(Constants.ADMIN_ROLE) && !isFormSubmission(request)) {
            if (request.getParameter("id") != null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                log.warn("User '" + request.getRemoteUser() + "' is trying to edit user with id '" +
                        request.getParameter("id") + "'");
                throw new AccessDeniedException("You do not have permission to modify other users.");
            }
        }

        if (!isFormSubmission(request)) {
            User user;
            	user = new User();
                user = getUserManager().getUserByUsername(request.getRemoteUser());
            return user;
        } else {
            // populate user object from database, so all fields don't need to be hidden fields in form
            return getUserManager().getUser(request.getParameter("id"));
        }
    }

    private boolean isFormSubmission(final HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }
    

    @ModelAttribute(Constants.AVAILABLE_ROLES_SERVIZIDOG)
	public List<LabelValue> checkBoxServizi(HttpServletRequest request) {
		return super.checkBoxServizi(request);
	}



}
