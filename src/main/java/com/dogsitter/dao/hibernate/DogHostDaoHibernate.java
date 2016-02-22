package com.dogsitter.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.dogsitter.dao.DogHostDao;
import com.dogsitter.model.DogHost;



/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@EnableTransactionManagement
@Repository("DogHostDao")
public class DogHostDaoHibernate extends GenericDaoHibernate<DogHost, Long> implements DogHostDao {

	public DogHostDaoHibernate() {
		super(DogHost.class);
	}
	
	
	
	@Override
    @Transactional(readOnly = true)
	public DogHost get(Long id){
		DogHost dogHost = (DogHost) getSession().get(DogHost.class, id);
		return dogHost;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<DogHost> getDogHostByUser(long idUser) {
		List<DogHost> listDogHost = new ArrayList<DogHost>();
    	listDogHost = getSession().createCriteria(DogHost.class).add(Restrictions.eq("user.id", idUser)).list(); 
        
        return listDogHost;
		
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<DogHost> getDogHosts() {
        return getSession().createCriteria(DogHost.class).list();
	}
	
	

	@Override
	public DogHost saveDogHost(DogHost dogHost) {
		getSession(). saveOrUpdate(dogHost);
		//getSession().flush();
		return dogHost;
	}
	
	
	

}
