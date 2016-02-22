package com.dogsitter.dao;

import java.util.List;
import com.dogsitter.model.Comuni;



public interface ZonaDao extends GenericDao<Object, Long> {
	

	
	List<Comuni> findByZonaRicercaGoogleMap(String firstName);
	
	List<Object> findByZonaRicercaGoogleMapComuneProvinciaRegione(String term);
	
	
	List<Comuni> dammiComuni(String term);
	
}
