package com.dogsitter.service.impl;

import java.io.FileNotFoundException;
import java.util.List;

import com.dogsitter.dao.AdozioneDao;
import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.dao.AssociazioneDao;
import com.dogsitter.dao.CaneDao;
import com.dogsitter.dao.DogHostDao;
import com.dogsitter.dao.DogSitterDao;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.Cane;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.DogSitter;
import com.dogsitter.service.CreaSitemapManager;
import com.dogsitter.webapp.util.CreaSitemap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("creaSitemapManager")
public class CreaSitemapManagerImpl implements CreaSitemapManager {
	
	
    @Autowired
    DogSitterDao daoDogSitter;
    
    @Autowired
    DogHostDao daoDogHost;
    
    @Autowired
    AdozioneDao daoAdozione;
    
    @Autowired
    AssociazioneDao daoAssociazione;
    
    @Autowired
    CaneDao daoCane;
    
    @Autowired
    AnnunciKijijiDogSitterDao daoAnnunciDogSitter;
    
    @Autowired
    AnnunciCercaPadroneAdozioneDao daoAnnunciAdozione;
    
	@Override
	public void creaSitemapProUrlProfili(ServletContext servletContext) throws FileNotFoundException, NullPointerException {

		List<DogSitter> listDogSitter = daoDogSitter.getDogSitters();
		List<DogHost> listDogHost = daoDogHost.getDogHosts();
		List<Adozione> listAdozione = daoAdozione.getAdozioni();
		List<Associazione> listAssociazione = daoAssociazione.getAssociazioni();
		List<Cane> listCani = daoCane.getCanes();
		List<AnnunciKijijiDogSitter> listDogSitterAnnunci = daoAnnunciDogSitter.getAnnunciKijijiDogSitter();
		List<AnnunciCercaPadroneAdozione> daoAdozioneAnnunci = daoAnnunciAdozione.getAnnunciCercaPadroneAdozione();

		
		CreaSitemap creaSitemap = new CreaSitemap();
		creaSitemap.creaSitemapProUrlProfili(servletContext, listDogSitter, listDogHost, listAdozione, listAssociazione, listCani, listDogSitterAnnunci, daoAdozioneAnnunci);

	}

	
	
    
}
