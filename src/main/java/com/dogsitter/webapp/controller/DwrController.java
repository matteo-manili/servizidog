package com.dogsitter.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import com.dogsitter.service.RoleManager;
import com.dogsitter.service.TipoRuoliServiceDogManager;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Classe utile per richiamare il ServletContext HttpSession HttpServletRequest da una chiamata DWR implementando org.directwebremoting.WebContext
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
public class DwrController extends BaseFormController {

    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
    }
    
    @Autowired
    public void setTipoRuoliServiceDogManager(
			TipoRuoliServiceDogManager tipoRuoliServiceDogManager) {
	}



    
    
    public String  controllaDogsitterTitolo(String username ) {
        System.out.println("sono in DwrController controllaDogsitterTitolo");
        
        //return getUserManager().getUserByUsername(username).getDogSitter().getTitolo();
        
        return "titolo statico da modificare!!";
        
      }
    
    
    
    public String impostaSessionRuoliMenu(String aa) throws Exception {
    	System.out.println("sono in DwrController (funziona come una Servlet) impostaSessionRuoliMenu dwr.....");
    	WebContext wc = WebContextFactory.get();
    	//ServletContext AA = wc.getServletContext();
    	//HttpSession session = WebContextFactory.get().getSession();
    	//HttpServletRequest req = ctx.getHttpServletRequest();
        //final User user = getUserManager().getUserByUsername(req.getRemoteUser());
    	HttpServletRequest bb = wc.getHttpServletRequest();
    	bb.getRemoteAddr();
    	//AA.setAttribute("stocazzo", "stocazzoPorcaMadonna");
    	//session.setAttribute("stocazzo", "stocazzoPorcaMadonna");
        return "ok";
    }







}
