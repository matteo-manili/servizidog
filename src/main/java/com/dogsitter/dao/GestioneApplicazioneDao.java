package com.dogsitter.dao;

import com.dogsitter.model.GestioneApplicazione;
import com.dogsitter.model.Role;


/**
 * Role Data Access Object (DAO) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface GestioneApplicazioneDao extends GenericDao<GestioneApplicazione, Long> {

	
	
	GestioneApplicazione saveGestioneApplicazione(GestioneApplicazione gestioneApplicazione);

	GestioneApplicazione getGestioneApplicazioneName(String nomeFunzione);
	
}
