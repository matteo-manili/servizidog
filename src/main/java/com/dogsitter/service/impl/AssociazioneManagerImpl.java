package com.dogsitter.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogsitter.dao.AssociazioneDao;
import com.dogsitter.model.Associazione;
import com.dogsitter.service.AssociazioneManager;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("AssociazioneManager")
public class AssociazioneManagerImpl extends GenericManagerImpl<Associazione, Long> implements AssociazioneManager {

	private AssociazioneDao associazioneDao;
	
	@Override
    @Autowired
	public void setAssociazioneDao(AssociazioneDao associazioneDao) {
		this.associazioneDao = associazioneDao;
	}

	
	
	@Override
	public Associazione get(Long id) {
		return this.associazioneDao.get(id);
	}
	

	
	@Override
    public void removeAssociazione(long userAssociazione) {
		associazioneDao.remove(userAssociazione);
    }
	
	
	
	@Override
	public Associazione getAssociazioneByUser(long idUser) {

		if (associazioneDao.getAssociazioneByUser(idUser).iterator().hasNext()){
			return associazioneDao.getAssociazioneByUser(idUser).iterator().next();
		}
		
		return null;

	}
	
	
	@Override
	public List<Associazione> getAssociazioni() {
		return associazioneDao.getAssociazioni();
	}
	
	
	
	@Override
	public int getAssociazioniConInfoInserite() {
		
		Iterator<Associazione> iterator = associazioneDao.getAssociazioni().iterator();
		Associazione associazione = null;
		int count = 0;
		while(iterator.hasNext()){
			associazione = iterator.next();
			if (associazione.informazioniGeneraliInserite())
				count = count + 1;
		}
		return count;
			
	}
	
	


	@Override
	public Associazione saveAssociazione(Associazione associazione) throws Exception {
		return associazioneDao.saveAssociazione(associazione);
	}

	
	
	
	
}
