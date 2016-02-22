package com.dogsitter.webapp.util;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;

import com.dogsitter.Constants;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.Cane;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.Image;
import com.dogsitter.util.CapitalizeDescription;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class BuildRicercaJSONObject {

	public BuildRicercaJSONObject() {
		// TODO Auto-generated constructor stub
	}
	public static String nome = new String(); public static float tariffa = 0;
	public static String indirizzo = new String(); public static String descrizione = new String(); 
	public static String telefono = new String(); public static String urlProfilo = new String(); 
	public static String img = new String(); public static String servizio = new String();
	
	@SuppressWarnings("unchecked")
	public static JSONObject RicercaJSONObject(){
		JSONObject jO = new JSONObject();
		jO.put("nome", nome);
		jO.put("descrizione", descrizione);
		jO.put("indirizzo", indirizzo);
		jO.put("telefono", telefono);
		jO.put("urlProfilo", urlProfilo);
		jO.put("img", img);
		jO.put("servizio", servizio);
		jO.put("tariffa", tariffa);
		return jO;
	}
	
	
	public JSONObject DogSitterBuildJSONObject(DogSitter dogSitter, Image image, String servizio, String context){
		//nome
		nome = dogSitter.getTitolo();
		//tariffa
		if (dogSitter.getTariffa() != null && !dogSitter.getTariffa().equals("")){
			String strNumb =  dogSitter.getTariffa().replace(',', '.');
			tariffa = Float.parseFloat(strNumb);
		}else{
			tariffa = Float.parseFloat(Constants.PREZZO_MEDIO_DOGSITTER.replace(",", "."));
		}
		//indirizzo
		indirizzo = dogSitter.getAddress().dammiTutto();
		//descrizione
		descrizione = StringUtils.abbreviate(dogSitter.getDescrizione(), 60);
		descrizione = CapitalizeDescription.LettereMaiuscoleDopoilPunto(descrizione);
		//telefono
		if(!dogSitter.getTelefono().equals("")){
			telefono = dogSitter.getTelefono();
		}else{
			telefono = "non/dispobile";
		}
		//url profilo
		urlProfilo = dogSitter.getUrlProfilo();
		
		if(image != null){
			img = context+"/thumbnail/"+image.getId();
		}else{
			img = context+Constants.IMMAGINE_SOSTITUTIVA;
		}
		BuildRicercaJSONObject.servizio = servizio;
		
		return RicercaJSONObject();
	}
	 
	public JSONObject AnnunciKijijiDogSitterBuildJSONObject(AnnunciKijijiDogSitter annunciKijijiDogSitter, String servizio, String context){
		//nome
		nome = annunciKijijiDogSitter.getNomeUtente();
		//tariffa
		if (annunciKijijiDogSitter.getTariffa() != null && !annunciKijijiDogSitter.getTariffa().equals("")){
			String strNumb =  annunciKijijiDogSitter.getTariffa().replace(',', '.');
			tariffa = Float.parseFloat(strNumb);
		}else{
			tariffa = Float.parseFloat(Constants.PREZZO_MEDIO_DOGSITTER.replace(",", "."));
		}
		//indirizzo
		indirizzo = annunciKijijiDogSitter.getLuogo();
		//descrizione
		descrizione = StringUtils.abbreviate(annunciKijijiDogSitter.getDescrizione(), 60);
		descrizione = CapitalizeDescription.LettereMaiuscoleDopoilPunto(descrizione);
		//telefono
		if(!annunciKijijiDogSitter.getTelefono().equals("")){
			telefono = annunciKijijiDogSitter.getTelefono();
		}else{
			telefono = "non/dispobile";
		}
		//url profilo
		urlProfilo = annunciKijijiDogSitter.getUrlProfilo();
		
		if(annunciKijijiDogSitter.getNomeImmagine() != null){
			img = context+Constants.PATH_THUMBNAIL_ANN_DOGSITTER+"/"+annunciKijijiDogSitter.getIdAnnuncio();
		}else{
			img = context+Constants.IMMAGINE_SOSTITUTIVA;
		}

		BuildRicercaJSONObject.servizio = servizio;
		return RicercaJSONObject();
	}
	
	
	public JSONObject DogHostBuildJSONObject(DogHost dogHost, Image image, String servizio, String context){
		//nome
		nome = dogHost.getTitolo();
		//tariffa
		if (dogHost.getTariffa() != null && !dogHost.getTariffa().equals("")){
			String strNumb =  dogHost.getTariffa().replace(',', '.');
			tariffa = Float.parseFloat(strNumb);
		}else{
			tariffa = Float.parseFloat(Constants.PREZZO_MEDIO_DOGHOST.replace(",", "."));
		}
		indirizzo = dogHost.getAddress().dammiTutto();
		descrizione = StringUtils.abbreviate(dogHost.getDescrizione(), 60);
		descrizione = CapitalizeDescription.LettereMaiuscoleDopoilPunto(descrizione);
		if(!dogHost.getTelefono().equals("")){
			telefono = dogHost.getTelefono();
		}else{
			telefono = "non/dispobile";
		}
		urlProfilo = dogHost.getUrlProfilo();
		
		if(image != null){
			img = context+"/thumbnail/"+image.getId();
		}else{
			img = context+Constants.IMMAGINE_SOSTITUTIVA;
		}
		BuildRicercaJSONObject.servizio = servizio;
		
		return RicercaJSONObject();
	}

	public JSONObject AdozioneBuildJSONObject(Adozione adozione, Image image, Iterator<Cane> cane, String servizio, String context){
		//immagine
		if( image != null ){
			img = context+"/thumbnail/"+image.getId();
		}else{
			img = context+Constants.IMMAGINE_SOSTITUTIVA;
		}
		//nome cane
		nome = "";
		while (cane.hasNext() ){
			nome = nome + cane.next().getNomeCane();
			if (cane.hasNext())
				nome = nome + ", ";
		}
		nome = StringUtils.abbreviate(nome, 15);
		
		indirizzo = adozione.getAddress().dammiTutto();
		
		if(!adozione.getTelefono().equals("")){
			telefono = adozione.getTelefono();
		}else{
			telefono = "non/dispobile";
		}
		urlProfilo = adozione.getUrlProfilo();
		tariffa = Float.parseFloat("0.0");
		//descrizione
		descrizione = StringUtils.abbreviate(adozione.getDescrizione(), 60);
		descrizione = CapitalizeDescription.LettereMaiuscoleDopoilPunto(descrizione);
		BuildRicercaJSONObject.servizio = servizio;
		
		return RicercaJSONObject();
	}
	
	public JSONObject AnnunciCercaPadroneAdozioneBuildJSONObject(AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione, String servizio, String context){
		//nome
		nome = annunciCercaPadroneAdozione.getTitoloCane() ;
		//tariffa
		tariffa = Float.parseFloat("0.0");
		//indirizzo
		indirizzo = annunciCercaPadroneAdozione.getLuogo();
		//descrizione
		descrizione = StringUtils.abbreviate(annunciCercaPadroneAdozione.getDescrizione(), 60);
		descrizione = CapitalizeDescription.LettereMaiuscoleDopoilPunto(descrizione);
		//telefono
		if(!annunciCercaPadroneAdozione.getTelefono().equals("")){
			telefono = annunciCercaPadroneAdozione.getTelefono();
		}else{
			telefono = "non/dispobile";
		}
		//url profilo
		urlProfilo = annunciCercaPadroneAdozione.getUrlProfilo();
		
		if(annunciCercaPadroneAdozione.getNomeImmagine() != null){
			img = context+Constants.PATH_THUMBNAIL_ANN_ADOZIONE+"/"+annunciCercaPadroneAdozione.getIdAnnuncio();
		}else{
			img = context+Constants.IMMAGINE_SOSTITUTIVA;
		}
		BuildRicercaJSONObject.servizio = servizio;
		return RicercaJSONObject();
	}
	
	public JSONObject AssociazioneBuildJSONObject(Associazione associazione, Image image, String servizio, String context){
		if( image != null ){
			img = context+"/thumbnail/"+image.getId();
		}else{
			img = context+Constants.IMMAGINE_SOSTITUTIVA;
		}
		indirizzo = associazione.getAddress().dammiTutto();
		if(!associazione.getTelefono().equals("")){
			telefono = associazione.getTelefono();
		}else{
			telefono = "non/dispobile";
		}
		nome = associazione.getNome();
		urlProfilo = associazione.getUrlProfilo();
		tariffa = Float.parseFloat("0.0");
		//descrizione
		descrizione = StringUtils.abbreviate(associazione.getDescrizione(), 60);
		descrizione = CapitalizeDescription.LettereMaiuscoleDopoilPunto(descrizione);
		BuildRicercaJSONObject.servizio = servizio;
		
		return RicercaJSONObject();
	}
	

}