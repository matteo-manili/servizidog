package com.dogsitter.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.service.AnnunciCercaPadroneAdozioneManager;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("AnnunciCercaPadroneAdozioneManager")
public class AnnunciCercaPadroneAdozioneManagerImpl extends GenericManagerImpl<AnnunciCercaPadroneAdozione, Long> implements AnnunciCercaPadroneAdozioneManager {

	private AnnunciCercaPadroneAdozioneDao annunciCercaPadroneAdozioneDao;
	
	@Override
    @Autowired
	public void setAnnunciCercaPadroneAdozioneDao(AnnunciCercaPadroneAdozioneDao annunciCercaPadroneAdozioneDao) {
		this.annunciCercaPadroneAdozioneDao = annunciCercaPadroneAdozioneDao;
	}


	@Override
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione() {
		return annunciCercaPadroneAdozioneDao.getAnnunciCercaPadroneAdozione();
	}
	
	@Override
	public Long getAnnunciCercaPadroneAdozione_COUNT() {
		return annunciCercaPadroneAdozioneDao.getAnnunciCercaPadroneAdozione_COUNT();
	}
	
	@Override
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_RICERCA_MAPPA() {
		return annunciCercaPadroneAdozioneDao.getAnnunciCercaPadroneAdozione_RICERCA_MAPPA();
	}
	
	@Override
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_RICERCA() {
		return annunciCercaPadroneAdozioneDao.getAnnunciCercaPadroneAdozione_RICERCA();
	}
	
	
	@Override
	public AnnunciCercaPadroneAdozione get(Long id) {
		return this.annunciCercaPadroneAdozioneDao.get(id);
	}


	@Override
	public AnnunciCercaPadroneAdozione saveAnnunciCercaPadroneAdozione(AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione) throws Exception {
		return annunciCercaPadroneAdozioneDao.saveAnnuncioAdozione(annunciCercaPadroneAdozione);
	}

	
	
	
	
}
