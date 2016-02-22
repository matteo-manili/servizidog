package com.dogsitter.service;

import java.util.List;

import com.dogsitter.dao.CaneDao;
import com.dogsitter.model.Cane;
import com.dogsitter.util.CaneListContainer;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface CaneManager extends GenericManager<Cane, Long> {
	
	void setCaneDao(CaneDao caneDao);
	
	Cane get(Long id);
	
	void removeCane(long idCane);
	
	List<Cane> getCaneByAdozione(long idAdozione) ;
	
	List<Cane> getCaneByAssociazione(long idAssociazione) throws Exception;

	List<Cane> getCanes();
	
	int getTotaleCaniByAdozione();
	
	int salvaCani(CaneListContainer caneListContainer, Object object) throws Exception;
	
	void eliminaCaniByAdozione(long idAdozione);
	
	void eliminaCaniByAssociazione(long idAssociazione);
	
	Cane eliminaCane(Cane cane);

	Cane saveCane(Cane cane);

}
