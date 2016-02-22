package com.dogsitter.dao;

import java.util.List;

import com.dogsitter.model.Adozione;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface AdozioneDao extends GenericDao<Adozione, Long> {
	
	Adozione get(Long id);
	
	List<Adozione> getAdozioneByUser(long idUser);
	
	List<Adozione> getAdozioni();
	
	Adozione saveAdozione(Adozione adozione);

}
