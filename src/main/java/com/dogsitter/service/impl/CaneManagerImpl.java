package com.dogsitter.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogsitter.dao.CaneDao;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.Cane;
import com.dogsitter.service.CaneManager;
import com.dogsitter.util.CaneListContainer;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("CaneManager")
public class CaneManagerImpl extends GenericManagerImpl<Cane, Long> implements CaneManager {

	private CaneDao caneDao;
	
	@Override
    @Autowired
	public void setCaneDao(CaneDao caneDao) {
		this.caneDao = caneDao;
	}

	
	@Override
	public Cane get(Long id) {
		return this.caneDao.get(id);
	}
	
	@Override
	public List<Cane> getCaneByAdozione(long idAdozione)  {
		try {
			return caneDao.getCaneByAdozione(idAdozione);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			List<Cane> list = new ArrayList<Cane>();
			return list;
		}
	}
	
	
	@Override
	public List<Cane> getCaneByAssociazione(long idAssociazione) throws Exception {
		return caneDao.getCaneByAssociazione(idAssociazione);
	}
	
	@Override
	public List<Cane> getCanes() {
		return caneDao.getCanes();
	}
	
	
	@Override
	public int getTotaleCaniByAdozione() { 
        return caneDao.getTotaleCaniByAdozione();
	}
	
	@Override
	public int salvaCani(CaneListContainer caneListContainer, Object object) throws Exception {
		Cane newCane = null;
		Calendar cal = null;
		//Integer eta = 0;
		
		int numeroCani = 0;
		//mi serve per il messaggio finale...
		if (caneListContainer.getCaneList() != null){
			numeroCani = caneListContainer.getCaneList().size();
		}
        for( Cane caneItem : caneListContainer.getCaneList() ) {
        	if( caneItem.getId() != null ){
        		newCane = caneDao.get(caneItem.getId());
        	}else{
        		newCane = new Cane();
        	}
        	if (caneItem.getEta() != null){
        		newCane.setEta(caneItem.getEta());
        	}else{
        		newCane.setEta(0);
        	}
        	newCane.setNomeCane(caneItem.getNomeCane());
        	newCane.setSesso(caneItem.getSesso());
        	newCane.setTaglia(caneItem.getTaglia());
        	newCane.setPelo(caneItem.getPelo());
        	newCane.setCarattere(caneItem.getCarattere());
        	newCane.setStoriadelcane(caneItem.getStoriadelcane()); 
        	
        	if (caneItem.getEta() == 0 || (caneItem.getEta() > 0 && caneItem.getEta() < 30) ){
	        	cal = Calendar.getInstance();
	        	cal.setTime(new Date());
	        	cal.add(Calendar.YEAR, -caneItem.getEta());
	        	newCane.setAnnoDinascita(cal.getTime());
        	}else{
        		newCane.setAnnoDinascita(null);
        	}
        	if (object.getClass().equals(Associazione.class) ){
        		Associazione associazione = (Associazione) object;
        		newCane.setAssociazione(associazione);
        	}
        	if (object.getClass().equals(Adozione.class) ){
        		Adozione adozione = (Adozione) object;
        		newCane.setAdozione(adozione);
        	}
        	caneDao.saveCane(newCane);
        }
		
    	return numeroCani;
	}
	
	
	
	
	


	@Override
	public Cane eliminaCane(Cane cane) {
		return caneDao.eliminaCane(cane);
	}

	
	@Override
	public void eliminaCaniByAdozione(long idAdozione){
		caneDao.eliminaCaniByAdozione(idAdozione);
	}
	
	
	@Override
	public void eliminaCaniByAssociazione(long idAssociazione){
		caneDao.eliminaCaniByAssociazione(idAssociazione);
	}
	
	
	@Override
	public Cane saveCane(Cane cane) {
		return caneDao.saveCane(cane);
	}

	
	@Override
    public void removeCane(long idCane) {
		caneDao.remove(idCane);
    }
	
	
	
}
