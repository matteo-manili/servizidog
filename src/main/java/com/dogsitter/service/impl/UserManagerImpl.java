package com.dogsitter.service.impl;


import org.apache.commons.lang.StringUtils;

import com.dogsitter.dao.UserDao;
import com.dogsitter.model.User;
import com.dogsitter.service.MailEngine;
import com.dogsitter.service.UserExistsException;
import com.dogsitter.service.UserManager;
import com.dogsitter.service.UserService;
import com.dogsitter.service.UserSignupTokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;




/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("userManager")
@WebService(serviceName = "UserService", endpointInterface = "com.dogsitter.service.UserService")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager, UserService {
    private PasswordEncoder passwordEncoder;
    private UserDao userDao;

    private MailEngine mailEngine;
    private SimpleMailMessage message;
    private PasswordTokenManager passwordTokenManager;

    private String passwordRecoveryTemplate = "passwordRecovery.vm";
    private String passwordUpdatedTemplate = "passwordUpdated.vm";
    private String usernameAndPasswordRecoveryTemplate = "usernameAndPasswordRecovery.vm";
                                                         
    
    
    //----------conferma email user regustration
    
    
    // i18n for the subject's email. Configurable in spring bean, with a default bundle name
    private String resourceBundleName = "MailResources";
    private ResourceBundle rb;
 

    @Override
    public User startConfirm(User user, Locale locale, String AppUrl) throws MailException, Exception {
        
        //invio la mail con il token
        message.setTo(user.getFullName() + "<" + user.getEmail() + ">");
        message.setSubject("Conferma Registrazione");
        
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("userFirstName", user.getFirstName());
        model.put("appURL", AppUrl);
        model.put("signupConfirmURL", AppUrl + "/confirmAccount?token="+user.getEmailConfirmToken());
        mailEngine.sendMessage(message, "signupConfirm.vm", model);
        return user;
    }
 

    @Override
    public User confirmSignup(String token) throws UserSignupTokenNotFoundException, Exception {
        return userDao.getUserFromSignupToken(token) ;

    }

    
    //------------------fine conferma user registration
    

    @Autowired
    @Qualifier("passwordEncoder")
    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Autowired
    public void setUserDao(final UserDao userDao) {
        this.dao = userDao;
        this.userDao = userDao;
    }

    @Autowired(required = false)
    public void setMailEngine(final MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Autowired(required = false)
    public void setMailMessage(final SimpleMailMessage message) {
        this.message = message;
    }

    @Autowired(required = false)
    public void setPasswordTokenManager(final PasswordTokenManager passwordTokenManager) {
        this.passwordTokenManager = passwordTokenManager;
    }

    /**
     * Velocity template name to send users a password recovery mail (default
     * passwordRecovery.vm).
     *
     * @param passwordRecoveryTemplate the Velocity template to use (relative to classpath)
     * @see com.dogsitter.service.MailEngine#sendMessage(org.springframework.mail.SimpleMailMessage, String, java.util.Map)
     */
    public void setPasswordRecoveryTemplate(final String passwordRecoveryTemplate) {
        this.passwordRecoveryTemplate = passwordRecoveryTemplate;
    }

    /**
     * Velocity template name to inform users their password was updated
     * (default passwordUpdated.vm).
     *
     * @param passwordUpdatedTemplate the Velocity template to use (relative to classpath)
     * @see com.dogsitter.service.MailEngine#sendMessage(org.springframework.mail.SimpleMailMessage, String, java.util.Map)
     */
    public void setPasswordUpdatedTemplate(final String passwordUpdatedTemplate) {
        this.passwordUpdatedTemplate = passwordUpdatedTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(final String userId) {
        return userDao.get(new Long(userId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsers() {
        return userDao.getAllDistinct();
    }

    
    @Override
    public boolean userUsernameExist(final String username)  {
    	
    	return userDao.userUsernameExist(username);
    }
    
    
    @Override
    public User saveUser(final User user) throws UserExistsException {

        if (user.getVersion() == null) {
            // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
        }

        // Get and prepare password management-related artifacts
        boolean passwordChanged = false; //<--- qui ci va
        if (passwordEncoder != null) { //<--- qui ci va
            // Check whether we have to encrypt (or re-encrypt) the password
            if (user.getVersion() == null) { // <--- qui ci va
                // New user, always encrypt
                passwordChanged = true; // <--- qui ci va
            } else {
                // Existing user, check password in DB
                final String currentPassword = userDao.getUserPassword(user.getId());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (!currentPassword.equals(user.getPassword())) {
                        passwordChanged = true;
                    }
                }
            }

            // If password was changed (or new user), encrypt it
            if (passwordChanged) { //<--- qui ci va
                user.setPassword(passwordEncoder.encode(user.getPassword())); //<--- qui ci va
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }

        try {
        	
            return userDao.saveUser(user); //<--- qui ci va
        } catch (final Exception e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }
    }

    

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUser(final User user) {
        log.debug("removing user: " + user);
        userDao.remove(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUser(final String userId) {
        log.debug("removing user: " + userId);
        userDao.remove(new Long(userId));
    }

    /**
     * {@inheritDoc}
     *
     * @param username the login name of the human
     * @return User the populated user object
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException thrown when username not found
     */
    @Override
    public User getUserByUsername(final String username) throws UsernameNotFoundException {
        return (User) userDao.loadUserByUsername(username);
    }
    
    @Override
    public User getUserByEmail(final String email) throws UsernameNotFoundException {
        return (User) userDao.loadUserByEmail(email);
    }
    
    
    @Override
    public User getUserFacebookByEmail(final String email) {
        if ( userDao.userEmailExist(email) ){
        	User userFacebook = (User) userDao.loadUserByEmail(email);
        	if (userFacebook.getFacebookId() != null && userFacebook.getEmailConfirmToken() == null ){
        		return userFacebook;
        	}else{
        		return null;
        	}
        }else{
        	return null;
        }
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> search(final String searchTerm) {
        return super.search(searchTerm, User.class);
    }

    @Override
    public String buildRecoveryPasswordUrl(final User user, final String urlTemplate) {
        final String token = generateRecoveryToken(user);
        final String username = user.getUsername();
        return StringUtils.replaceEach(urlTemplate,
                new String[]{"{username}", "{token}"},
                new String[]{username, token});
    }

    @Override
    public String generateRecoveryToken(final User user) {
        return passwordTokenManager.generateRecoveryToken(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRecoveryTokenValid(final String username, final String token) {
        return isRecoveryTokenValid(getUserByUsername(username), token);
    }

    @Override
    public boolean isRecoveryTokenValid(final User user, final String token) {
        return passwordTokenManager.isRecoveryTokenValid(user, token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPasswordRecoveryEmail(final String username, final String urlTemplate) {
        log.debug("Sending password recovery token to user: " + username);

        final User user = getUserByUsername(username);
        final String url = buildRecoveryPasswordUrl(user, urlTemplate);

        sendUserEmail(user, passwordRecoveryTemplate, url, "Password Recovery");
    }
    
    
    @Override
    public void sendUsenameAndPasswrdRecoveryEmail(final String email, final String urlTemplate) {
        log.debug("Sending password recovery token to user: " + email);

        final User user = getUserByEmail(email);
        final String url = buildRecoveryPasswordUrl(user, urlTemplate);

        sendUserEmail(user, usernameAndPasswordRecoveryTemplate, url, "Password Recovery");
    }
    
    

    private void sendUserEmail(final User user, final String template, final String url, final String subject) {
        message.setTo(user.getFullName() + "<" + user.getEmail() + ">");
        message.setSubject(subject);

        final Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);
        model.put("applicationURL", url);

        mailEngine.sendMessage(message, template, model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updatePassword(final String username, final String currentPassword, final String recoveryToken, final String newPassword, final String applicationUrl) throws UserExistsException {
        User user = getUserByUsername(username);
        if (isRecoveryTokenValid(user, recoveryToken)) {
            log.debug("Updating password from recovery token for user: " + username);
            user.setPassword(newPassword);
            user = saveUser(user);
            passwordTokenManager.invalidateRecoveryToken(user, recoveryToken);

            sendUserEmail(user, passwordUpdatedTemplate, applicationUrl, "Password Updated");

            return user;
        } else if (StringUtils.isNotBlank(currentPassword)) {
            if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                log.debug("Updating password (providing current password) for user:" + username);
                user.setPassword(newPassword);
                user = saveUser(user);
                return user;
            }
        }
        // or throw exception
        return null;
    }
}
