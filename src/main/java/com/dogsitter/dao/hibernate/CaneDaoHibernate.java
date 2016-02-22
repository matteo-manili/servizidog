package com.dogsitter.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.dogsitter.dao.CaneDao;
import com.dogsitter.model.Cane;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("CaneDao")
public class CaneDaoHibernate extends GenericDaoHibernate<Cane, Long> implements CaneDao {

	public CaneDaoHibernate() {
		super(Cane.class);
	}
	
	
	
	@Override
    @Transactional(readOnly = true)
	public Cane get(Long id){
		Cane cane = (Cane) getSession().get(Cane.class, id);
		return cane;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Cane> getCaneByAdozione(long idAdozione) throws Exception {
		List<Cane> listCane = new ArrayList<Cane>();
    	listCane = getSession().createCriteria(Cane.class).add(Restrictions.eq("adozione.id", idAdozione)).list();
    	return listCane;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cane> getCaneByAssociazione(long idAssociazione) throws Exception {
		List<Cane> listCane = new ArrayList<Cane>();
    	listCane = getSession().createCriteria(Cane.class).add(Restrictions.eq("associazione.id", idAssociazione)).list();
        return listCane;
	}
	
	
	
	@Override
	public void eliminaCaniByAdozione(long idAdozione){
		@SuppressWarnings("unchecked")
		List<Cane> toDelete = getSession().createCriteria(Cane.class).add(Restrictions.eq("adozione.id", idAdozione)).list();
        for (Object o: toDelete){
        	getSession() .delete(o);
        	
        }
        getSession().flush();
	} 
	
	
	@Override
	public void eliminaCaniByAssociazione(long idAssociazione){
		@SuppressWarnings("unchecked")
		List<Cane> toDelete = getSession().createCriteria(Cane.class).add(Restrictions.eq("associazione.id", idAssociazione)).list();
        for (Object o: toDelete){
        	getSession() .delete(o);
        	
        }
        getSession().flush();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Cane> getCanes() {
        return getSession().createCriteria(Cane.class).list();
	}
	
	
	@Override
	public int getTotaleCaniByAdozione() { 
        return getSession().createCriteria(Cane.class).add(Restrictions.isNotNull("adozione.id")).list().size();
	}
	
	
	@Override
	public Cane eliminaCane(Cane cane) {
		getSession(). delete(cane);
		//getSession().flush();
		return cane;
	}

	@Override
	public Cane saveCane(Cane cane) {
		getSession(). saveOrUpdate(cane);
		//getSession().flush();
		return cane;
	}
	
	
	

}
