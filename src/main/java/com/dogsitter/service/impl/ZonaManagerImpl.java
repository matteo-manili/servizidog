package com.dogsitter.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogsitter.dao.ZonaDao;
import com.dogsitter.model.Comuni;
import com.dogsitter.model.Province;
import com.dogsitter.model.Regioni;
import com.dogsitter.service.ZonaManager;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Service("ZonaManager")
public class ZonaManagerImpl extends GenericManagerImpl<Object, Long> implements ZonaManager {

	private ZonaDao zonaDao;
	
	@Override
    @Autowired
	public void setZonaDao(ZonaDao zonaDao) {
		this.zonaDao = zonaDao;
		
	}
	
	
	
	@Override
	public List<String> dammiComuni(String term) {
		
		
		List<Comuni> listComuni;
		listComuni = zonaDao.dammiComuni(term);
		Iterator<Comuni> iterator = listComuni.iterator();
		
		Comuni comuni = null;
		
    	ArrayList<String> listComuniString = new ArrayList<String>();
    	while ( iterator.hasNext() ) {
    		comuni = iterator.next();
    	    
    	    if (listComuniString.size() < 10)
    	    	listComuniString.add(comuni.getNomeComune());
    	    
    	}
		
		
		
		return listComuniString;
	}
	
	
	
	
	
	
	@Override
	public List<String> getZonaRicercaGoogleMap(String term) throws Exception {
		try{
			
			List<Object> listComuni;
			listComuni = zonaDao.findByZonaRicercaGoogleMapComuneProvinciaRegione(term); 
	    	Iterator<Object> iterator = listComuni.iterator();
	    	ArrayList<String> listComuniString = new ArrayList<String>();
	    	while ( iterator.hasNext() ) {
	    	    Object[] tuple = (Object[]) iterator.next();
	    	    Comuni com =  (Comuni) tuple[0];
	    	    Province prov = (Province) tuple[1];
	    	    Regioni reg = (Regioni) tuple[2];
	    	    String appo = com.getNomeComune()+", "+prov.getNomeProvincia()+", "+reg.getNomeRegione();
	    	    if (listComuniString.size() < 15)
	    	    	listComuniString.add(appo);
	    	}
	
    		
    		return listComuniString;

		}catch(Exception ee){
			return null;
		}
	}



}
