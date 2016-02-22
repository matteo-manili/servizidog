package com.dogsitter.webapp.util;


import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 * 	Questa classe permette di posizionare le coordinate geografiche duplicate degli annunci, in cerchio.
 *  Per esempio se si hanno 30 annunci nella stessa latitudine e lingitudine, la classe disporrà 30 coordinate in cerchio in un punto equidistante dall'altro.
 *  La classe è necessaria per poter visualizzare gli annunci in una mappa, senza che si sovrappongano, ostacolando la visualizzazione.
 */

public final class AnnunciAdozione_CancellaAnnunciDuplicati {
	private static final Log log = LogFactory.getLog(AnnunciAdozione_CancellaAnnunciDuplicati.class);
	public static ApplicationContext contextDao = 
    		new ClassPathXmlApplicationContext("App-Database-Spring-Module.xml");
	
	public static AnnunciCercaPadroneAdozioneDao annunciAdozioneDao = (AnnunciCercaPadroneAdozioneDao) contextDao.getBean("AnnunciCercaPadroneAdozioneDao");
	
	public static AnnunciKijijiDogSitterDao annunciDogSitterDao = (AnnunciKijijiDogSitterDao) contextDao.getBean("AnnunciKijijiDogSitterDao");
	


	public static void inizio() {
		
		log.debug("@@@ INIZIO PROCEDURA AnnunciAdozione_CancellaAnnunciDuplicati...");
		
		//long annuncioTutti= annunciAdozioneDao.getAnnunciCercaPadroneAdozione_COUNT();
		//System.out.println("TATALI TUTTI= "+annuncioTutti);
		//---------------------------------------------------------------------
		
		List<String> annuncioAdozioneUnivoche = annunciAdozioneDao.getAnnunciCercaPadroneAdozione_DESCRIONI_UNIVOCHE();
		//System.out.println("TATALI DESCRIONI UNIVOCI= "+annuncioAdozioneUnivoche.size());
		Iterator<String> annuncioAdozioneUnivoche_ite = annuncioAdozioneUnivoche.iterator();
		
		int TOTALI_ELIMINATI = 0;
		
		while (annuncioAdozioneUnivoche_ite. hasNext()) {
			String descrizione = annuncioAdozioneUnivoche_ite.next();
			
			List<AnnunciCercaPadroneAdozione> adozioneDescrizione;
			adozioneDescrizione = annunciAdozioneDao.getAnnunciCercaPadroneAdozione_DESCRIZIONE(descrizione);
			Iterator<AnnunciCercaPadroneAdozione> adozioneDescrizione_ite = adozioneDescrizione.iterator();
			
			if(adozioneDescrizione.size() > 1){
				Map<Long, Date> arrAnnnunciDup = new HashMap<Long, Date>();
				while(adozioneDescrizione_ite.hasNext()){
					AnnunciCercaPadroneAdozione annuncioAdozione = adozioneDescrizione_ite.next();
					//System.out.println(annuncioAdozione.getIdAnnuncio() + " getDataPubblicazione= "+annuncioAdozione.getDataPubblicazione()+ " getDescrizione= "+annuncioAdozione.getDescrizione());
					arrAnnnunciDup.put(annuncioAdozione.getIdAnnuncio(), annuncioAdozione.getDataPubblicazione());
				}
				//System.out.println("ELIMINO......");
				Map<Long, Date> sortedMap = sortByComparator(arrAnnnunciDup);
				int i = 1;
				for (Map.Entry<Long, Date> entry : sortedMap.entrySet()) {
					if(i == 1){
						//System.out.println("[Key] : " + entry.getKey()  + " [Value] : " + entry.getValue() +" NON ELIMINATO");
					}else{
						//System.out.println("[Key] : " + entry.getKey()  + " [Value] : " + entry.getValue() +" ELIMINATO!");
						annunciAdozioneDao.eliminaAnnuncioAdozione(annunciAdozioneDao.get(entry.getKey())); 
						TOTALI_ELIMINATI ++;	
					}
					i ++;
				}
				//System.out.println("---------------------------------------------------------------");
			}
		}
		log.debug("@@@ FINE PROCEDURA AnnunciAdozione_CancellaAnnunciDuplicati TOTALI_ELIMINATI "+TOTALI_ELIMINATI+" [OK]");
	}
	
	

	private static Map<Long, Date> sortByComparator(Map<Long, Date> unsortMap) {

		// Convert Map to List
		List<Map.Entry<Long, Date>> list = new LinkedList<Map.Entry<Long, Date>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Long, Date>>() {
			public int compare(Map.Entry<Long, Date> o1, Map.Entry<Long, Date> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		
		Collections.reverse(list);

		// Convert sorted map back to a Map
		Map<Long, Date> sortedMap = new LinkedHashMap<Long, Date>();
		for (Iterator<Map.Entry<Long, Date>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Long, Date> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	
}
