package com.dogsitter.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogsitter.dao.DogSitterDao;
import com.dogsitter.model.DogSitter;
import com.dogsitter.service.DogSitterManager;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("DogSitterManager")
public class DogSitterManagerImpl extends GenericManagerImpl<DogSitter, Long> implements DogSitterManager {

	private DogSitterDao dogSitterDao;
	
	@Override
    @Autowired
	public void setDogSitterDao(DogSitterDao dogSitterDao) {
		this.dogSitterDao = dogSitterDao;
	}

	
	
	@Override
	public DogSitter get(Long id) {
		return this.dogSitterDao.get(id);
	}
	

	
	@Override
    public void removeDogSitter(long userDogSitter) {
		dogSitterDao.remove(userDogSitter);
    }
	
	
	
	@Override
	public DogSitter getDogSitterByUser(long idUser) {

		if (dogSitterDao.getDogSitterByUser(idUser).iterator().hasNext()){
			return dogSitterDao.getDogSitterByUser(idUser).iterator().next();
		}
		
		return null;

	}
	
	
	@Override
	public List<DogSitter> getDogSitters() {
		return dogSitterDao.getDogSitters();
	}
	
	
	@Override
	public int getDogSittersConInfoInserite() {
		
		Iterator<DogSitter> iterator = dogSitterDao.getDogSitters().iterator();
		DogSitter dogSitter = null;
		int count = 0;
		while(iterator.hasNext()){
			dogSitter = iterator.next();
			if (dogSitter.informazioniGeneraliInserite())
				count = count + 1;
		}
		return count;
			
	}


	@Override
	public DogSitter saveDogSitter(DogSitter dogSitter) throws Exception {
		return dogSitterDao.saveDogSitter(dogSitter);
	}

	
	
	
	
}
