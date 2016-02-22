package com.dogsitter.webapp.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView; 

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/canismarriti*")
public class CaniSmarritiController extends BaseFormController {
	


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
    	System.out.println("CaniSmarritiController GET");
    	
    	//Person userForm = new Person();
 
        return new ModelAndView("canismarriti");
    }
    
	

 
    
   
    
    
}
