package com.dogsitter;


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

public class Zzz_Pulisci_NOMI_IMMAGINI {
	private static final Log log = LogFactory.getLog(Zzz_Pulisci_NOMI_IMMAGINI.class);
	
	public static ApplicationContext contextDao = 
    		new ClassPathXmlApplicationContext("App-Database-Spring-Module.xml");
	
	public static AnnunciCercaPadroneAdozioneDao annunciAdozioneDao = (AnnunciCercaPadroneAdozioneDao) contextDao.getBean("AnnunciCercaPadroneAdozioneDao");
	
	public static AnnunciKijijiDogSitterDao annunciDogSitterDao = (AnnunciKijijiDogSitterDao) contextDao.getBean("AnnunciKijijiDogSitterDao");
	

	public static void main(String[] args) {
		

		List<AnnunciCercaPadroneAdozione> adozioneAnnunci = annunciAdozioneDao.getAnnunciCercaPadroneAdozione() ;
		Iterator<AnnunciCercaPadroneAdozione> adozioneAnnunci_ite;
		adozioneAnnunci_ite = adozioneAnnunci.iterator();
		System.out.println("SIZE distinctImmagine()= "+adozioneAnnunci.size());

		while (adozioneAnnunci_ite.hasNext()) {
			AnnunciCercaPadroneAdozione annunciImmagine = adozioneAnnunci_ite.next();

			if(annunciImmagine.getImmagine1() == null){
				//AnnunciCercaPadroneAdozione modificaAdozione = new AnnunciCercaPadroneAdozione();
				annunciImmagine.setNomeImmagine(null);
				annunciAdozioneDao.saveAnnuncioAdozioneNoFlush(annunciImmagine);
			}
		}
		
		
		List<AnnunciKijijiDogSitter> dogSitterAnnunci = annunciDogSitterDao.getAnnunciKijijiDogSitter() ;
		Iterator<AnnunciKijijiDogSitter> dogSitterAnnunci_ite;
		dogSitterAnnunci_ite = dogSitterAnnunci.iterator();
		System.out.println("SIZE distinctImmagine()= "+dogSitterAnnunci.size());

		while (dogSitterAnnunci_ite.hasNext()) {
			AnnunciKijijiDogSitter annunciImmagine = dogSitterAnnunci_ite.next();

			if(annunciImmagine.getImmagine1() == null){
				//AnnunciCercaPadroneAdozione modificaAdozione = new AnnunciCercaPadroneAdozione();
				annunciImmagine.setNomeImmagine(null);
				annunciDogSitterDao.saveAnnuncioDogSitterNoFlush(annunciImmagine);
			}
		}
		
		
		
		
		
		
		
		
    }
	
	

	
	
	
}
