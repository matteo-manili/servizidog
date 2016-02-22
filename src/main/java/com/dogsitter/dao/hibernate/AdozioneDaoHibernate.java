package com.dogsitter.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.dogsitter.dao.AdozioneDao;
import com.dogsitter.model.Adozione;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("AdozioneDao")
public class AdozioneDaoHibernate extends GenericDaoHibernate<Adozione, Long> implements AdozioneDao {

	public AdozioneDaoHibernate() {
		super(Adozione.class);
	}
	
	
	
	@Override
    @Transactional(readOnly = true)
	public Adozione get(Long id){
		Adozione adozione = (Adozione) getSession().get(Adozione.class, id);
		return adozione;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Adozione> getAdozioneByUser(long idUser) {
		
		
		List<Adozione> listAdozione = new ArrayList<Adozione>();
    	listAdozione = getSession().createCriteria(Adozione.class).add(Restrictions.eq("user.id", idUser)).list(); 
        
        return listAdozione;
		
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Adozione> getAdozioni() {
        return getSession().createCriteria(Adozione.class).list();
	}
	


	@Override
	public Adozione saveAdozione(Adozione adozione) {
		getSession(). saveOrUpdate(adozione);
		//getSession().flush();
		return adozione;
	}
	
	
	

}
