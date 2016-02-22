package com.dogsitter.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.dogsitter.dao.AssociazioneDao;
import com.dogsitter.model.Associazione;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("AssociazioneDao")
public class AssociazioneDaoHibernate extends GenericDaoHibernate<Associazione, Long> implements AssociazioneDao {

	public AssociazioneDaoHibernate() {
		super(Associazione.class);
	}
	
	
	
	@Override
    @Transactional(readOnly = true)
	public Associazione get(Long id){
		Associazione associazione = (Associazione) getSession().get(Associazione.class, id);
		return associazione;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Associazione> getAssociazioneByUser(long idUser) {
		List<Associazione> listAssociazione = new ArrayList<Associazione>();
    	listAssociazione = getSession().createCriteria(Associazione.class).add(Restrictions.eq("user.id", idUser)).list(); 
        return listAssociazione;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Associazione> getAssociazioni() {
        return getSession().createCriteria(Associazione.class).list();
	}
	
	
	
	

	@Override
	public Associazione saveAssociazione(Associazione associazione) {
		getSession(). saveOrUpdate(associazione);
		//getSession().flush();
		return associazione;
	}
	
	
	

}
