package com.dogsitter.webapp.util;


import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.util.util_BUKOWSKI;
import com.google.code.geocoder.model.GeocodeResponse;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 * 	Questa classe permette di posizionare le coordinate geografiche duplicate degli annunci, in cerchio.
 *  Per esempio se si hanno 30 annunci nella stessa latitudine e lingitudine, la classe disporrà 30 coordinate in cerchio in un punto equidistante dall'altro.
 *  La classe è necessaria per poter visualizzare gli annunci in una mappa, senza che si sovrappongano, ostacolando la visualizzazione.
 */

public class OrdinaCoordinateCerchio_Annunci {
	private static final Log log = LogFactory.getLog(OrdinaCoordinateCerchio_Annunci.class);
	
	public static ApplicationContext contextDao = 
    		new ClassPathXmlApplicationContext("App-Database-Spring-Module.xml");
	
	public static AnnunciCercaPadroneAdozioneDao annunciAdozioneDao = (AnnunciCercaPadroneAdozioneDao) contextDao.getBean("AnnunciCercaPadroneAdozioneDao");
	
	public static AnnunciKijijiDogSitterDao annunciDogSitterDao = (AnnunciKijijiDogSitterDao) contextDao.getBean("AnnunciKijijiDogSitterDao");
	

	public static void main(String[] args) {
		
		inizio("ADOZIONE");
    }
	
	
	
	
	public static void inizio(String TABELLA){

		System.out.println("@@@@@@ INIZIO PROCEDURA OrdinaCoordinateCerchio_Annunci "+TABELLA+"...");
		
		Iterator<String> distinctLuogo_ite;
		if(TABELLA.equals("ADOZIONE")){
			List<String> distinctLuogo = annunciAdozioneDao.getAnnunciCercaPadroneAdozione_VALORI_DISTINCT() ;
			distinctLuogo_ite = distinctLuogo.iterator();
			//System.out.println("SIZE getAnnunciCercaPadroneAdozione_VALORI_DISTINCT()= "+distinctLuogo.size());
			
		}else if(TABELLA.equals("DOGSITTER")){
			List<String> distinctLuogo = annunciDogSitterDao.getAnnunciKijijiDogSitter_VALORI_DISTINCT();
			distinctLuogo_ite = distinctLuogo.iterator();
			//System.out.println("SIZE getAnnunciKijijiDogSitter_VALORI_DISTINCT()= "+distinctLuogo.size());
			
		}else{
			distinctLuogo_ite = null;
		}
		
		
		if(distinctLuogo_ite != null){
			List<AnnunciCercaPadroneAdozione> annAdozioneLuogoDuplicati = null;
			List<AnnunciKijijiDogSitter> annDogSitterLuogoDuplicati = null;
			while (distinctLuogo_ite.hasNext()) {
				String luogo = distinctLuogo_ite.next();
				//System.out.println("-------------citta------------------");
				//System.out.println(luogo);
				
				if(TABELLA.equals("ADOZIONE")){
					annAdozioneLuogoDuplicati = annunciAdozioneDao.getAnnunciCercaPadroneAdozione_LUOGO(luogo);
				}else{
					annDogSitterLuogoDuplicati = annunciDogSitterDao.getAnnunciKijijiDogSitter_LUOGO(luogo);
				}
					
				if(TABELLA.equals("ADOZIONE")){
					if(annAdozioneLuogoDuplicati!=null && annAdozioneLuogoDuplicati.size() > 1){
						try{
						GeocodeResponse geocoderResponse = util_BUKOWSKI.prendiInfo_GEO_GOOGLE(luogo);
							if (geocoderResponse != null){
		
								double latCitta = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue();
								double lngCitta = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue();
		
								aggiustaCoordinateAdozioneDUPLICATE(annAdozioneLuogoDuplicati, latCitta, lngCitta);
							}
						}catch(IndexOutOfBoundsException dd){
							//dd.printStackTrace();
							//System.out.println("IndexOutOfBoundsException coordinate google");
						}
					}
				}else{
					if(annDogSitterLuogoDuplicati!=null && annDogSitterLuogoDuplicati.size() > 1){
						try{
							GeocodeResponse geocoderResponse = util_BUKOWSKI.prendiInfo_GEO_GOOGLE(luogo);
							if (geocoderResponse != null){
		
								double latCitta = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue();
								double lngCitta = geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue();
		
								aggiustaCoordinateDogSitterDUPLICATE(annDogSitterLuogoDuplicati, latCitta, lngCitta);
							}
						}catch(IndexOutOfBoundsException dd){
							//dd.printStackTrace();
							//System.out.println("IndexOutOfBoundsException coordinate google");
						}
					}
				}
			}
		}
		
		System.out.println("@@@@@@ FINE PROCEDURA OrdinaCoordinateCerchio_Annunci "+TABELLA+" [OK]");
	}
	
	
	
