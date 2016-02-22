package com.dogsitter.webapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dogsitter.model.User;
import com.dogsitter.service.MailEngine;
import com.dogsitter.service.UserManager;
import com.dogsitter.webapp.util.RequestUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple class to retrieve and send a password hint to users.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/passwordHint*")
public class PasswordHintController {
    private final Log log = LogFactory.getLog(PasswordHintController.class);
    private UserManager userManager = null;
    private MessageSource messageSource = null;
    protected MailEngine mailEngine = null;
    protected SimpleMailMessage message = null;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Autowired
    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }

    @RequestMapping(method = RequestMethod.GET)
    public /*ModelAndView*/ String handleRequest(HttpServletRequest request)
    throws Exception {
        log.debug("entering 'handleRequest' method...");

        String username = request.getParameter("username");
        MessageSourceAccessor text = new MessageSourceAccessor(messageSource, request.getLocale());

        // ensure that the username has been sent
        if (username == null) {
            log.warn("Username not specified, notifying user that it's a required field.");
            request.setAttribute("error", text.getMessage("errors.required", text.getMessage("user.username")));
            //return new ModelAndView("login");
            return "redirect:/login";
        }

        log.debug("Processing Password Hint...");

        // look up the user's information
        try {
            User user = userManager.getUserByUsername(username);

            StringBuffer msg = new StringBuffer();
            msg.append("Suggerimento password: ").append(user.getPasswordHint());
            msg.append("\n\nAccedi a: ").append(RequestUtil.getAppURL(request));

            message.setTo(user.getEmail());
            String subject = '[' + text.getMessage("webapp.name") + "] " + 
                             text.getMessage("user.passwordHint");
            message.setSubject(subject);
            message.setText(msg.toString());
            mailEngine.send(message);

            saveMessage(request, text.getMessage("login.passwordHint.sent", new Object[] { username, user.getEmail() }));
        } catch (UsernameNotFoundException e) {
            log.warn(e.getMessage());
            saveError(request, text.getMessage("login.passwordHint.error", new Object[] { username }));
        } catch (MailException me) {
            log.warn(me.getMessage());
            saveError(request, me.getCause().getLocalizedMessage());
        }

        //return new ModelAndView(new RedirectView(request.getContextPath()));
        return "redirect:/login";
    }

    @SuppressWarnings("unchecked")
    public void saveError(HttpServletRequest request, String error) {
        List errors = (List) request.getSession().getAttribute("errors");
        if (errors == null) {
            errors = new ArrayList<String>();
        }
        errors.add(error);
        request.getSession().setAttribute("errors", errors);
    }

    // this method is also in BaseForm Controller
    @SuppressWarnings("unchecked")
    public void saveMessage(HttpServletRequest request, String msg) {
        List messages = (List) request.getSession().getAttribute(BaseFormController.MESSAGES_KEY);
        if (messages == null) {
            messages = new ArrayList<String>();
        }
        messages.add(msg);
        request.getSession().setAttribute(BaseFormController.MESSAGES_KEY, messages);
    }
}
