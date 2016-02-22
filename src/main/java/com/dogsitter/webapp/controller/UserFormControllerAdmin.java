package com.dogsitter.webapp.controller;

import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.dogsitter.Constants;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.Role;
import com.dogsitter.model.User;
import com.dogsitter.service.CaneManager;
import com.dogsitter.service.RoleManager;
import com.dogsitter.service.UserExistsException;
import com.dogsitter.webapp.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/admin/userformAdmin*")
public class UserFormControllerAdmin extends BaseFormController {

    private RoleManager roleManager;

    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }
    
    private CaneManager caneManager;
	@Autowired
	public void setCaneManager(CaneManager caneManager) {
		this.caneManager = caneManager;
	}

    public UserFormControllerAdmin() {
        setCancelView("redirect:/home-utente");
        setSuccessView("redirect:/admin/users");
    }

    @Override
    @InitBinder
    protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        super.initBinder(request, binder);
        binder.setDisallowedFields("password", "confirmPassword");
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
        if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }

        if (validator != null) { // validator is null during testing
            validator.validate(user, errors); //SONO ADMIN QUI DENTRO è ENTRATO
           
            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
            	System.out.println("UserFormControllerAdmin.onSubmit validator");
                return "userform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        final Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
        	
        	//devo elimniare tutte le immagini relative all'utente.....
        	getImageManager().rimuoviTutteImmagineByUser(user.getId());
        	
        	System.out.println("elimino il dog sitter....");
        	DogSitter dogSitter = getDogSitterManager().getDogSitterByUser(user.getId());
        	if(dogSitter != null){
	        	System.out.println("elimino il dog sitter: "+dogSitter.getId()); 
	        	getDogSitterManager().removeDogSitter(dogSitter.getId());
	        	
	        	System.out.println("dog sitter eliminato"); 
        	}
        	
        	System.out.println("elimino il dog host....");
        	DogHost dogHost = getDogHostManager().getDogHostByUser(user.getId());
        	if(dogHost != null){
	        	System.out.println("elimino il dog host: "+dogHost.getId()); 
	        	getDogHostManager().removeDogHost(dogHost.getId());
	        	
	        	System.out.println("dog host eliminato");
        	}
        	
        	System.out.println("elimino la adozione....");
        	Adozione adozione = getAdozioneManager().getAdozioneByUser(user.getId());
        	if(adozione != null){
        		System.out.println("elimino i cani annessi: "+adozione.getId()); 
        		caneManager.eliminaCaniByAdozione(adozione.getId());
        		
	        	System.out.println("elimino la adozione: "+adozione.getId()); 
	        	getAdozioneManager().removeAdozione(adozione.getId());
	        	
	        	System.out.println("adozione eliminato");
        	}
        	
        	System.out.println("elimino la associazione....");
        	Associazione associazione = getAssociazioneManager().getAssociazioneByUser(user.getId());
        	if(associazione != null){
        		System.out.println("elimino i cani annessi: "+associazione.getId()); 
        		caneManager.eliminaCaniByAssociazione(associazione.getId());
        		
	        	System.out.println("elimino la associazione: "+associazione.getId()); 
	        	getAssociazioneManager().removeAssociazione(associazione.getId());
	        	
	        	System.out.println("associazione eliminato");
        	}
        	
        	
        	
            getUserManager().removeUser(user.getId().toString());
            saveMessage(request, getText("user.deleted", user.getFullName(), locale));

            return getSuccessView();
            //return "admin/users";
            
        } else {

            // only attempt to change roles if user is admin for other users,
            // showForm() method will handle populating
            if (request.isUserInRole(Constants.ADMIN_ROLE)) {
                final String[] userRoles = request.getParameterValues("userRoles"); //SONO ADMIN QUI DENTRO è ENTRATO

                if (userRoles != null) {
                    user.getRoles().clear(); //SONO ADMIN QUI DENTRO NON ENTRATO
                    for (final String roleName : userRoles) {
                        user.addRole(roleManager.getRole(roleName)); //SONO ADMIN QUI DENTRO è ENTRATO
                    }
                }
            } else {
                // if user is not an admin then load roles from the database
                // (or any other user properties that should not be editable
                // by users without admin role)
                final User cleanUser = getUserManager().getUserByUsername(
                        request.getRemoteUser());
                user.setRoles(cleanUser.getRoles());
            }

            final Integer originalVersion = user.getVersion();

            // set a random password if user is added by admin
            if (originalVersion == null && StringUtils.isBlank(user.getPassword())) {
                user.setPassword(UUID.randomUUID().toString()); // XXX review if
                // UUID is a
                // good choice
                // here
            }

            try {
                getUserManager().saveUser(user);
            } catch (final AccessDeniedException ade) {
                // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
                log.warn(ade.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            } catch (final UserExistsException e) {
                errors.rejectValue("username", "errors.existing.user",
                        new Object[] { user.getUsername(), user.getEmail() }, "duplicate user");

                // reset the version # to what was passed in
                user.setVersion(originalVersion);

                return "admin/userformAdmin";
            }

            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                saveMessage(request, getText("user.saved", user.getFullName(), locale));

                // return to main Menu
                return getCancelView();
            } else {
                if (StringUtils.isBlank(request.getParameter("version"))) {
                    saveMessage(request, getText("user.added", user.getFullName(), locale));

                    // Send an account information e-mail
                    message.setSubject(getText("signup.email.subject", locale));

                    try {
                        final String resetPasswordUrl = getUserManager().buildRecoveryPasswordUrl(user,
                                UpdatePasswordController.RECOVERY_PASSWORD_TEMPLATE);
                        sendUserMessage(user, getText("newuser.email.message", user.getFullName(), locale),
                                RequestUtil.getAppURL(request) + resetPasswordUrl);
                    } catch (final MailException me) {
                        saveError(request, me.getCause().getLocalizedMessage());
                    }

                    return getSuccessView();
                } else {
                    saveMessage(request, getText("user.updated.byAdmin", user.getFullName(), locale).toString() );
                }
            }
        }
        return "admin/userformAdmin";
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected User showForm(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        // If not an administrator, make sure user is not trying to add or edit another user
        if (!request.isUserInRole(Constants.ADMIN_ROLE) && !isFormSubmission(request)) { //SONO ADMIN QUI DENTRO NON è ENTRATO
            if (isAdd(request) || request.getParameter("id") != null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                log.warn("User '" + request.getRemoteUser() + "' is trying to edit user with id '" +
                        request.getParameter("id") + "'");

                throw new AccessDeniedException("You do not have permission to modify other users.");
            }
        }

        if (!isFormSubmission(request)) {
            final String userId = request.getParameter("id");

            User user;
            if (userId == null && !isAdd(request)) {
                user = getUserManager().getUserByUsername(request.getRemoteUser()); //SONO ADMIN QUI DENTRO NON è ENTRATO
            } else if (!StringUtils.isBlank(userId) && !"".equals(request.getParameter("version"))) {
                user = getUserManager().getUser(userId); //SONO ADMIN QUI DENTRO è ENTRATO
            } else {
                user = new User();
                user.addRole(new Role(Constants.USER_ROLE));
            }

            return user;
        } else {
            // populate user object from database, so all fields don't need to be hidden fields in form
            return getUserManager().getUser(request.getParameter("id"));
        }
    }

    private boolean isFormSubmission(final HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    protected boolean isAdd(final HttpServletRequest request) {
        final String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }
}
