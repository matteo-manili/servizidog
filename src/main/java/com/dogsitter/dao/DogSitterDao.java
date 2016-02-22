package com.dogsitter.dao;

import java.util.List;

import com.dogsitter.model.DogSitter;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface DogSitterDao extends GenericDao<DogSitter, Long> {
	
	DogSitter get(Long id);
	
	List<DogSitter> getDogSitterByUser(long idUser);
	
	List<DogSitter> getDogSitters();
	
	DogSitter saveDogSitter(DogSitter dogSitter);

}
