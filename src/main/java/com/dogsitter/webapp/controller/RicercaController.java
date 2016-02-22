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
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.Cane;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.Image;
import com.dogsitter.model.LabelValue;
import com.dogsitter.service.CaneManager;
import com.dogsitter.webapp.util.BuildRicercaJSONObject;


/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping("/ricerca*")
public class RicercaController extends BaseFormController {
	
	
	private CaneManager caneManager;
	@Autowired
	public void setCaneManager(CaneManager caneManager) {
		this.caneManager = caneManager;
	}
	
	
	public RicercaController() {
		setCancelView("redirect:/");
	}
	

	
	@ModelAttribute("serviziJson")
	protected List<JSONObject> ricercaServiziJson(final HttpServletRequest request) {
	
		String context = request.getContextPath();
		List<JSONObject> list = new LinkedList<JSONObject>();
		final Locale locale = request.getLocale();
		final String servizioDogSitter = getText("dogsitter.title", locale);
		final String servizioDogHost = getText("doghost.title", locale);
		final String servizioAdozione = getText("adozione.adozionecane.title", locale);
		final String servizioAssociazione = getText("associazione.title", locale);
		
		//getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGSITTER_ROLE).getDescription();
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
		while (annunci_dog_sitter_ite.hasNext()) {
			AnnunciKijijiDogSitter annunciKijijiDogSitter = annunci_dog_sitter_ite.next();
			list.add( brJSONOBJECT. AnnunciKijijiDogSitterBuildJSONObject(annunciKijijiDogSitter, servizioDogSitter, context) );
		}
		
		//DOG HOST
    	Iterator<DogHost> dog_host_ite = getDogHostManager().getDogHosts().iterator();
		while (dog_host_ite.hasNext()) {
			DogHost doghost = dog_host_ite.next();
			if(doghost.informazioniGeneraliInserite() ){
				
				Image image = getImageManager().getImmaginePrefeita( doghost.getUser().getId(), 
						getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGHOST_ROLE).getId());
				list.add( brJSONOBJECT.DogHostBuildJSONObject(doghost, image, servizioDogHost, context) );
			}
		}
		
		//ADOZIONE
		Iterator<Adozione> adozione_ite =  getAdozioneManager().getAdozioni().iterator();
		while (adozione_ite.hasNext()) {
			Adozione adozione = adozione_ite.next();
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
			list.add( brJSONOBJECT. AnnunciCercaPadroneAdozioneBuildJSONObject(annunciCercaPadroneAdozione, servizioAdozione, context));
		}
		
		//ASSOCIAZIONE
		Iterator<Associazione> associazione_ite =  getAssociazioneManager().getAssociazioni().iterator();
		while (associazione_ite.hasNext()) {
			Associazione associazione = associazione_ite.next();
			// && caneManager.getCaneByAssociazione(associazione.getId()).size() > 0
			if(associazione.informazioniGeneraliInserite()   ){
				Image image = getImageManager().getImmaginePrefeita( associazione.getUser().getId(), 
						getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ASSOCIAZIONE_ROLE).getId());
				list.add( brJSONOBJECT.AssociazioneBuildJSONObject(associazione, image, servizioAssociazione, context) );
			}
		}
		
    return list;

	}
	
	
	
	@ModelAttribute(Constants.AVAILABLE_ROLES_SERVIZIDOG)
	public List<LabelValue> checkServizi4Ricerca(HttpServletRequest request) {
		return super.checkServizi4Ricerca(request);
	}
	
	
	@ModelAttribute("serviziTot")
    protected String loadDogSitterTot(final HttpServletRequest request) {
        	Integer totAnn = (int) (long) getAnnunciKijijiDogSitterManager().getAnnunciKijijiDogSitter_COUNT();
        	int totDogSitter = getDogSitterManager().getDogSittersConInfoInserite();
        	int togDogHost = getDogHostManager().getDogHostConInfoInserite();
        	int caniAdozioneTot = caneManager.getTotaleCaniByAdozione();
        	Integer annunciAdozioneTot = (int) (long) getAnnunciCercaPadroneAdozioneManager().getAnnunciCercaPadroneAdozione_COUNT();
        	int totAssociazioni = getAssociazioneManager().getAssociazioniConInfoInserite();
        	int totale = totAnn + totDogSitter + togDogHost + caniAdozioneTot + annunciAdozioneTot + totAssociazioni;
            return String.valueOf(totale);
        }
	
	
	@RequestMapping(method = RequestMethod.GET)
    protected ModelAndView ricercaGet(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
		System.out.println("RicercaController GET");
    	
		return new ModelAndView("ricerca");
		
		//return new ModelAndView("ricercaStream");

    }
	
	
	/*
	@RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(final HttpServletRequest request, final HttpServletResponse response){
		System.out.println("RicercaController POST");
		return new ModelAndView("ricerca");
	}
	*/
	






}