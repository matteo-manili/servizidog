package com.dogsitter.webapp.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dogsitter.model.Contatti;
import com.dogsitter.service.MailEngine;



/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/contatti*")
public class ContattiController extends BaseFormController {
	
	protected SimpleMailMessage message = null;
	
    @Autowired
    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Autowired
    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }
	
	public ContattiController() {
		setCancelView("/");
        setSuccessView("/contatti");
	}
	
	
	
	
	@ModelAttribute("contatti")
    protected Contatti loadContatti(final HttpServletRequest request) {
		Contatti contatti = new Contatti();
		return contatti;
    }
	
	
	@RequestMapping(method = RequestMethod.GET)
    protected String contattiGet(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	return getSuccessView();
    }
	
	
	@RequestMapping(method = RequestMethod.POST)
    public String contattiPost(@ModelAttribute("contatti") final Contatti contattiMod, final BindingResult errors, 
		final HttpServletRequest request, final HttpServletResponse response){
	
	
		System.out.println("sono in ContattiController POST");
    	if (validator != null) { // validator is null during testing
    		validator.validate(contattiMod, errors);
            if (errors.hasErrors() ) { // don't validate when deleting
            	System.out.println("validazione errata");
            	return getSuccessView();
            }
        }
	        	
			//final Locale locale = request.getLocale();
	    try{
			//message 
			String messageParam = contattiMod.getMessaggio();
			String emailParam = contattiMod.getEmail();
			String nameParam = contattiMod.getNome();
			
			StringBuffer msg = new StringBuffer();
	        msg.append("Nome: ").append(nameParam);
	        msg.append("\n\n Email: ").append(emailParam);
	        msg.append("\n\n Testo: ").append(messageParam);
	        
	        message.setFrom("info@servizidog.it");
			message.setTo("matteo.manili@gmail.com");
	        String subject = "Contatti - ServiziDog";
	        message.setSubject(subject);
	        message.setText(msg.toString());
	        mailEngine.send(message);
			
	        
	        saveMessage(request, "messaggio inviato" );
			return getSuccessView();
			
		}catch(Exception e){
			System.out.println("Exception ContattiController.contattiPost");
			saveError(request, "errore messaggio non inviato");
			e.printStackTrace();
			
			return getSuccessView();
		}
		
	}
	
	
	


    
	

    


}
