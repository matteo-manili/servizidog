package com.dogsitter.dao;

import java.util.List;

import com.dogsitter.model.DogHost;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface DogHostDao extends GenericDao<DogHost, Long> {
	
	DogHost get(Long id);
	
	List<DogHost> getDogHostByUser(long idUser);
	
	List<DogHost> getDogHosts();
	
	DogHost saveDogHost(DogHost dogHost);

}
