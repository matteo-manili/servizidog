package com.dogsitter.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.service.AnnunciKijijiDogSitterManager;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("AnnunciKijijiDogSitterManager")
public class AnnunciKijijiDogSitterManagerImpl extends GenericManagerImpl<AnnunciKijijiDogSitter, Long> implements AnnunciKijijiDogSitterManager {

	private AnnunciKijijiDogSitterDao annunciKijijiDogSitterDao;
	
	@Override
    @Autowired
	public void setAnnunciKijijiDogSitterDao(AnnunciKijijiDogSitterDao annunciKijijiDogSitterDao) {
		this.annunciKijijiDogSitterDao = annunciKijijiDogSitterDao;
	}


	@Override
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter() {
		return annunciKijijiDogSitterDao.getAnnunciKijijiDogSitter();
	}
	
	@Override
	public Long getAnnunciKijijiDogSitter_COUNT() {
		return annunciKijijiDogSitterDao.getAnnunciKijijiDogSitter_COUNT();
	}
	
	@Override
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_RICERCA_MAPPA() {
		return annunciKijijiDogSitterDao.getAnnunciKijijiDogSitter_RICERCA_MAPPA();
	}
	
	@Override
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_RICERCA() {
		return annunciKijijiDogSitterDao.getAnnunciKijijiDogSitter_RICERCA();
	}
	
	/*
	@Override
	public int getAnnunciKijijiDogSitterConInfoInserite() {
		
		Iterator<AnnunciKijijiDogSitter> iterator = annunciKijijiDogSitterDao.getAnnunciKijijiDogSitter().iterator();
		AnnunciKijijiDogSitter annunciKijijiDogSitter = null;
		int count = 0;
		while(iterator.hasNext()){
			annunciKijijiDogSitter = iterator.next();
			if (annunciKijijiDogSitter.infoGeneraliPerRicerca_e_Sitemap_e_Immagini())
				count = count + 1;
		}
		return count;
			
	}
	*/
	
	
	@Override
	public AnnunciKijijiDogSitter get(Long id) {
		return this.annunciKijijiDogSitterDao.get(id);
	}


	@Override
	public AnnunciKijijiDogSitter saveAnnunciKijijiDogSitter(AnnunciKijijiDogSitter annunciKijijiDogSitter) throws Exception {
		return annunciKijijiDogSitterDao.saveAnnuncioDogSitter(annunciKijijiDogSitter);
	}

	
	
	
	
}