	public static void aggiustaCoordinateAdozioneDUPLICATE(List<AnnunciCercaPadroneAdozione> annuncioLuogoDuplicati, double latCitta, double lngCitta){
		
		Iterator<AnnunciCercaPadroneAdozione> annLuogoDuplicati_ite = annuncioLuogoDuplicati.iterator();
		
		int annunciTotali = annuncioLuogoDuplicati.size();
		double angoloGrado360 = 360 / (annunciTotali);
		double distanzaCentroCitta = annunciTotali * 0.001;
		
		//System.out.println("...latCitta= "+latCitta +" lngCitta= "+lngCitta);
		//System.out.println("size= "+annuncioLuogoDuplicati.size());
		//System.out.println("angoloGrado360= "+angoloGrado360);
		//System.out.println("distanzaCentroCitta= "+distanzaCentroCitta); 
		int iesimoGrado = 1;
		
		if(latCitta > 0 && lngCitta > 0){
			while (annLuogoDuplicati_ite.hasNext()) {
				AnnunciCercaPadroneAdozione annuncioLuogo = annLuogoDuplicati_ite.next();
				Long idAnnuncio = annuncioLuogo.getIdAnnuncio();
				AnnunciCercaPadroneAdozione annuncioModificato = annunciAdozioneDao.get(idAnnuncio);

				double gradoITE = angoloGrado360 * iesimoGrado;
				//distanzaCentroCitta = distanzaCentroCitta + 0.002; // MODIFICARE DA CERCHIO A SPIRALE !!!! (ABBASSANDO GRADUALMENTE LA DISTANZA CITTA
				//System.out.println("gradoITE= "+gradoITE);
				double x1 = distanzaCentroCitta * Math.cos(gradoITE * Math.PI / 180) + latCitta;
				double y1 = distanzaCentroCitta * Math.sin(gradoITE * Math.PI / 180) + lngCitta;
				
				
				annuncioModificato.setLat(x1);
				annuncioModificato.setLng(y1);
				
				annunciAdozioneDao.saveAnnuncioAdozioneNoFlush(annuncioModificato);
				
				//System.out.println(x1+","+y1 );
				iesimoGrado = iesimoGrado + 1;
			}
		}
	}

	public static void aggiustaCoordinateDogSitterDUPLICATE(List<AnnunciKijijiDogSitter> annuncioLuogoDuplicati, double latCitta, double lngCitta){
		
		Iterator<AnnunciKijijiDogSitter> annLuogoDuplicati_ite = annuncioLuogoDuplicati.iterator();
		
		int annunciTotali = annuncioLuogoDuplicati.size();
		double angoloGrado360 = 360 / (annunciTotali);
		double distanzaCentroCitta = annunciTotali * 0.001;
		
		//System.out.println("...latCitta= "+latCitta +" lngCitta= "+lngCitta);
		//System.out.println("size= "+annuncioLuogoDuplicati.size());
		//System.out.println("angoloGrado360= "+angoloGrado360);
		int iesimoGrado = 1;
		
		if(latCitta > 0 && lngCitta > 0){
			while (annLuogoDuplicati_ite.hasNext()) {
				AnnunciKijijiDogSitter annuncioLuogo = annLuogoDuplicati_ite.next();
				Long idAnnuncio = annuncioLuogo.getIdAnnuncio();
				AnnunciKijijiDogSitter annuncioModificato = annunciDogSitterDao.get(idAnnuncio);
				
				double gradoITE = angoloGrado360 * iesimoGrado;
				//System.out.println("gradoITE= "+gradoITE);
				double x1 = distanzaCentroCitta * Math.sin(gradoITE * Math.PI / 180) + latCitta;
				double y1 = distanzaCentroCitta * Math.cos(gradoITE * Math.PI / 180) + lngCitta;
				
				annuncioModificato.setLat(x1);
				annuncioModificato.setLng(y1);
				
				annunciDogSitterDao.saveAnnuncioDogSitterNoFlush(annuncioModificato);
				
				//System.out.println(x1+","+y1 );
				iesimoGrado = iesimoGrado + 1;
			}
		}
	}
	
}
