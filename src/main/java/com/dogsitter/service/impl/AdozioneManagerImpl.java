package com.dogsitter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogsitter.dao.AdozioneDao;
import com.dogsitter.model.Adozione;
import com.dogsitter.service.AdozioneManager;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("AdozioneManager")
public class AdozioneManagerImpl extends GenericManagerImpl<Adozione, Long> implements AdozioneManager {

	private AdozioneDao adozioneDao;
	
	@Override
    @Autowired
	public void setAdozioneDao(AdozioneDao adozioneDao) {
		this.adozioneDao = adozioneDao;
	}

	
	
	@Override
	public Adozione get(Long id) {
		return this.adozioneDao.get(id);
	}
	

	
	@Override
    public void removeAdozione(long userAdozione) {
		adozioneDao.remove(userAdozione);
    }
	
	
	
	@Override
	public Adozione getAdozioneByUser(long idUser) {

		if (adozioneDao.getAdozioneByUser(idUser).iterator().hasNext()){
			return adozioneDao.getAdozioneByUser(idUser).iterator().next();
		}
		
		return null;

	}
	
	
	@Override
	public List<Adozione> getAdozioni() {
		return adozioneDao.getAdozioni();
	}


	@Override
	public Adozione saveAdozione(Adozione adozione) throws Exception {
		return adozioneDao.saveAdozione(adozione);
	}

	
	
	
}
