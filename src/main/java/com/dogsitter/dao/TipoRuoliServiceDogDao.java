package com.dogsitter.dao;

import java.util.List;

import com.dogsitter.model.TipoRuoliServiceDog;

/**
 * Role Data Access Object (DAO) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface TipoRuoliServiceDogDao extends GenericDao<TipoRuoliServiceDog, Long> {
	
	
	
	List<TipoRuoliServiceDog> getRuoliServiziDogOrdineId();
	
	
    /**
     * Gets role information based on rolename
     * @param rolename the rolename
     * @return populated role object
     */
	TipoRuoliServiceDog getTipoRuoliServiceDogDaoByName(String rolename);

    /**
     * Removes a role from the database by name
     * @param rolename the role's rolename
     */
    void removeTipoRuoliServiceDog(String rolename);
}
