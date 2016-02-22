package com.dogsitter.webapp.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dogsitter.Constants;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.Cane;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.Image;
import com.dogsitter.service.CaneManager;
import com.dogsitter.util.DateUtil;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@Controller
public class ChiamateAjaxController extends BaseFormController {


	
	private CaneManager caneManager;
	@Autowired
	public void setCaneManager(CaneManager caneManager) {
		this.caneManager = caneManager;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getActiveMap.json*")
	public @ResponseBody String getActiveMap(HttpServletRequest request) {
		System.out.println("ChiamateAjaxController getActiveMap");
		try{
			Iterator<DogSitter> dog_sitter_ite =  getDogSitterManager().getDogSitters().iterator();
			Iterator<DogHost> dog_host_ite =  getDogHostManager().getDogHosts().iterator();
			Iterator<Adozione> adozione_ite =  getAdozioneManager().getAdozioni().iterator();
			Iterator<Associazione> associazione_ite =  getAssociazioneManager().getAssociazioni(). iterator();
			Iterator<AnnunciKijijiDogSitter> annunci_dog_sitter_kijiji_ite =  getAnnunciKijijiDogSitterManager().getAnnunciKijijiDogSitter_RICERCA_MAPPA().iterator();
			Iterator<AnnunciCercaPadroneAdozione> annunci_adozione_cercaPadrone_ite =  getAnnunciCercaPadroneAdozioneManager().getAnnunciCercaPadroneAdozione_RICERCA_MAPPA().iterator();
			
			DogSitter dogsitter = null;
			DogHost doghost = null;
			Adozione adozione = null;
			Associazione associazione = null;
			AnnunciKijijiDogSitter annunciKijijiDogSitter = null;
			AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione = null;
			
			List<Map<String, Serializable>> list = new LinkedList<Map<String, Serializable>>();
			String jsonString = "";
			String context = request.getContextPath();
			
			//DOG SITTER
			while (dog_sitter_ite.hasNext()) {
				dogsitter = dog_sitter_ite.next();
				String tariffa = "";
				if(dogsitter.informazioniGeneraliInserite() ){
					JSONArray tags_list = new JSONArray();
					tags_list.add("ROLE_DOGSITTER");
					
					Map<String, Serializable> valueJson = new HashMap<String, Serializable>();
					valueJson.put ("tags", tags_list);
					Image image = getImageManager().getImmaginePrefeita( dogsitter.getUser().getId(), 
							getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGSITTER_ROLE).getId());
					if( image != null ){
						valueJson.put("img", context+"/thumbnail/"+image.getId() );
					}else{
						//valueJson.put("img", context+"/scripts/activmap/img/logo_x_mappa.png" );
						valueJson.put("img", context+Constants.IMMAGINE_SOSTITUTIVA );
					}
					valueJson.put("lng", dogsitter.getAddress().getLng());
					valueJson.put("lat", dogsitter.getAddress().getLat());
					valueJson.put("address", dogsitter.getAddress().dammiTutto() );
					valueJson.put("phone", "tel. "+dogsitter.getTelefono());
					
					if (dogsitter.getTariffa() != null)
						tariffa = "<br>"+ dogsitter.getTariffa()+"&#8364 h." ;

					valueJson.put("title", dogsitter.getTitolo()+tariffa);
					valueJson.put("url", dogsitter.getUrlProfilo());
					valueJson.put("urlTitle", "Visualizza profilo");
					valueJson.put("icon", "marker_dogsitter.png");
					list.add(valueJson);
				}
			}
			//ANNUNCI DOG SITTER KIJIJI
			while (annunci_dog_sitter_kijiji_ite.hasNext()) {
				annunciKijijiDogSitter = annunci_dog_sitter_kijiji_ite.next();

				String tariffa = "";
				JSONArray tags_list = new JSONArray();
				tags_list.add("ROLE_DOGSITTER");
				
				Map<String, Serializable> valueJson = new HashMap<String, Serializable>();
				valueJson.put ("tags", tags_list);
				
				if(annunciKijijiDogSitter.getNomeImmagine() != null){
					valueJson.put("img", context+Constants.PATH_THUMBNAIL_ANN_DOGSITTER+"/"+annunciKijijiDogSitter.getIdAnnuncio());
				}else{
					//valueJson.put("img", context+"/scripts/activmap/images/logo_x_mappa.png" );
					valueJson.put("img", context+Constants.IMMAGINE_SOSTITUTIVA );
				}
				
				valueJson.put("lng", annunciKijijiDogSitter.getLng());
				valueJson.put("lat", annunciKijijiDogSitter.getLat());
				valueJson.put("address", annunciKijijiDogSitter.getLuogo());
				valueJson.put("phone", "tel. "+annunciKijijiDogSitter.getTelefono());
				
				if (annunciKijijiDogSitter.getTariffa() != null)
					tariffa = "<br>"+ annunciKijijiDogSitter.getTariffa()+"&#8364 h." ;

				valueJson.put("title", annunciKijijiDogSitter.getNomeUtente()+tariffa);
				valueJson.put("url", annunciKijijiDogSitter.getUrlProfilo());
				valueJson.put("urlTitle", "Visualizza profilo");
				valueJson.put("icon", "marker_dogsitter.png");
				list.add(valueJson);
			}
			//DOG HOST
			while (dog_host_ite.hasNext()) {
				doghost = dog_host_ite.next();
				String tariffa = "";
				if(doghost.informazioniGeneraliInserite() ){
					JSONArray tags_list = new JSONArray();
					tags_list.add("ROLE_DOGHOST");
					Map<String, Serializable> valueJson = new HashMap<String, Serializable>();
					valueJson.put ("tags", tags_list);
					Image image = getImageManager().getImmaginePrefeita( doghost.getUser().getId(), 
							getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGHOST_ROLE).getId());
					if( image != null ){
						valueJson.put("img", context+"/thumbnail/"+image.getId() );
					}else{
						valueJson.put("img", context+Constants.IMMAGINE_SOSTITUTIVA );
					}
					valueJson.put("lng", doghost.getAddress().getLng());
					valueJson.put("lat", doghost.getAddress().getLat());
					valueJson.put("address", doghost.getAddress().dammiTutto() );
					valueJson.put("phone", "tel. "+doghost.getTelefono());
					
					if (doghost.getTariffa() != null)
						tariffa = "<br>"+ doghost.getTariffa()+"&#8364 h.";
					
					valueJson.put("title", doghost.getTitolo()+tariffa);
					valueJson.put("url", doghost.getUrlProfilo());
					valueJson.put("urlTitle", "Visualizza profilo");
					valueJson.put("icon", "marker_doghost.png");
					list.add(valueJson);
				}
			}
			//ADOZIONE
			while (adozione_ite.hasNext()) {
				adozione = adozione_ite.next();
				if(adozione.informazioniGeneraliInserite() && caneManager.getCaneByAdozione(adozione.getId()).size() > 0  ){
					JSONArray tags_list = new JSONArray();
					tags_list.add("ROLE_ADOZIONE");
					Map<String, Serializable> valueJson = new HashMap<String, Serializable>();
					valueJson.put ("tags", tags_list);
					Image image = getImageManager().getImmaginePrefeita( adozione.getUser().getId(), 
							getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ADOZIONE_ROLE).getId());
					if( image != null ){
						valueJson.put("img", context+"/thumbnail/"+image.getId() );
					}else{
						valueJson.put("img", context+Constants.IMMAGINE_SOSTITUTIVA );
					}
					Iterator<Cane> cane = caneManager.getCaneByAdozione(adozione.getId()).iterator();
					String caniSting = "";
					while (cane.hasNext() ){
						caniSting = caniSting + cane.next().getNomeCane();
						if (cane.hasNext())
							caniSting = caniSting + ", ";
					}
					valueJson.put("lng", adozione.getAddress().getLng());
					valueJson.put("lat", adozione.getAddress().getLat());
					valueJson.put("address", adozione.getAddress().dammiTutto() );
					valueJson.put("phone", "tel. "+adozione.getTelefono());
					valueJson.put("title", caniSting);
					valueJson.put("url", adozione.getUrlProfilo());
					valueJson.put("urlTitle", "Visualizza profilo");
					valueJson.put("icon", "marker_adozione.png");
					list.add(valueJson);
				}
			}
			//ANNUNCI ADOZIONE CERCA PADRONE
			while (annunci_adozione_cercaPadrone_ite.hasNext()) {
				annunciCercaPadroneAdozione = annunci_adozione_cercaPadrone_ite.next();
				
				//if(annunciCercaPadroneAdozione. getLat() > 0 && annunciCercaPadroneAdozione. getLat() > 0){
					JSONArray tags_list = new JSONArray();
					tags_list.add("ROLE_ADOZIONE");
					
					Map<String, Serializable> valueJson = new HashMap<String, Serializable>();
					valueJson.put ("tags", tags_list);
					
					if(annunciCercaPadroneAdozione.getNomeImmagine() != null){
						valueJson.put("img", context+Constants.PATH_THUMBNAIL_ANN_ADOZIONE+"/"+annunciCercaPadroneAdozione.getIdAnnuncio());
					}else{
						valueJson.put("img", context+Constants.IMMAGINE_SOSTITUTIVA );
					}
					valueJson.put("lng", annunciCercaPadroneAdozione.getLng());
					valueJson.put("lat", annunciCercaPadroneAdozione.getLat());
					valueJson.put("address", annunciCercaPadroneAdozione.getLuogo());
					valueJson.put("phone", "tel. "+annunciCercaPadroneAdozione.getTelefono());
					
					valueJson.put("title", StringUtils.abbreviate(annunciCercaPadroneAdozione.getTitoloCane(), 30));
					valueJson.put("url", annunciCercaPadroneAdozione.getUrlProfilo());
					valueJson.put("urlTitle", "Visualizza profilo");
					valueJson.put("icon", "marker_adozione.png");
					list.add(valueJson);
				//}
			}
			//ASSOCIAZIONE
			while (associazione_ite.hasNext()) {
				associazione = associazione_ite.next();
				if(associazione.informazioniGeneraliInserite() /*&& caneManager.getCaneByAssociazione(associazione.getId()).size() > 0 */  ){
					JSONArray tags_list = new JSONArray();
					tags_list.add("ROLE_ASSOCIAZIONE");
					Map<String, Serializable> valueJson = new HashMap<String, Serializable>();
					valueJson.put ("tags", tags_list);
					Image image = getImageManager().getImmaginePrefeita( associazione.getUser().getId(), 
							getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ASSOCIAZIONE_ROLE).getId());
					if( image != null ){
						valueJson.put("img", context+"/thumbnail/"+image.getId() );
					}else{
						valueJson.put("img", context+Constants.IMMAGINE_SOSTITUTIVA );
					}
					valueJson.put("lng", associazione.getAddress().getLng());
					valueJson.put("lat", associazione.getAddress().getLat());
					valueJson.put("address", associazione.getAddress().dammiTutto() );
					valueJson.put("phone", "tel. "+associazione.getTelefono());
					valueJson.put("title", associazione.getNome() );
					valueJson.put("url", associazione.getUrlProfilo());
					valueJson.put("urlTitle", "Visualizza profilo");
					valueJson.put("icon", "marker_associazioni.png");
					list.add(valueJson);
				}
			}
			
			
			jsonString = JSONValue.toJSONString(list);
			
			jsonString = jsonString.replace("marker_dogsitter.png", context+"/scripts/activmap/images/icons/marker_dogsitter.png");
			jsonString = jsonString.replace("marker_doghost.png", context+"/scripts/activmap/images/icons/marker_doghost.png");
			jsonString = jsonString.replace("marker_adozione.png", context+"/scripts/activmap/images/icons/marker_adozione.png");
			jsonString = jsonString.replace("marker_associazioni.png", context+"/scripts/activmap/images/icons/marker_associazioni.png");
			
			String inizo_stringa_json = "{ \"places\": ";
			String fine_stringa_json = " }";
			jsonString = inizo_stringa_json + jsonString + fine_stringa_json;
			
			return jsonString;
			
		}catch(Exception ee){
			
			String stringa_json = "";
			String inizo_stringa_json = "{ \"places\": [ ";
			String fine_stringa_json = " ]}";
			stringa_json = inizo_stringa_json + stringa_json + fine_stringa_json;
			
			ee.printStackTrace();
			
			return stringa_json;
		}
	}


	@RequestMapping(value = "/impostaTariffaDogSitter", method = RequestMethod.POST)
    public @ResponseBody String impostaTariffaDogSitter(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("ChiamateAjaxController impostaTariffaDogSitter");
    	try {
    		String stringTariffa = request.getParameter("tariffa");
    		stringTariffa = stringTariffa.replace(",", ".");
    		
    		Float floatTariffa = Float.parseFloat(stringTariffa);
    		DecimalFormat decform = new DecimalFormat("0.00");
    		decform.setMaximumFractionDigits(2);
    		stringTariffa = decform.format(floatTariffa);
    		
    		long idDogSitter = Long.parseLong(request.getParameter("idDogSitter"));
    		DogSitter dogSitter = getDogSitterManager().get(idDogSitter);
    		String esito = "";
	    	dogSitter.setTariffa( stringTariffa );
	    	dogSitter.setUltimaModifica(new Date());
    		DogSitter dogSitterok = getDogSitterManager().saveDogSitter(dogSitter);

    		//creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
    		esito = "Tariffa salvata "+ dogSitterok.getTariffa();
    		
    		return esito;
    	}catch(NumberFormatException nE) {
        	nE.printStackTrace();
            return "Iserisci un valore numerico";
        }
    	catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
	
	
	
	@RequestMapping(value = "/impostaInfoCasaDogHost", method = RequestMethod.POST)
    public @ResponseBody String impostaInfoCasaDogHost(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("ChiamateAjaxController impostaInfoCasaDogHost");
    	try {
    		
    		long idDogHost = Long.parseLong(request.getParameter("idDogHost"));
    		
    		DogHost dogHost = getDogHostManager().get(idDogHost);
    		String esito = "";
	    	
    		String metriQuadrati = request.getParameter("metriQuadrati");
    		dogHost.setMetriQuadrati(metriQuadrati);

    		String[] results = request.getParameterValues("checkInfoCasa");
	        if(results == null){
	        	results = new String[]{"VUOTO"};
	        }
	        
	        List<String> checkInfoCasa = Arrays.asList(results);  
	        String listCheckboxInfoCasa = checkInfoCasa.get(0);
	        
	        if (listCheckboxInfoCasa.indexOf("terrazza") != -1) {
	        	dogHost.setTerrazza(true);
	        } else {
	        	dogHost.setTerrazza(false);
	        }
	        if (listCheckboxInfoCasa.indexOf("giardino") != -1) {
	        	dogHost.setGiardino(true);
	        } else {
	        	dogHost.setGiardino(false);
	        }
	        if (listCheckboxInfoCasa.indexOf("animaliPresenti") != -1) {
	        	dogHost.setAnimaliPresenti(true);
	        } else {
	        	dogHost.setAnimaliPresenti(false);
	        }
    		
	        
	        dogHost.setUltimaModifica(new Date());
	        
    		getDogHostManager().saveDogHost(dogHost);
    		
    		//creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
    		esito = "Informazioni Salvate";
    		
    		return esito;
    	}catch(NumberFormatException nE) {
        	nE.printStackTrace();
            return "Iserisci un valore numerico";
        }
    	catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
	
	@RequestMapping(value = "/eliminaCaneAssociazione", method = RequestMethod.POST)
    public @ResponseBody String eliminaCaneAssociazione(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("ChiamateAjaxController eliminaCaneAssociazione");
    	try {

    		long idCane = Long.parseLong(request.getParameter("idCane"));
    		Cane cane = caneManager.get(idCane);
    		caneManager.eliminaCane(cane);
    		return "cane rimosso"; 
        }
    	catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
	
	@RequestMapping(value = "/eliminaCaneAdozione", method = RequestMethod.POST)
    public @ResponseBody String eliminaCaneAdozione(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("ChiamateAjaxController eliminaCaneAdozione");
    	try {
    		long idCane = Long.parseLong(request.getParameter("idCane"));
    		Cane cane = caneManager.get(idCane);
    		caneManager.eliminaCane(cane);
    		return "cane rimosso"; 
        }
    	catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
	
	
	@RequestMapping(value = "/impostaTariffaDogHost", method = RequestMethod.POST)
    public @ResponseBody String impostaTariffaDogHost(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("ChiamateAjaxController impostaTariffaDogHost");
    	try {
    		
    		String stringTariffa = request.getParameter("tariffa");
    		stringTariffa = stringTariffa.replace(",", ".");
    		Float floatTariffa = Float.parseFloat(stringTariffa);
    		DecimalFormat decform = new DecimalFormat("0.00");
    		decform.setMaximumFractionDigits(2);
    		stringTariffa = decform.format(floatTariffa);
    		
    		long idDogHost = Long.parseLong(request.getParameter("idDogHost"));
    		DogHost dogHost = getDogHostManager().get(idDogHost);
    		String esito = "";
	    	dogHost.setTariffa( stringTariffa );
	    	dogHost.setUltimaModifica(new Date());
    		DogHost dogHostok = getDogHostManager().saveDogHost(dogHost);
    		//creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
    		esito = "Tariffa salvata "+ dogHostok.getTariffa();
    		return esito;
    	}catch(NumberFormatException nE) {
        	nE.printStackTrace();
            return "Iserisci un valore numerico";
        }
    	catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
	
	
    @RequestMapping(value = "/impostaDateRangeDisponibilitaDogSitter", method = RequestMethod.POST)
    public @ResponseBody String impostaDateRangeDisponibilitaDogSitter(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("ChiamateAjaxController impostaDateRangeDisponibilitaDogSitter");
    	try {
    		long idDogSitter = Long.parseLong(request.getParameter("idDogSitter"));
    		DogSitter dogSitter = getDogSitterManager().get(idDogSitter);
    		String esito = "";
    		if (request.getParameter("dataRangeInizio") != null && request.getParameter("dataRangeInizio") != null){
	    		dogSitter.setDataInizioDisponib(DateUtil.convertStringToDate("dd-MM-yyyy",request.getParameter("dataRangeInizio")));
	    		dogSitter.setDataFineDisponib(DateUtil.convertStringToDate("dd-MM-yyyy",request.getParameter("dataRangeFine")));
    		}
    		dogSitter.setUltimaModifica(new Date());
    		DogSitter dogSitterok = getDogSitterManager().saveDogSitter(dogSitter);
    		//creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
    		esito = "Date salvate con successo: dal "+DateUtil.getDateTime("dd-MM-yyyy", dogSitterok.getDataInizioDisponib())+" al "+DateUtil.getDateTime("dd-MM-yyyy", dogSitterok.getDataFineDisponib());
    		return esito;
    		
    	} catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
	
    
    @RequestMapping(value = "/impostaDateRangeDisponibilitaDogHost", method = RequestMethod.POST)
    public @ResponseBody String impostaDateRangeDisponibilitaDogHost(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("ChiamateAjaxController impostaDateRangeDisponibilitaDogHost");
    	try {
    		long idDogHost = Long.parseLong(request.getParameter("idDogHost"));
    		DogHost dogHost = getDogHostManager().get(idDogHost);
    		String esito = "";
    		if (request.getParameter("dataRangeInizio") != null && request.getParameter("dataRangeInizio") != null){
	    		dogHost.setDataInizioDisponib(DateUtil.convertStringToDate("dd-MM-yyyy",request.getParameter("dataRangeInizio")));
	    		dogHost.setDataFineDisponib(DateUtil.convertStringToDate("dd-MM-yyyy",request.getParameter("dataRangeFine")));
    		}
    		dogHost.setUltimaModifica(new Date());
    		DogHost dogHostok = getDogHostManager().saveDogHost(dogHost);
    		//creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
    		esito = "Date salvate con successo: dal "+DateUtil.getDateTime("dd-MM-yyyy", dogHostok.getDataInizioDisponib())+" al "+DateUtil.getDateTime("dd-MM-yyyy", dogHostok.getDataFineDisponib());
    		return esito;
    	} catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
	
    
    //------------- PROVA AJAX ------------
    //---------------------------------
    
    /*
    @RequestMapping(value = "/ajaxtest*")
    public @ResponseBody String getTime() {
    	System.out.println("ChiamateAjaxController getTime");
        Random rand = new Random();
        float r = rand.nextFloat() * 100;
        String result = "<br>Next Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
        return result;
    }
    
    
    @RequestMapping(value = "/getComune*")
	public @ResponseBody List<String> getComune(@RequestParam String term) {
    	System.out.println("ChiamateAjaxController getComune");
		if (term==null){
			term="";
		}
		List<String> listComuniString = new ArrayList<String>();
		try{
			listComuniString = getZonaManager().dammiComuni(term);
			return listComuniString;
		}catch(Exception ee){
			ee.getCause();
			return listComuniString;
		}
	}
	
    
	@RequestMapping(value = "/getTags*")
	public @ResponseBody List<String> getTags(@RequestParam String term) {
		System.out.println("ChiamateAjaxController getTags");
		if (term==null){
			term="";
		}
		List<String> listPeopleString = new ArrayList<String>();
		List<Person> listPerson;
		try{
    		listPerson = getPersonManager().getPersonByNameJson(term);
    		if(listPerson.size() > 0){
	    		Iterator<Person> iterator = listPerson.iterator();
		        while(iterator.hasNext()){
		        	String s = iterator.next().getFirstName();
		        	listPeopleString.add(s);
				}
    		}
		return listPeopleString;
		
		}catch(Exception ee){
			ee.getCause();
			return listPeopleString;
		}
	}
	

	@RequestMapping(value = "/getComuneProvRegione*")
	public @ResponseBody List<String> getComuneProvRegione(@RequestParam String term) {
		System.out.println("ChiamateAjaxController getComuneProvRegione");
		if (term==null){
			term="";
		}
		List<String> listComuniString = new ArrayList<String>();
		try{
			listComuniString = getZonaManager().getZonaRicercaGoogleMap(term);
			return listComuniString;
		}catch(Exception ee){
			ee.getCause();
			return listComuniString;
		}
	}
	
    @RequestMapping(value = "/get_ceos_listXXXXXXX")
    public @ResponseBody List<String> getCountryList(@RequestParam("term") String query) {
		System.out.println("ChiamateAjaxController getCountryList");
		List<String> listPeopleString = new ArrayList<String>();
		List<Person> listPerson;
		try{
    		listPerson = getPersonManager().getTuttiPerson();
    		if(listPerson.size() > 0){
	    		Iterator<Person> iterator = listPerson.iterator();
		        while(iterator.hasNext()){
		        	String s = iterator.next().getFirstName();
		        	listPeopleString.add(s);
				}
    		}
		return listPeopleString;
		
		}catch(Exception ee){
			ee.getCause();
			return listPeopleString;
		}
    }
    
    */
    
}