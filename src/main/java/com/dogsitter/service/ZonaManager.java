package com.dogsitter.service;


import java.util.List;

import com.dogsitter.dao.ZonaDao;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
public interface ZonaManager extends GenericManager<Object, Long> {
	
	/**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setZonaDao(ZonaDao zonaDao);
    
    List<String> dammiComuni(String term);
	
	List<String> getZonaRicercaGoogleMap(String term) throws Exception;
	
	
}
