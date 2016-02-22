package com.dogsitter.service;

import java.util.List;

import com.dogsitter.dao.DogSitterDao;
import com.dogsitter.model.DogSitter;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface DogSitterManager extends GenericManager<DogSitter, Long> {
	
	void setDogSitterDao(DogSitterDao dogSitterDao);
	
	
	DogSitter get(Long id);
	
	
	void removeDogSitter(long userDogSitter);
	
	DogSitter getDogSitterByUser(long idUser);

	List<DogSitter> getDogSitters();
	
	int getDogSittersConInfoInserite();
	

	DogSitter saveDogSitter(DogSitter dogSitter) throws Exception;

}
