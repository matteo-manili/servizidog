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
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.Image;
import com.dogsitter.webapp.util.BuildRicercaJSONObject;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping(value = "/dogsitter*", method = RequestMethod.GET)
public class ListDogSitterController extends BaseFormController {
	
	
	@RequestMapping(method = RequestMethod.GET)
    protected ModelAndView dogSitterGet(final HttpServletRequest request, final HttpServletResponse response) {
		try{
	        return new ModelAndView("listDogSitter");
		}catch(Exception exc){
			return new ModelAndView("redirect:/");
		}
    }
	
	
	@ModelAttribute("DogSitterJson")
	protected List<JSONObject> ricercaDogSitterJson(final HttpServletRequest request) throws Exception { 
	
		String context = request.getContextPath();
		List<JSONObject> list = new LinkedList<JSONObject>();
		final Locale locale = request.getLocale();
		final String servizioDogSitter = getText("dogsitter.title", locale);
		BuildRicercaJSONObject brJSONOBJECT = new BuildRicercaJSONObject();
		
		//DOG SITTER
		Iterator<DogSitter> dog_sitter_ite = getDogSitterManager().getDogSitters().iterator();
		DogSitter dogsitter = null;
		while (dog_sitter_ite.hasNext()) {
			dogsitter = new DogSitter();
			dogsitter = dog_sitter_ite.next();
			if(dogsitter.informazioniGeneraliInserite() ){
				//immagine
				Image image = getImageManager().getImmaginePrefeita( dogsitter.getUser().getId(), 
						getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGSITTER_ROLE).getId());
				
				list.add( brJSONOBJECT.DogSitterBuildJSONObject(dogsitter, image, servizioDogSitter, context) );
			}
		}
		
		//ANNUNCI KIJJI DOG SITTER 
		Iterator<AnnunciKijijiDogSitter> annunci_dog_sitter_ite = getAnnunciKijijiDogSitterManager().getAnnunciKijijiDogSitter_RICERCA().iterator();
		AnnunciKijijiDogSitter annunciKijijiDogSitter = null;
		while (annunci_dog_sitter_ite.hasNext()) {
			annunciKijijiDogSitter = new AnnunciKijijiDogSitter();
			annunciKijijiDogSitter = annunci_dog_sitter_ite.next();
			if(annunciKijijiDogSitter.infoGeneraliPerRicerca_e_Sitemap_e_Immagini() ){
				//immagine
				//Image image = getImageManager().getImmaginePrefeita( dogsitter.getUser().getId(), 
						//getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGSITTER_ROLE).getId());
				
				list.add( brJSONOBJECT. AnnunciKijijiDogSitterBuildJSONObject(annunciKijijiDogSitter, servizioDogSitter, context) );
			}
		}

		return list;

	}
	
	
	@ModelAttribute("DogSitterTot")
    protected String loadDogSitterTot(final HttpServletRequest request) {
        	int totale = getDogSitterManager().getDogSittersConInfoInserite();
            return String.valueOf(totale);
        }
	
	
}
