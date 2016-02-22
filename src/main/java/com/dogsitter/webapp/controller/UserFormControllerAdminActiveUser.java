package com.dogsitter.webapp.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dogsitter.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/admin/activeUsers*")
public class UserFormControllerAdminActiveUser extends BaseFormController {


    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("user") final User user, final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {

        return "admin/activeUsers";
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String showForm(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	
    	return "admin/activeUsers";
       
    }

}
