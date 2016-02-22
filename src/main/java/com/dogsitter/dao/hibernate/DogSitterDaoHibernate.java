package com.dogsitter.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.dogsitter.dao.DogSitterDao;
import com.dogsitter.model.DogSitter;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("DogSitterDao")
public class DogSitterDaoHibernate extends GenericDaoHibernate<DogSitter, Long> implements DogSitterDao {

	public DogSitterDaoHibernate() {
		super(DogSitter.class);
	}
	
	
	
	@Override
    @Transactional(readOnly = true)
	public DogSitter get(Long id){
		DogSitter dogSitter = (DogSitter) getSession().get(DogSitter.class, id);
		return dogSitter;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<DogSitter> getDogSitterByUser(long idUser) {
		List<DogSitter> listDogSitter = new ArrayList<DogSitter>();
    	listDogSitter = getSession().createCriteria(DogSitter.class).add(Restrictions.eq("user.id", idUser)).list(); 
        return listDogSitter;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<DogSitter> getDogSitters() {
        return getSession().createCriteria(DogSitter.class).list();
	}
	
	
	
	

	@Override
	public DogSitter saveDogSitter(DogSitter dogSitter) {
		getSession(). saveOrUpdate(dogSitter);
		//getSession().flush();
		return dogSitter;
	}
	
	
	

}
