package com.dogsitter.service;

import java.util.List;

import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.model.AnnunciKijijiDogSitter;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface AnnunciKijijiDogSitterManager extends GenericManager<AnnunciKijijiDogSitter, Long> {
	

	void setAnnunciKijijiDogSitterDao(AnnunciKijijiDogSitterDao annunciKijijiDogSitterDao);
	
	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter();
	
	AnnunciKijijiDogSitter get(Long id);
	
	AnnunciKijijiDogSitter saveAnnunciKijijiDogSitter(AnnunciKijijiDogSitter annunciKijijiDogSitter) throws Exception;

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_RICERCA();

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_RICERCA_MAPPA();

	Long getAnnunciKijijiDogSitter_COUNT();


	

}
