package com.dogsitter.webapp.controller;


import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.dogsitter.Constants;
import com.dogsitter.model.User;
import com.dogsitter.service.TipoRuoliServiceDogManager;
import com.dogsitter.service.UserSignupTokenNotFoundException;
import com.dogsitter.webapp.util.RequestUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller to signup new users.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/confirmAccount*")
public class SignupEmailConfirmController extends BaseFormController {
    
    private TipoRuoliServiceDogManager tipoRuoliServiceDogManager;
    
    @Autowired
    public void setTipoRuoliServiceDogManager(final TipoRuoliServiceDogManager tipoRuoliServiceDogManager) {
        this.tipoRuoliServiceDogManager = tipoRuoliServiceDogManager;
    }
    

    @RequestMapping(method = RequestMethod.GET)
    public String confirmSignup(
    		@RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "token", required = false) final String token,
            final HttpServletRequest request) throws Exception {
    	
    	final Locale locale = request.getLocale();
    	try{
    		User user = this.getUserManager().confirmSignup(token);
    		
    		
    		if (!user.isEnabled()) {
                user.setEnabled(true);
                user.setConfirmDate(new Date());
                
                this.getUserManager().saveUser(user);
        		
        		this.loginAutomatic(user, request, locale);
                return "redirect:/home-utente";
                
            }else{
            	saveMessage(request, getText("Hai già confermato la tua registrazione!", user.getUsername(), locale));
                return "redirect:/home-utente";
            }
    		
    	}
        catch(UserSignupTokenNotFoundException ee){
        	saveError(request, getText("verifica email non riuscita, regitrati! ", locale));

        	return "redirect:/registrazione";
        }
    	catch(Exception cc){
    		saveError(request, getText("errore di altro tipo", locale));
    		System.out.println("errore di altro tipo");
    		cc.printStackTrace();
    		System.out.println(cc.getMessage());
    		
        	return "redirect:/home";
        }

    }
    
    
    private void loginAutomatic(User user, HttpServletRequest request, Locale locale){
    	
    	/*
    	if (user.getTipoRuoliServiceDogListPerMenu().size() > 0 ) {
        	getServletContext().setAttribute(Constants.RUOLI_SERVIZI_UTENTE, user.getTipoRuoliServiceDogListPerMenu());
        }
        */
    	
    	final String password = user.getPassword();
    	
    	saveMessage(request, getText("user.registered", user.getUsername(), locale));
        request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);

        // log user in automatically
        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(), password, user.getAuthorities());
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Send user an e-mail
        if (log.isDebugEnabled()) {
            log.debug("Sending user '" + user.getUsername() + "' an account information e-mail");
        }

        // Send an account information e-mail
        message.setSubject(getText("signup.email.subject", locale));

        try {
            sendUserMessage(user, getText("signup.email.message", locale), RequestUtil.getAppURL(request));
        } catch (final MailException me) {
            saveError(request, me.getMostSpecificCause().getMessage());
        }
    }
    

    
}
