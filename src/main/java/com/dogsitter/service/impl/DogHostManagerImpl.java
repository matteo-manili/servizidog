package com.dogsitter.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogsitter.dao.DogHostDao;
import com.dogsitter.model.DogHost;
import com.dogsitter.service.DogHostManager;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("DogHostManager")
public class DogHostManagerImpl extends GenericManagerImpl<DogHost, Long> implements DogHostManager {

	private DogHostDao dogHostDao;
	
	@Override
    @Autowired
	public void setDogHostDao(DogHostDao dogHostDao) {
		this.dogHostDao = dogHostDao;
	}

	
	
	@Override
	public DogHost get(Long id) {
		return this.dogHostDao.get(id);
	}
	

	
	@Override
    public void removeDogHost(long userDogHost) {
		dogHostDao.remove(userDogHost);
    }
	
	
	
	@Override
	public DogHost getDogHostByUser(long idUser) {

		if (dogHostDao.getDogHostByUser(idUser).iterator().hasNext()){
			return dogHostDao.getDogHostByUser(idUser).iterator().next();
		}
		
		return null;

	}
	
	
	@Override
	public List<DogHost> getDogHosts() {
		return dogHostDao.getDogHosts();
	}
	
	
	@Override
	public int getDogHostConInfoInserite() {
		Iterator<DogHost> iterator = dogHostDao.getDogHosts().iterator();
		DogHost dogHost = null;
		int count = 0;
		while(iterator.hasNext()){
			dogHost = iterator.next();
			if (dogHost.informazioniGeneraliInserite())
				count = count + 1;
		}
		return count;
			
	}
	
	


	@Override
	public DogHost saveDogHost(DogHost dogHost) throws Exception {
		return dogHostDao.saveDogHost(dogHost);
	}

	
	
	
	
}
