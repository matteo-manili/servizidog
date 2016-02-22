package com.dogsitter.webapp.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dogsitter.service.CaneManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/home")
public class IndexController extends BaseFormController {

	private CaneManager caneManager;
	@Autowired
	public void setCaneManager(CaneManager caneManager) {
		this.caneManager = caneManager;
	}

    @ModelAttribute("dogSitterTot")
    protected String loadDogSitterTot(final HttpServletRequest request) {
    	Long totAnn = getAnnunciKijijiDogSitterManager().getAnnunciKijijiDogSitter_COUNT();
    	int totDogSitter = getDogSitterManager().getDogSittersConInfoInserite();
        return String.valueOf(totAnn + totDogSitter);
    }
    
    @ModelAttribute("dogHostTot")
    protected String loadDogHost(final HttpServletRequest request) {
    	return String.valueOf(getDogHostManager().getDogHostConInfoInserite());
    }
    
    @ModelAttribute("caniTotAdozione")
    protected String loadAdozione(final HttpServletRequest request) {
    	int caniAdozioneTot = caneManager.getTotaleCaniByAdozione();
    	long annunciAdozioneTot = getAnnunciCercaPadroneAdozioneManager().getAnnunciCercaPadroneAdozione_COUNT();
    	return String.valueOf(caniAdozioneTot + annunciAdozioneTot);
    }
    
    @ModelAttribute("associazioneTot")
    protected String loadAssociazione(final HttpServletRequest request) {
    	return String.valueOf(getAssociazioneManager().getAssociazioniConInfoInserite() );
    }
    

    @RequestMapping(method = RequestMethod.GET)
    protected String indexGet(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	System.out.println("sono in IndexController GET");
        return "home"; 
    }


}
