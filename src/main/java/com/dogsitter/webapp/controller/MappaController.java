package com.dogsitter.webapp.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.dogsitter.Constants;
import com.dogsitter.model.LabelValue;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/mappa*")
public class MappaController extends BaseFormController {
	
	public MappaController() {
		setCancelView("redirect:/");
        //setSuccessView("redirect:/mappa");
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
    protected ModelAndView mappaGet(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
		System.out.println("MappaController GET");
    	
    	//return getSuccessView();
    	return new ModelAndView("mappa");

    }
	
	
	@RequestMapping(method = RequestMethod.POST)
    public String onSubmit(final HttpServletRequest request, final HttpServletResponse response){
		
		System.out.println("MappaController POST");

        return getCancelView();
        
	}
	
	
	
	@ModelAttribute(Constants.AVAILABLE_ROLES_SERVIZIDOG)
	public List<LabelValue> checkBoxServizi(HttpServletRequest request) {
		return super.checkBoxServizi(request);
	}


}
