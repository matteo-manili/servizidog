package com.dogsitter.service;

import java.util.List;

import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface AnnunciCercaPadroneAdozioneManager extends GenericManager<AnnunciCercaPadroneAdozione, Long> {
	

	void setAnnunciCercaPadroneAdozioneDao(AnnunciCercaPadroneAdozioneDao annunciCercaPadroneAdozioneDao);
	
	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione();
	
	AnnunciCercaPadroneAdozione get(Long id);
	
	AnnunciCercaPadroneAdozione saveAnnunciCercaPadroneAdozione(AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione) throws Exception;

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_RICERCA();

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_RICERCA_MAPPA();

	Long getAnnunciCercaPadroneAdozione_COUNT();

}
