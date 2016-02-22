package com.dogsitter.dao;

import java.util.List;

import com.dogsitter.model.Associazione;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface AssociazioneDao extends GenericDao<Associazione, Long> {
	
	Associazione get(Long id);
	
	List<Associazione> getAssociazioneByUser(long idUser);
	
	List<Associazione> getAssociazioni();
	
	Associazione saveAssociazione(Associazione associazione);

}
