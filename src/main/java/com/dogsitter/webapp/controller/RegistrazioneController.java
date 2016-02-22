package com.dogsitter.webapp.controller;



import java.util.Date;
import java.util.Locale;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.dogsitter.Constants;
import com.dogsitter.model.User;
import com.dogsitter.service.RoleManager;
import com.dogsitter.service.UserExistsException;
import com.dogsitter.webapp.util.RequestUtil;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/registrazione*")
public class RegistrazioneController extends BaseFormController {
	
	
	
    public RegistrazioneController() {
    	setCancelView("redirect:login");
        setSuccessView("redirect:login");
		// TODO Auto-generated constructor stub
	}




	private RoleManager roleManager;
    
    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }
	
    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    public User showForm() {
    	System.out.println("RegistrazioneController registrazione GET");
        return new User();
    }


	

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(final User user, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response)
    		throws Exception {

    	System.out.println("RegistrazioneController save POST");
    	
    	
    	if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        if (validator != null) { // validator is null during testing
            validator.validate(user, errors);

            if (StringUtils.isBlank(user.getPassword())) {
                errors.rejectValue("password", "errors.required", new Object[] { getText("user.password", request.getLocale()) },
                        "Password is a required field.");
            }

            if (errors.hasErrors()) {
                return "registrazione";
            }
        }

        final Locale locale = request.getLocale();

        // Set the default user role on this new user
        user.addRole(roleManager.getRole(Constants.USER_ROLE));

        try {

	    	//inserisco la data di adesso 
	        user.setSignupDate(new Date());
	        
	    	//disabilito l'utente
	        user.setEnabled(false);
	        
	        //genero il token e lo salvo (per la conferma email)
	        int length = RandomUtils.nextInt(16) + 16;
	    	String code = RandomStringUtils.randomAlphanumeric(length);
	        user.setEmailConfirmToken(code);
	        
	        //salvo l'utente
	        User newUser = this.getUserManager().saveUser(user);
	
	        this.getUserManager().startConfirm(user, locale, RequestUtil.getAppURL(request));
			
			saveMessage(request, getText("Conferma la Registrazione: abbiamo inviato una mail al tuo indirizzo di posta: "+ 
					newUser.getEmail(), newUser.getUsername(), locale));

        	return getSuccessView();
            
        } catch (final AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (final UserExistsException e) {
        	System.out.println("UserExistsException RegistrazioneController");
        	if ( this.getUserManager().getUserFacebookByEmail(user.getEmail()) != null ){
        		User facebookUser = this.getUserManager().getUserFacebookByEmail(user.getEmail());
        		//saveMessage(request, getText("errors.existing.userFacebook"+ 
        			//	facebookUser.getEmail(), facebookUser.getFullName(), locale));
        		
        		saveMessage(request, getText("errors.existing.userFacebook", new Object[] { facebookUser.getUsername(), facebookUser.getEmail() },  locale));
        		
        		
        		return getSuccessView();
        	}else{
        		
                errors.rejectValue("username", "errors.existing.user",
                        new Object[] { user.getUsername(), user.getEmail() }, "duplicate user");
                return "registrazione";
        		
        	}
        } catch (final MailException me) {
        	me.printStackTrace();
	    	log.warn(me.getMessage());
	    	return "registrazione";
        } catch (final Exception exc) {
	    	exc.printStackTrace();
	    	log.warn(exc.getMessage());
	    	return "registrazione";
	     }

    }
    
    

    
}
