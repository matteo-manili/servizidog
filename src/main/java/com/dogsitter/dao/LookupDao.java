package com.dogsitter.dao;

import com.dogsitter.model.DogSitter;
import com.dogsitter.model.Role;
import com.dogsitter.model.TipoRuoliServiceDog;

import java.util.List;

/**
 * Lookup Data Access Object (GenericDao) interface.  This is used to lookup values in
 * the database (i.e. for drop-downs).
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupDao {
    //~ Methods ================================================================

    /**
     * Returns all Roles ordered by name
     * @return populated list of roles
     */
    List<Role> getRoles();
    
    List<TipoRuoliServiceDog> getRuoliServiziDog();
    
    List<DogSitter> getDogSitters();
}
