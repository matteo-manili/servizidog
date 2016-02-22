package com.dogsitter.dao;

import java.util.List;

import com.dogsitter.model.Cane;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface CaneDao extends GenericDao<Cane, Long> {
	
	Cane get(Long id);
	
	List<Cane> getCaneByAdozione(long idAdozione) throws Exception;
	
	List<Cane> getCaneByAssociazione(long idAssociazione) throws Exception;
	
	List<Cane> getCanes();
	
	int getTotaleCaniByAdozione();
	
	Cane eliminaCane(Cane cane);
	
	void eliminaCaniByAdozione(long idAdozione);
	
	void eliminaCaniByAssociazione(long idAdozione);
	
	Cane saveCane(Cane cane);

}
