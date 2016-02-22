package com.dogsitter.service;

import java.util.List;

import com.dogsitter.dao.DogHostDao;
import com.dogsitter.model.DogHost;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface DogHostManager extends GenericManager<DogHost, Long> {
	
	void setDogHostDao(DogHostDao dogHostDao);
	
	
	DogHost get(Long id);
	
	
	void removeDogHost(long userDogHost);
	
	DogHost getDogHostByUser(long idUser);

	List<DogHost> getDogHosts();
	
	int getDogHostConInfoInserite();

	DogHost saveDogHost(DogHost dogHost) throws Exception;

}
