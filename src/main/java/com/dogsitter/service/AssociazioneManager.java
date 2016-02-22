package com.dogsitter.service;

import java.util.List;

import com.dogsitter.dao.AssociazioneDao;
import com.dogsitter.model.Associazione;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface AssociazioneManager extends GenericManager<Associazione, Long> {
	
	void setAssociazioneDao(AssociazioneDao associazioneDao);
	
	
	Associazione get(Long id);
	
	void removeAssociazione(long userAssociazione);
	
	Associazione getAssociazioneByUser(long idUser);

	List<Associazione> getAssociazioni();
	
	int getAssociazioniConInfoInserite();

	Associazione saveAssociazione(Associazione associazione) throws Exception;

}
