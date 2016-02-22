package com.dogsitter.webapp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.dao.GestioneApplicazioneDao;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.model.GestioneApplicazione;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public final class Attiva_Disattiva_Annunci {
	
	private static final Log log = LogFactory.getLog(Attiva_Disattiva_Annunci.class);
	
	public static DateFormat dfStr = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
	
	public static void cercaPadrone( AnnunciCercaPadroneAdozioneDao annunciDao, GestioneApplicazioneDao gestioneAppDao ){
		
		log.debug("@@@ INIZIO PROCEDURA Attiva_Disattiva_ANUNCI ADOZIONE...");
		
		long annunci_TOTALI = annunciDao.getAnnunciCercaPadroneAdozione_TOTALE_COUNT();
		System.out.println("annunci_TOTALI= "+annunci_TOTALI);
		
		long annunciAttivi_COUNT = annunciDao.getAnnunciCercaPadroneAdozione_COUNT();
		System.out.println("annunciAttivi_COUNT= "+annunciAttivi_COUNT);
		
		GestioneApplicazione ATTIVA_CATTURA_ADOZIONE = gestioneAppDao.getGestioneApplicazioneName("ATTIVA_CATTURA_ADOZIONE");
		int numeroMesiAnnuncioAttivo =  ATTIVA_CATTURA_ADOZIONE.getNumeroMesiAnnuncioAttivo();
		System.out.println("numero_Mesi_Annuncio_Attivo= "+numeroMesiAnnuncioAttivo);
		
		String dataString;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		Date inizio = c.getTime();
		
		dataString = dfStr.format(inizio);
		System.out.println("data inizio= "+dataString);
		
		c.add (Calendar.MONTH, -numeroMesiAnnuncioAttivo);
		Date fine = c.getTime();
		
		dataString = dfStr.format(fine);
		System.out.println("data fine= "+dataString);
		
		
		
		int annunciAttivati = 0;
		/*
		Iterator<AnnunciCercaPadroneAdozione> attovaAnnunci_ite = annunciDao.getAnnunciCercaPadroneAdozione_DATE_ATTIVA_ANNUNCI_NUOVI(inizio,fine).iterator();
		while(attovaAnnunci_ite.hasNext()){
			AnnunciCercaPadroneAdozione annuncioAttiva = attovaAnnunci_ite.next();
			//Date dataAnnuncio = annuncioAttiva.getDataPubblicazione();
			//dataString = dfStr.format(dataAnnuncio);
			//System.out.println(dataString +"attivo= "+annuncioAttiva.isAttivo()+" titolo=" +annuncioAttiva.getTitoloCane());
			
			AnnunciCercaPadroneAdozione modificaAnnuncio = annunciDao.get(annuncioAttiva.getIdAnnuncio());
			modificaAnnuncio.setAttivo(true);
			annunciDao.saveAnnuncioAdozione(modificaAnnuncio);
			annunciAttivati ++;
		}
		*/
		
		annunciAttivati = annunciDao.getAnnunciCercaPadroneAdozione_DATE_ATTIVA_ANNUNCI_NUOVI_UPDATE(inizio, fine);
		System.out.println("annunciAttivati= "+annunciAttivati);
		
		
		int annunciDisattivati = 0;
		/*
		Iterator<AnnunciCercaPadroneAdozione> disattivaAnnunci_ite = annunciDao.getAnnunciCercaPadroneAdozione_DATE_DISATTIVA_ANNUNCI_VECCHI(fine).iterator();
		while(disattivaAnnunci_ite.hasNext()){
			AnnunciCercaPadroneAdozione annuncioDisatt = disattivaAnnunci_ite.next();
			//Date dataAnnuncio = annuncioDisatt.getDataPubblicazione();
			//dataString = dfStr.format(dataAnnuncio);
			//System.out.println(dataString +" attivo= "+annuncioDisatt.isAttivo()+" titolo=" +annuncioDisatt.getTitoloCane());
			
			AnnunciCercaPadroneAdozione modificaAnnuncio = annunciDao.get(annuncioDisatt.getIdAnnuncio());
			modificaAnnuncio.setAttivo(false);
			annunciDao.saveAnnuncioAdozione(modificaAnnuncio);
			annunciDisattivati ++;
		}
		*/
		annunciDisattivati = annunciDao.getAnnunciCercaPadroneAdozione_DATE_DISATTIVA_ANNUNCI_VECCHI_UPDATE(fine);
		System.out.println("annunciDisattivati= "+annunciDisattivati);
		
		//--------------------------------------------------------------------------------------------

		System.out.println(System.getProperty("java.runtime.version"));
		
		log.debug("@@@ FINE PROCEDURA Attiva_Disattiva_ANUNCI ADOZIONE [OK]");
	}

	
	public static void dogSitter( AnnunciKijijiDogSitterDao annunciDao, GestioneApplicazioneDao gestioneAppDao ){
		
		log.debug("@@@ INIZIO PROCEDURA Attiva_Disattiva_ANUNCI DOGSITTER...");
		
		long annunci_TOTALI = annunciDao.getAnnunciKijijiDogSitter_TOTALE_COUNT();
		System.out.println("annunci_TOTALI= "+annunci_TOTALI);
		
		long annunciAttivi_COUNT = annunciDao.getAnnunciKijijiDogSitter_COUNT();
		System.out.println("annunciAttivi_COUNT= "+annunciAttivi_COUNT);
		
		GestioneApplicazione ATTIVA_CATTURA_DOGSITTER = gestioneAppDao.getGestioneApplicazioneName("ATTIVA_CATTURA_DOGSITTER");
		int numeroMesiAnnuncioAttivo =  ATTIVA_CATTURA_DOGSITTER.getNumeroMesiAnnuncioAttivo();
		System.out.println("numero_Mesi_Annuncio_Attivo= "+numeroMesiAnnuncioAttivo);
		
		String dataString;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		Date inizio = c.getTime();
		
		dataString = dfStr.format(inizio);
		System.out.println("data inizio= "+dataString);
		
		c.add (Calendar.MONTH, -numeroMesiAnnuncioAttivo);
		Date fine = c.getTime();
		
		dataString = dfStr.format(fine);
		System.out.println("data fine= "+dataString);
		
		int annunciAttivati = 0;
		/*
		Iterator<AnnunciKijijiDogSitter> attovaAnnunci_ite = annunciDao.getAnnunciKijijiDogSitter_DATE_ATTIVA_ANNUNCI_NUOVI(inizio,fine).iterator();
		while(attovaAnnunci_ite.hasNext()){
			AnnunciKijijiDogSitter annuncioAttiva = attovaAnnunci_ite.next();
			//Date dataAnnuncio = annuncioAttiva.getDataPubblicazione();
			//dataString = dfStr.format(dataAnnuncio);
			//System.out.println(dataString +" attivo= "+annuncioAttiva.isAttivo()+" titolo=" +annuncioAttiva.getNomeUtente());
			
			AnnunciKijijiDogSitter modificaAnnuncio = annunciDao.get(annuncioAttiva.getIdAnnuncio());
			modificaAnnuncio.setAttivo(true);
			annunciDao.saveAnnuncioDogSitterNoFlush(modificaAnnuncio);
			annunciAttivati ++;
		}*/
		annunciAttivati = annunciDao.getAnnunciKijijiDogSitter_DATE_ATTIVA_ANNUNCI_NUOVI_UPDATE(inizio,fine);
		System.out.println("annunciAttivati= "+annunciAttivati);
		
		
		int annunciDisattivati = 0;
		/*
		Iterator<AnnunciKijijiDogSitter> disattivaAnnunci_ite = annunciDao.getAnnunciKijijiDogSitter_DATE_DISATTIVA_ANNUNCI_VECCHI(fine).iterator();
		while(disattivaAnnunci_ite.hasNext()){
			AnnunciKijijiDogSitter annuncioDisatt = disattivaAnnunci_ite.next();
			//Date dataAnnuncio = annuncioDisatt.getDataPubblicazione();
			//dataString = dfStr.format(dataAnnuncio);
			//System.out.println(dataString +" attivo= "+annuncioDisatt.isAttivo()+" titolo=" +annuncioDisatt.getNomeUtente());
			
			AnnunciKijijiDogSitter modificaAnnuncio = annunciDao.get(annuncioDisatt.getIdAnnuncio());
			modificaAnnuncio.setAttivo(false);
			annunciDao.saveAnnuncioDogSitterNoFlush(modificaAnnuncio);
			annunciDisattivati ++;
		}
		*/
		annunciDisattivati = annunciDao.getAnnunciKijijiDogSitter_DATE_DISATTIVA_ANNUNCI_VECCHI_UPDATE(fine);
		System.out.println("annunciDisattivati= "+annunciDisattivati);
		
		//--------------------------------------------------------------------------------------------

		System.out.println(System.getProperty("java.runtime.version"));
		
		log.debug("@@@ FINE PROCEDURA Attiva_Disattiva_ANUNCI DOGSITTER [OK]");
	}

}
