package com.dogsitter.webapp.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dogsitter.Constants;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.Image;
import com.dogsitter.webapp.util.BuildRicercaJSONObject;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping(value = "/associazione*", method = RequestMethod.GET)
public class ListAssociazioneController extends BaseFormController {
	
	
	@RequestMapping(method = RequestMethod.GET)
    protected ModelAndView associazioneGet(final HttpServletRequest request, final HttpServletResponse response) {
		try{
	        return new ModelAndView("listAssociazione");
		}catch(Exception exc){
			return new ModelAndView("redirect:/");
		}
    }
	
	
	@ModelAttribute("AssociazioneJson")
	protected List<JSONObject> ricercaAssociazioneJson(final HttpServletRequest request) throws Exception { 
	
		String context = request.getContextPath();
		List<JSONObject> list = new LinkedList<JSONObject>();
		final Locale locale = request.getLocale();
		final String servizioAssociazione = getText("associazione.title", locale);
		BuildRicercaJSONObject brJSONOBJECT = new BuildRicercaJSONObject();
		
		//ASSOCIAZIONE
		Iterator<Associazione> associazione_ite =  getAssociazioneManager().getAssociazioni().iterator();
		Associazione associazione = null;
		while (associazione_ite.hasNext()) {
			associazione = new Associazione();
			associazione = associazione_ite.next();
			if(associazione.informazioniGeneraliInserite() /*&& caneManager.getCaneByAssociazione(associazione.getId()).size() > 0 */  ){
				
				Image image = getImageManager().getImmaginePrefeita( associazione.getUser().getId(), 
						getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ASSOCIAZIONE_ROLE).getId());
				
				list.add( brJSONOBJECT.AssociazioneBuildJSONObject(associazione, image, servizioAssociazione, context) );
			}
		}

		return list;

	}
	
	
	@ModelAttribute("AssociazioneTot")
    protected String loadAssociazioneTot(final HttpServletRequest request) {
        	int totale = getAssociazioneManager().getAssociazioniConInfoInserite();
            return String.valueOf(totale);
        }
	
	
}
