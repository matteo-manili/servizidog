package com.dogsitter.dao.hibernate;


import com.dogsitter.dao.TipoRuoliServiceDogDao;
import com.dogsitter.model.TipoRuoliServiceDog;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Repository
public class TipoRuoliServiceDogDaoHibernate extends GenericDaoHibernate<TipoRuoliServiceDog, Long> implements TipoRuoliServiceDogDao {

    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public TipoRuoliServiceDogDaoHibernate() {
        super(TipoRuoliServiceDog.class);
    }
    
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<TipoRuoliServiceDog> getRuoliServiziDogOrdineId() {
        log.debug("Retrieving all ruoli servizi dog names...");
        return getSession().createCriteria(TipoRuoliServiceDog.class).addOrder(Order.asc("id")).list(); 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoRuoliServiceDog getTipoRuoliServiceDogDaoByName(String rolename) {
        List tipoRuoliServiceDog = getSession().createCriteria(TipoRuoliServiceDog.class).add(Restrictions.eq("name", rolename)).list();
        if (tipoRuoliServiceDog.isEmpty()) {
            return null;
        } else {
            return (TipoRuoliServiceDog) tipoRuoliServiceDog.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTipoRuoliServiceDog(String tipoRuoliServiceDogname) {
        Object roleServiceDogname = getTipoRuoliServiceDogDaoByName(tipoRuoliServiceDogname);
        Session session = getSessionFactory().getCurrentSession();
        session.delete(roleServiceDogname);
    }
   
}
