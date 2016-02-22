package com.dogsitter.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.dogsitter.Constants;
import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.service.InizializzaServiziDogManager;
import com.dogsitter.webapp.util.CreaImmaginiAnnunciAdozione;
import com.dogsitter.webapp.util.CreaImmaginiAnnunciDogSitter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("inizializzaServiziDogManager")
public class InizializzaServiziDogManagerImpl implements InizializzaServiziDogManager {
	private static final Log log = LogFactory.getLog(InizializzaServiziDogManagerImpl.class);
	
    @Autowired
    AnnunciKijijiDogSitterDao daoAnnunciDogSitter;
    
    @Autowired
    AnnunciCercaPadroneAdozioneDao daoAnnunciAdozione;

	@Override
	public void creaCartelleImmagini(ServletContext servletContext) throws FileNotFoundException, NullPointerException {

		String storageDirectory = servletContext.getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI);
		String storageDirectorympImageAnnunci = servletContext.getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI_ANNUNCI);
		
		File theDir = new File(storageDirectory);
		File theDirImgAnnunci = new File(storageDirectorympImageAnnunci);
		
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    boolean result = theDir.mkdir();

		    if(result) {    
		        System.out.println(storageDirectory+" creata");  
		    }else{
		    	System.out.println(storageDirectory+" non creata");
		    }
		}else{
			System.out.println(storageDirectory+" gia presente");
		}
		
		// if the directory does not exist, create it
		if (!theDirImgAnnunci.exists()) {
		    boolean result = theDirImgAnnunci.mkdir();

		    if(result) {    
		        System.out.println(storageDirectorympImageAnnunci+" creata");  
		    }else{
		    	System.out.println(storageDirectorympImageAnnunci+" non creata");
		    }
		}else{
			System.out.println(storageDirectorympImageAnnunci+" gia presente");
		}
		
		log.debug("Creazione cartelle /tmpImage e /tmpImageAnnunci [OK]");

		//-----------------------------------------------------------------------------------

		if (theDirImgAnnunci.exists()) {
			//Carico immagini annunci nella cartella /img
			/* //LO FACCIO FARE AL QUARTZ, QUANDO SI AVVIA LA APPLICAZIONE
			List<AnnunciKijijiDogSitter> listDogSitterAnnunci = daoAnnunciDogSitter.getAnnunciKijijiDogSitter();
			List<AnnunciCercaPadroneAdozione> listAdozioneAnnunci = daoAnnunciAdozione.getAnnunciCercaPadroneAdozione();
			
			CreaImmaginiAnnunciDogSitter creaImmaginiAnnunci = new CreaImmaginiAnnunciDogSitter();
			creaImmaginiAnnunci.salvaImmaginiAnnunciInFolderTmpImageAnnunci(servletContext, listDogSitterAnnunci);
			
			CreaImmaginiAnnunciAdozione creaImmaginiAnnunciAdozione = new CreaImmaginiAnnunciAdozione();
			creaImmaginiAnnunciAdozione.salvaImmaginiAnnunciInFolderTmpImageAnnunci(servletContext, listAdozioneAnnunci);
			
			log.debug("Caricamento immagini annunci nella cartella /img [OK]");
			*/
		}

	}


    
}
