package com.dogsitter.webapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.dogsitter.Constants;
import com.dogsitter.model.LabelValue;
import com.dogsitter.model.User;
import com.dogsitter.service.AdozioneManager;
import com.dogsitter.service.AnnunciCercaPadroneAdozioneManager;
import com.dogsitter.service.AnnunciKijijiDogSitterManager;
import com.dogsitter.service.AssociazioneManager;
import com.dogsitter.service.DogHostManager;
import com.dogsitter.service.DogSitterManager;
import com.dogsitter.service.ImageManager;
import com.dogsitter.service.MailEngine;
import com.dogsitter.service.TipoRuoliServiceDogManager;
import com.dogsitter.service.UserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Implementation of <strong>SimpleFormController</strong> that contains
 * convenience methods for subclasses.  For example, getting the current
 * user and saving messages/errors. This class is intended to
 * be a base class for all Form controllers.
 *
 * <p><a href="BaseFormController.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseFormController implements ServletContextAware {
    public static final String MESSAGES_KEY = "successMessages";
    public static final String ERRORS_MESSAGES_KEY = "errors";
    protected final transient Log log = LogFactory.getLog(getClass());
  
    protected MailEngine mailEngine = null;
    protected SimpleMailMessage message = null;
    protected String templateName = "accountCreated.vm";
    protected String cancelView;
    protected String successView;
    protected String successCheckBoxServizi;
    protected String successCaniAdozione;
    protected String successCaniAssociazione;
    protected String eliminaProfilo;
    
    
    
    @Autowired(required = false)
    Validator validator;
    
    
    
    private ServletContext servletContext;
    private MessageSourceAccessor messages;


    @Autowired
    public void setMessages(MessageSource messageSource) {
        messages = new MessageSourceAccessor(messageSource);
    }
    
    private UserManager userManager = null;
    
    //aggiunto
    private DogSitterManager dogSitterManager = null;
    private DogHostManager dogHostManager = null;
    private AdozioneManager adozioneManager = null;
    private AssociazioneManager associazioneManager = null;
    private TipoRuoliServiceDogManager TipoRuoliManager = null;
    private ImageManager imageManager = null;
    private AnnunciKijijiDogSitterManager annunciKijijiDogSitterManager = null;
    private AnnunciCercaPadroneAdozioneManager annunciCercaPadroneAdozioneManager = null;
    
    //private PersonManager personManager = null;
    //private ZonaManager zonaManager = null;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }
    
    //aggiunto
    @Autowired
    public void setDogSitterManager(DogSitterManager dogSitterManager) {
		this.dogSitterManager = dogSitterManager;
	}
    public DogSitterManager getDogSitterManager() {
		return dogSitterManager;
	}
    
    @Autowired
    public void setDogHostManager(DogHostManager dogHostManager) {
		this.dogHostManager = dogHostManager;
	}

    public DogHostManager getDogHostManager() {
		return dogHostManager;
	}

    @Autowired
    public void setAdozioneManager(AdozioneManager adozioneManager) {
		this.adozioneManager = adozioneManager;
	}
	public AdozioneManager getAdozioneManager() {
		return adozioneManager;
	}

	@Autowired
	public void setAssociazioneManager(AssociazioneManager associazioneManager) {
		this.associazioneManager = associazioneManager;
	}
	public AssociazioneManager getAssociazioneManager() {
		return associazioneManager;
	}

	@Autowired
	public void setTipoRuoliManager(TipoRuoliServiceDogManager tipoRuoliManager) {
		TipoRuoliManager = tipoRuoliManager;
	}
    public TipoRuoliServiceDogManager getTipoRuoliManager() {
		return TipoRuoliManager;
	}

    @Autowired
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}
	public ImageManager getImageManager() {
		return imageManager;
	}
	
	@Autowired
	public void setAnnunciKijijiDogSitterManager(
			AnnunciKijijiDogSitterManager annunciKijijiDogSitterManager) {
		this.annunciKijijiDogSitterManager = annunciKijijiDogSitterManager;
	}
	public AnnunciKijijiDogSitterManager getAnnunciKijijiDogSitterManager() {
		return annunciKijijiDogSitterManager;
	}
	
	@Autowired
	public void setAnnunciCercaPadroneAdozioneManager(
			AnnunciCercaPadroneAdozioneManager annunciCercaPadroneAdozioneManager) {
		this.annunciCercaPadroneAdozioneManager = annunciCercaPadroneAdozioneManager;
	}
	public AnnunciCercaPadroneAdozioneManager getAnnunciCercaPadroneAdozioneManager() {
		return annunciCercaPadroneAdozioneManager;
	}
	
	/*
	 * ALTRA SOLUZIONE PER GESTIRE LA PAGINA 404 NOT FOUND: LANCIO LA ECCEZZIONE throw new ResourceNotFoundException("Risorsa non trovata")
	 * DAL GET E LA RACCOLGO QUI
	 * VEDERE http://www.javabeat.net/spring-mvc-404-error-page/
	 * E VEDERE http://stackoverflow.com/questions/21061638/spring-mvc-how-to-return-custom-404-errorpages
	 * 
	 * 
	@ExceptionHandler(ResourceNotFoundException.class)
	public ModelAndView handleResourceNotFoundException() {
		System.out.println("error handleResourceNotFoundException BaseFormController");
		return new ModelAndView("redirect:/404");
	}
	*/

	@SuppressWarnings("unchecked")
    public void saveError(HttpServletRequest request, String error) {
        List<String> errors = (List<String>) request.getSession().getAttribute(ERRORS_MESSAGES_KEY);
        if (errors == null) {
            errors = new ArrayList<String>();
        }
        errors.add(error);
        request.getSession().setAttribute(ERRORS_MESSAGES_KEY, errors);
    }
    
    @SuppressWarnings("unchecked")
    public void saveMessage(HttpServletRequest request, String msg) {
        List<String> messages = (List<String>) request.getSession().getAttribute(MESSAGES_KEY);

        if (messages == null) {
            messages = new ArrayList<String>();
        }
        
        messages.add(msg);
        request.getSession().setAttribute(MESSAGES_KEY, messages) ;
    }

    
    
    /**
     * Convenience method for getting a i18n key's value.  Calling
     * getMessageSourceAccessor() is used because the RequestContext variable
     * is not set in unit tests b/c there's no DispatchServlet Request.
     *
     * @param msgKey
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, Locale locale) {
        return messages.getMessage(msgKey, locale);
    }

    /**
     * Convenient method for getting a i18n key's value with a single
     * string argument.
     *
     * @param msgKey
     * @param arg
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, String arg, Locale locale) {
        return getText(msgKey, new Object[] { arg }, locale);
    }

    /**
     * Convenience method for getting a i18n key's value with arguments.
     *
     * @param msgKey
     * @param args
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, Object[] args, Locale locale) {
        return messages.getMessage(msgKey, args, locale);
    }

    /**
     * Convenience method to get the Configuration HashMap
     * from the servlet context.
     *
     * @return the user's populated form from the session
     */
    public Map getConfiguration() {
        Map config = (HashMap) servletContext.getAttribute(Constants.CONFIG);
        // so unit tests don't puke when nothing's been set
        if (config == null) {
            return new HashMap();
        }

        return config;
    }

    /**
     * Set up a custom property editor for converting form inputs to real objects
     * @param request the current request
     * @param binder the data binder
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Integer.class, null,
                                    new CustomNumberEditor(Integer.class, null, true));
        binder.registerCustomEditor(Long.class, null,
                                    new CustomNumberEditor(Long.class, null, true));
        binder.registerCustomEditor(byte[].class,
                                    new ByteArrayMultipartFileEditor());
        SimpleDateFormat dateFormat = 
            new SimpleDateFormat(getText("date.format", request.getLocale()));
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, 
                                    new CustomDateEditor(dateFormat, true));
    }

    /**
     * Convenience message to send messages to users, includes app URL as footer.
     * @param user the user to send a message to.
     * @param msg the message to send.
     * @param url the URL of the application.
     */
    protected void sendUserMessage(User user, String msg, String url) {
        if (log.isDebugEnabled()) {
            log.debug("sending e-mail to user [" + user.getEmail() + "]...");
        }
        message.setTo(user.getFullName() + "<" + user.getEmail() + ">");

        Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);

        // TODO: once you figure out how to get the global resource bundle in
        // WebWork, then figure it out here too.  In the meantime, the Username
        // and Password labels are hard-coded into the template. 
        // model.put("bundle", getTexts());
        model.put("message", msg);
        model.put("applicationURL", url);
        mailEngine.sendMessage(message, templateName, model);
    }

    @Autowired
    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Autowired
    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
   
    public final BaseFormController setCancelView(String cancelView) {
        this.cancelView = cancelView;
        return this;
    }

    public final String getCancelView() {
        // Default to successView if cancelView is invalid
        if (this.cancelView == null || this.cancelView.length()==0) {
            return getSuccessView();
        }
        return this.cancelView;   
    }

    public final String getSuccessView() {
        return this.successView;
    }
    
    public final BaseFormController setSuccessView(String successView) {
        this.successView = successView;
        return this;
    }

    public String getSuccessCheckBoxServizi() {
		return successCheckBoxServizi;
	}

	public void setSuccessCheckBoxServizi(String successCheckBoxServizi) {
		this.successCheckBoxServizi = successCheckBoxServizi;
	}

	public String getSuccessCaniAdozione() {
		return successCaniAdozione;
	}

	public void setSuccessCaniAdozione(String successCaniAdozione) {
		this.successCaniAdozione = successCaniAdozione;
	}

	public String getSuccessCaniAssociazione() {
		return successCaniAssociazione;
	}

	public void setSuccessCaniAssociazione(String successCaniAssociazione) {
		this.successCaniAssociazione = successCaniAssociazione;
	}

	public String getEliminaProfilo() {
		return eliminaProfilo;
	}

	public void setEliminaProfilo(String eliminaProfilo) {
		this.eliminaProfilo = eliminaProfilo;
	}

	public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    protected ServletContext getServletContext() {
        return servletContext;
    }

    
    
    public List<LabelValue> checkBoxServizi(HttpServletRequest request) {
		List<LabelValue> list = new ArrayList<LabelValue>();
		list.add(new LabelValue( getText("dogsitter.title", request.getLocale()), Constants.DOGSITTER_ROLE ));
		list.add(new LabelValue( getText("doghost.title", request.getLocale()), Constants.DOGHOST_ROLE ));
		list.add(new LabelValue( getText("adozione.adozionecane.title", request.getLocale()), Constants.ADOZIONE_ROLE ));
		list.add(new LabelValue( getText("associazione.title", request.getLocale()), Constants.ASSOCIAZIONE_ROLE ));
		return list;
	}
    
    
    
    public List<LabelValue> checkServizi4Ricerca(final HttpServletRequest request) {
    	List<LabelValue> list = new ArrayList<LabelValue>();
    	list.add(new LabelValue( getText("dogsitter.title", request.getLocale()), getText("dogsitter.title", request.getLocale()) ));
    	list.add(new LabelValue( getText("doghost.title", request.getLocale()), getText("doghost.title", request.getLocale()) ));
    	list.add(new LabelValue( getText("adozione.adozionecane.title", request.getLocale()), getText("adozione.adozionecane.title", request.getLocale())));
    	list.add(new LabelValue( getText("associazione.title", request.getLocale()), getText("associazione.title", request.getLocale()) ));
    	return list;

    }
    
    
    public Map<String, String> MapSessoList(Locale locale) {
    	Map<String,String> sesso = new LinkedHashMap<String,String>();
		sesso.put("", getText("selected.seleziona", locale));
		sesso.put("cane.maschio", getText("cane.maschio", locale));
		sesso.put("cane.femmina", getText("cane.femmina", locale));
		return sesso;
	}
	
	
	public Map<String, String> MapTagliaList(Locale locale) {
		Map<String,String> taglia = new LinkedHashMap<String,String>();
		taglia.put("", getText("selected.seleziona", locale));
		taglia.put("cane.molto.piccola", getText("cane.molto.piccola", locale));
		taglia.put("cane.piccola", getText("cane.piccola", locale));
		taglia.put("cane.media", getText("cane.media", locale));
		taglia.put("cane.grande", getText("cane.grande", locale));
		taglia.put("cane.molto.grande", getText("cane.molto.grande", locale));
		return taglia;
	}
	
	
	public Map<String, String> MapPeloList(Locale locale) {
		Map<String,String> pelo = new LinkedHashMap<String,String>();
		pelo.put("", getText("selected.seleziona", locale));
		pelo.put("cane.raso", getText("cane.raso", locale));
		pelo.put("cane.medio", getText("cane.medio", locale));
		pelo.put("cane.lungo", getText("cane.lungo", locale));
		return pelo;
	}

}
