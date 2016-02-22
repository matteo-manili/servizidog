package com.dogsitter.webapp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dogsitter.Constants;
import com.dogsitter.dao.AdozioneDao;
import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.dao.AssociazioneDao;
import com.dogsitter.dao.CaneDao;
import com.dogsitter.dao.DogHostDao;
import com.dogsitter.dao.DogSitterDao;
import com.dogsitter.dao.ImmaginiDao;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.Cane;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.Immagini_store;


/**
 * @author Matteo - matteo.manili@gmail.com
 *

 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class Avvio_CaricaImmagini implements Job, Serializable {
	private static final long serialVersionUID = -2249507358751610872L;

	private static final Log log = LogFactory.getLog(Avvio_CaricaImmagini.class);
	
	//DATABASE DI BACKUP
	public static ApplicationContext contextImmaginiBackUP_Dao = 
    		new ClassPathXmlApplicationContext("App-Database-Immagini-BACK_UP-Spring-Module.xml");
	public static ImmaginiDao immaginiBackUP_Dao = (ImmaginiDao) contextImmaginiBackUP_Dao.getBean("ImmaginiDao");
	
	public static ApplicationContext contextDao = 
    		new ClassPathXmlApplicationContext("App-Database-Spring-Module.xml");
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			ServletContext servletContext = (ServletContext) context.getMergedJobDataMap().get("ServletContext");
			String storageDirectory = servletContext.getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI);
			
			File theDir = new File(storageDirectory);
			// if the directory exist, carica immagini utenti
			if (theDir.exists()) {
				log.debug("QUARTZ Avvio_CaricaImmagini_Sitemap CaricaImmagini Utenti da DB_STORED...");
				Iterator<Immagini_store> iteImmaginiStored = immaginiBackUP_Dao.getImmagini().iterator();
				Immagini_store immaginiSalvate;
				while(iteImmaginiStored.hasNext()){
					immaginiSalvate = iteImmaginiStored.next();
					try {
						
						FileOutputStream fosG = new FileOutputStream(storageDirectory+"/"+immaginiSalvate.getNewFilename());
						FileOutputStream fosP = new FileOutputStream(storageDirectory+"/"+immaginiSalvate.getThumbnailFilename());
						fosG.write(immaginiSalvate.getImageGrande());
						fosP.write(immaginiSalvate.getImagePiccola());
						fosG.close();
						fosP.close();
						
						//FileUtils.writeByteArrayToFile(new File(storageDirectory+"/"+immaginiSalvate.getNewFilename()), immaginiSalvate.getImageGrande());
						//FileUtils.writeByteArrayToFile(new File(storageDirectory+"/"+immaginiSalvate.getThumbnailFilename()), immaginiSalvate.getImagePiccola());
					
					}catch(IOException ioe){
						System.out.println("IOException Avvio_CaricaImmagini.JobExecutionContext");
						log.debug("IOException Avvio_CaricaImmagini.JobExecutionContext");
						ioe.printStackTrace();
				    }
				}
				log.debug("QUARTZ Avvio_CaricaImmagini_Sitemap CaricaImmagini Utenti da DB_STORED [OK]");

				AnnunciCercaPadroneAdozioneDao daoAnnunciAdozione = (AnnunciCercaPadroneAdozioneDao) contextDao.getBean("AnnunciCercaPadroneAdozioneDao"); 
				AnnunciKijijiDogSitterDao daoAnnunciDogSitter = (AnnunciKijijiDogSitterDao) contextDao.getBean("AnnunciKijijiDogSitterDao");
				
				//caricaImmaginiAnnunciAdozione( daoAnnunciAdozione, servletContext );

				//caricaImmaginiAnnunciDogSitter( daoAnnunciDogSitter, servletContext );
				
				log.debug("QUARTZ Avvio_CaricaImmagini_Sitemap CreaSitemap...");
				DogSitterDao daoDogSitter = (DogSitterDao) contextDao.getBean("DogSitterDao");
				DogHostDao daoDogHost = (DogHostDao) contextDao.getBean("DogHostDao");
				AdozioneDao daoAdozione = (AdozioneDao) contextDao.getBean("AdozioneDao");
				AssociazioneDao daoAssociazione = (AssociazioneDao) contextDao.getBean("AssociazioneDao");
				CaneDao daoCane = (CaneDao) contextDao.getBean("CaneDao");
				
				List<DogSitter> listDogSitter = daoDogSitter.getDogSitters();
				List<DogHost> listDogHost = daoDogHost.getDogHosts();
				List<Adozione> listAdozione = daoAdozione.getAdozioni();
				List<Associazione> listAssociazione = daoAssociazione.getAssociazioni();
				List<Cane> listCani = daoCane.getCanes();
				List<AnnunciCercaPadroneAdozione> listAdozioneAnnunciSitemap = daoAnnunciAdozione.getAnnunciCercaPadroneAdozione_SITEMAP();
				List<AnnunciKijijiDogSitter> listDogSitterAnnunciDogSitterSitemap = daoAnnunciDogSitter.getAnnunciKijijiDogSitter_SITEMAP();
				
				CreaSitemap creaSitemap = new CreaSitemap();
				creaSitemap.creaSitemapProUrlProfili(servletContext, listDogSitter, listDogHost, listAdozione, listAssociazione, listCani, listDogSitterAnnunciDogSitterSitemap, listAdozioneAnnunciSitemap);
				
				/*
				 * Java utilizza memoria gestita, quindi l'unico modo è possibile allocare la memoria è quello di utilizzare l'operatore new, 
				 * e l'unico modo è possibile deallocare memoria è affidandosi alla garbage collector.
				 * Questa gestione della memoria white paper (PDF) può aiutare a spiegare cosa sta succedendo.
				 * 
				 * È anche possibile chiamare System.gc() per suggerire che il garbage collector sia eseguito immediatamente. 
				 * Tuttavia, il runtime Java prende la decisione finale, non il codice. Secondo la documentazione Java.
				 */
				System. gc();
			}
			

        }catch(SecurityException se){
			System.out.println("SecurityException Avvio_CaricaImmagini.JobExecutionContext");
			log.debug("SecurityException Avvio_CaricaImmagini.JobExecutionContext");
			se.printStackTrace();
	    } catch(Exception e) {
        	System.out.println("Exception Avvio_CaricaImmagini.JobExecutionContext");
        	log.debug("SecurityException Avvio_CaricaImmagini.JobExecutionContext");
        	e.printStackTrace();
        }
        
	}

	//CARICA TUTTE LE IMMAGINI
	public void caricaImmaginiAnnunciAdozione(AnnunciCercaPadroneAdozioneDao daoAnnunciAdozione, ServletContext servletContext) throws Exception, IOException{
		log.debug("QUARTZ Avvio_CaricaImmagini_Sitemap CaricaImmagini Annunci Adozione...");
		
			long maxImmagini = Constants.MAX_IMMAGINI_X_QUERY;
			long idMax = daoAnnunciAdozione.getAnnunciCercaPadroneAdozione_MAX_ID();
			long totaleAnnunci = daoAnnunciAdozione.getAnnunciCercaPadroneAdozione_TOTALE_COUNT();
			System.out.println("totaleAnnunci= "+totaleAnnunci);
			
			for(long inizio = 0; inizio <= idMax; inizio++){
				long fine = inizio + maxImmagini;
				//System.out.println("inizio="+inizio +" fine="+fine );
				
				List<AnnunciCercaPadroneAdozione> listAdozioneAnnunci = daoAnnunciAdozione.getAnnunciCercaPadroneAdozione_IMMAGINI_LIMIT(inizio,fine);
				CreaImmaginiAnnunciAdozione.salvaImmaginiAnnunciInFolderTmpImageAnnunci(servletContext, listAdozioneAnnunci);
				inizio = inizio + maxImmagini;
			}
			
			
			log.debug("CaricaImmagini Annunci Adozione [OK]");
	}
	
	//CARICA TUTTE LE IMMAGINI
	public void caricaImmaginiAnnunciDogSitter(AnnunciKijijiDogSitterDao daoAnnunciDogSitter, ServletContext servletContext){
		log.debug("QUARTZ Avvio_CaricaImmagini_Sitemap CaricaImmagini Annunci DogSitter...");
		
		try{
			long maxImmagini = Constants.MAX_IMMAGINI_X_QUERY;
			long idMax = daoAnnunciDogSitter.getAnnunciDogSitter_MAX_ID();
			long totaleAnnunci = daoAnnunciDogSitter.getAnnunciKijijiDogSitter_TOTALE_COUNT();
			System.out.println("totaleAnnunci= "+totaleAnnunci);
			
			for(long inizio = 0; inizio <= idMax; inizio++){
				long fine = inizio + maxImmagini;
				//System.out.println("inizio="+inizio +" fine="+fine );
				
				List<AnnunciKijijiDogSitter> listDogSitterAnnunci = daoAnnunciDogSitter.getAnnunciDogSitter_IMMAGINI_LIMIT(inizio, fine);
				CreaImmaginiAnnunciDogSitter.salvaImmaginiAnnunciInFolderTmpImageAnnunci(servletContext, listDogSitterAnnunci); 
				inizio = inizio + maxImmagini;
			}
			log.debug("CaricaImmagini Annunci DogSitter [OK]");
		
		}catch(Exception exc){
			System.out.println("Exception caricaImmaginiAnnunciDogSitter");
			exc.printStackTrace();
		}
	}
	
	
	//CARICA SOLAMENTE LE IMMAGINI DEGLI ANNUNCI NUOVI E AGGIORNATI
	public void caricaImmaginiAnnunci_salvati_e_aggiornati_Adozione
		(AnnunciCercaPadroneAdozioneDao daoAnnunciAdozione, ArrayList<Long> annunciSalvati_e_aggiornati, ServletContext servletContext) throws Exception, IOException {
	
		int maxImmagini = Constants.MAX_IMMAGINI_X_QUERY_PICCOLA;
		Long countResutl = daoAnnunciAdozione.getAnnunciCercaPadroneAdozione_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI_COUNT(annunciSalvati_e_aggiornati);
		for(int first = 0; first < countResutl; ){
			
			List<Object[]> objList = daoAnnunciAdozione.getAnnunciCercaPadroneAdozione_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI(annunciSalvati_e_aggiornati, first, maxImmagini);
			List<AnnunciCercaPadroneAdozione> listAdozioneAnnunci = new ArrayList<AnnunciCercaPadroneAdozione>();
			for(Object[] annuncio: objList){
				AnnunciCercaPadroneAdozione annuncioAdozione = new AnnunciCercaPadroneAdozione();
				annuncioAdozione.setIdAnnuncio( (Long)annuncio[0] );
				annuncioAdozione.setImmagine1( (byte[])annuncio[1] );
				annuncioAdozione.setNomeImmagine( (String)annuncio[2] );
		        listAdozioneAnnunci.add(annuncioAdozione);
		    }
			CreaImmaginiAnnunciAdozione.salvaImmaginiAnnunciInFolderTmpImageAnnunci(servletContext, listAdozioneAnnunci);
			first = first + maxImmagini;
		}
		log.debug("CaricaImmagini Annunci Adozione [OK]");
	}
	
	//CARICA SOLAMENTE LE IMMAGINI DEGLI ANNUNCI NUOVI E AGGIORNATI
	public void caricaImmaginiAnnunci_salvati_e_aggiornati_DogSitter
		(AnnunciKijijiDogSitterDao daoAnnunciDogSitter, ArrayList<Long> annunciSalvati_e_aggiornati, ServletContext servletContext) throws Exception, IOException {
	
		int maxImmagini = Constants.MAX_IMMAGINI_X_QUERY_PICCOLA;
		Long countResutl = daoAnnunciDogSitter.getAnnunciKijijiDogSitter_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI_COUNT(annunciSalvati_e_aggiornati);
		for(int first = 0; first < countResutl; ){
			
			List<Object[]> objList = daoAnnunciDogSitter.getAnnunciKijijiDogSitter_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI(annunciSalvati_e_aggiornati, first, maxImmagini);
			List<AnnunciKijijiDogSitter> listDogSitterAnnunci = new ArrayList<AnnunciKijijiDogSitter>();
			for(Object[] annuncio: objList){
				AnnunciKijijiDogSitter annuncioDogSitter = new AnnunciKijijiDogSitter();
				annuncioDogSitter.setIdAnnuncio( (Long)annuncio[0] );
				annuncioDogSitter.setImmagine1( (byte[])annuncio[1] );
				annuncioDogSitter.setImmagine2( (byte[])annuncio[2] );
				annuncioDogSitter.setImmagine3( (byte[])annuncio[3] );
				annuncioDogSitter.setNomeImmagine( (String)annuncio[4] );
				listDogSitterAnnunci.add(annuncioDogSitter);
		    }
			CreaImmaginiAnnunciDogSitter.salvaImmaginiAnnunciInFolderTmpImageAnnunci(servletContext, listDogSitterAnnunci);
			first = first + maxImmagini;
		}
		
		log.debug("CaricaImmagini Annunci DogSitter [OK]");
	}
	
}
