package com.dogsitter.service;

import java.util.List;

import com.dogsitter.dao.AdozioneDao;
import com.dogsitter.model.Adozione;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface AdozioneManager extends GenericManager<Adozione, Long> {
	
	void setAdozioneDao(AdozioneDao adozioneDao);
	
	Adozione get(Long id);
	
	void removeAdozione(long userAdozione);
	
	Adozione getAdozioneByUser(long idUser);

	List<Adozione> getAdozioni();
	
	Adozione saveAdozione(Adozione adozione) throws Exception;

}
