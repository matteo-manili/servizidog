package com.dogsitter.dao.hibernate;


import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.dogsitter.dao.ImmaginiDao;
import com.dogsitter.model.Immagini_store;



/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@EnableTransactionManagement
@Repository("ImmaginiDao")
public class ImmaginiDaoHibernate extends GenericDaoHibernate<Immagini_store, Long> implements ImmaginiDao {

	public ImmaginiDaoHibernate() {
		super(Immagini_store.class);
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public Immagini_store get(Long id){
		Immagini_store immagini = (Immagini_store) getSession().get(Immagini_store.class, id);
		return immagini;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Immagini_store> getImmagini() {
		return getSession().createCriteria(Immagini_store.class).list();

	}

	@Override
	@Transactional
	public Immagini_store saveImmagine(Immagini_store immagini) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession(). saveOrUpdate(immagini);
		getSession().flush();
		return immagini;
	}





	@Override
	@Transactional
	public Immagini_store eliminaImmagine(Immagini_store immagine) {
		getSession(). delete(immagine);
		getSession().flush();
		System.out.println("annucio eliminato "+immagine.getId());
		return immagine;
		
		
	}





	
	

}
