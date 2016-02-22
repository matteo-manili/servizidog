package com.dogsitter.webapp.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dogsitter.Constants;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.Cane;
import com.dogsitter.model.Image;
import com.dogsitter.service.CaneManager;
import com.dogsitter.webapp.util.BuildRicercaJSONObject;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping(value = "/adozione*", method = RequestMethod.GET)
public class ListAdozioneController extends BaseFormController {
	
	private CaneManager caneManager;
	@Autowired
	public void setCaneManager(CaneManager caneManager) {
		this.caneManager = caneManager;
	}
	
	@RequestMapping(method = RequestMethod.GET)
    protected ModelAndView adozioneGet(final HttpServletRequest request, final HttpServletResponse response) {
		try{
	        return new ModelAndView("listAdozione");
		}catch(Exception exc){
			return new ModelAndView("redirect:/");
		}
    }
	
	
	@ModelAttribute("AdozioneJson")
	protected List<JSONObject> ricercaAdozioneJson(final HttpServletRequest request) throws Exception { 
		String context = request.getContextPath();
		List<JSONObject> list = new LinkedList<JSONObject>();
		final Locale locale = request.getLocale();
		final String servizioAdozione = getText("adozione.adozionecane.title", locale);
		BuildRicercaJSONObject brJSONOBJECT = new BuildRicercaJSONObject();
		
		//ADOZIONE
		Iterator<Adozione> adozione_ite =  getAdozioneManager().getAdozioni().iterator();
		Adozione adozione = null;
		while (adozione_ite.hasNext()) {
			adozione = new Adozione();
			adozione = adozione_ite.next();
			if(adozione.informazioniGeneraliInserite() && caneManager.getCaneByAdozione(adozione.getId()).size() > 0  ){
				
				//image
				Image image = getImageManager().getImmaginePrefeita( adozione.getUser().getId(), 
						getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ADOZIONE_ROLE).getId());
				
				//nome cane
				Iterator<Cane> cane = caneManager.getCaneByAdozione(adozione.getId()).iterator();
				
				list.add( brJSONOBJECT.AdozioneBuildJSONObject(adozione, image, cane, servizioAdozione, context) );
			}
		}
		
		//ANNUNCI CERCAPADRONE ADOZIONE
		Iterator<AnnunciCercaPadroneAdozione> annunci_adozione_ite = getAnnunciCercaPadroneAdozioneManager().getAnnunciCercaPadroneAdozione_RICERCA().iterator();
		while (annunci_adozione_ite.hasNext()) {
			AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione = annunci_adozione_ite.next();
				list.add( brJSONOBJECT. AnnunciCercaPadroneAdozioneBuildJSONObject(annunciCercaPadroneAdozione, servizioAdozione, context) );
		}

		return list;

	}
	
	
	@ModelAttribute("AdozioneTot")
    protected String loadAdozioneTot(final HttpServletRequest request) {
        	int totale = caneManager.getTotaleCaniByAdozione();
            return String.valueOf(totale);
        }
	
	
}
