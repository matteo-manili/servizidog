package com.dogsitter.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dogsitter.dao.LookupDao;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.Role;
import com.dogsitter.model.TipoRuoliServiceDog;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 * Hibernate implementation of LookupDao.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *      Modified by jgarcia: updated to hibernate 4
 */
@Repository
public class LookupDaoHibernate implements LookupDao {
    private Log log = LogFactory.getLog(LookupDaoHibernate.class);
    private final SessionFactory sessionFactory;

    /**
     * Initialize LookupDaoHibernate with Hibernate SessionFactory.
     * @param sessionFactory
     */
    @Autowired
    public LookupDaoHibernate(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Role.class).list();
    }
    
    
    @SuppressWarnings("unchecked")
    public List<TipoRuoliServiceDog> getRuoliServiziDog() {
        log.debug("Retrieving all ruoli servizi dog names...");
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(TipoRuoliServiceDog.class).addOrder(Order.asc("id")).list();
    }
    
    
    
    @SuppressWarnings("unchecked")
    public List<DogSitter> getDogSitters() {
        log.debug("Retrieving all Dog Sitter....");
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(DogSitter.class).list();
    }
    
    
    
    
}
