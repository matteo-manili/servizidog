package com.dogsitter.service;

import com.dogsitter.dao.TipoRuoliServiceDogDao;
import com.dogsitter.model.TipoRuoliServiceDog;

import java.util.List;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
public interface TipoRuoliServiceDogManager extends GenericManager<TipoRuoliServiceDog, Long> {
	
	
	
	void setTipoRuoliDao(TipoRuoliServiceDogDao tipoRuoliDao);
	
	
	List<TipoRuoliServiceDog> getRuoliServiziDogOrdineId();
	
	
    /**
     * {@inheritDoc}
     */
    List getRuoliServiceDog(TipoRuoliServiceDog tipoRuoliServiceDog);

    /**
     * {@inheritDoc}
     */
    TipoRuoliServiceDog getTipoRuoliServiceDogDaoByName(String rolename);

    /**
     * {@inheritDoc}
     */
    TipoRuoliServiceDog saveRuoliServiceDog(TipoRuoliServiceDog tipoRuoliServiceDog);

    /**
     * {@inheritDoc}
     */
    void removeRuoliServiceDog(String rolename);





	

	
}
