package com.dogsitter.service.impl;


import com.dogsitter.dao.TipoRuoliServiceDogDao;
import com.dogsitter.model.TipoRuoliServiceDog;
import com.dogsitter.service.TipoRuoliServiceDogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Service("tipoRuoliServiceDogManager")
public class TipoRuoliServiceDogManagerImpl extends GenericManagerImpl<TipoRuoliServiceDog, Long> implements TipoRuoliServiceDogManager {
	
	TipoRuoliServiceDogDao tipoRuoliDao;

	
	@Override
    @Autowired
	public void setTipoRuoliDao(TipoRuoliServiceDogDao tipoRuoliDao) {
		this.tipoRuoliDao = tipoRuoliDao;
	}
	
	
	@Override
	public List<TipoRuoliServiceDog> getRuoliServiziDogOrdineId() {
		return tipoRuoliDao.getRuoliServiziDogOrdineId();
	}
	
	



	@Override
	public TipoRuoliServiceDog getTipoRuoliServiceDogDaoByName(String rolename) {
		return tipoRuoliDao.getTipoRuoliServiceDogDaoByName(rolename);
	}
	
	
	@Override
	public List<TipoRuoliServiceDog> getRuoliServiceDog(TipoRuoliServiceDog tipoRuoliServiceDog) {
		// TODO Auto-generated method stub
		return tipoRuoliDao.getAll();
	}
	
	
	@Override
	public TipoRuoliServiceDog saveRuoliServiceDog(TipoRuoliServiceDog tipoRuoliServiceDog) {
		return tipoRuoliDao.save(tipoRuoliServiceDog);
	}
	
	@Override
	public void removeRuoliServiceDog(String rolename) {
		tipoRuoliDao.removeTipoRuoliServiceDog(rolename);
	}
    
    

}