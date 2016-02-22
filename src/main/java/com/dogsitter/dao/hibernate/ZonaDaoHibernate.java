package com.dogsitter.dao.hibernate;

import java.util.ArrayList;
import java.util.List;






import com.dogsitter.model.Comuni;
import com.dogsitter.dao.ZonaDao;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
 
/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Repository("zonaDao")
public class ZonaDaoHibernate extends GenericDaoHibernate<Object, Long> implements ZonaDao {
 
    public ZonaDaoHibernate() {
    	super(Object.class);
		// TODO Auto-generated constructor stub
	}
   

	@SuppressWarnings("unchecked")
	@Override
	public List<Comuni> findByZonaRicercaGoogleMap(String term) {
    	List<Comuni> listComuni = new ArrayList<Comuni>();
    	listComuni = getSession().createCriteria(Comuni.class).add(Restrictions.like("nomeComune", term+"%")).addOrder(Order.asc("nomeComune")).list(); 
        return listComuni;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Comuni> dammiComuni(String term) {
    	List<Comuni> listComuni = new ArrayList<Comuni>();
    	listComuni = getSession().createCriteria(Comuni.class).add(Restrictions.like("nomeComune", term+"%")).addOrder(Order.asc("nomeComune")).list(); 
        return listComuni;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findByZonaRicercaGoogleMapComuneProvinciaRegione(String term) {
		List<Object> list;
		String query4 = "SELECT a, b, c FROM Comuni a, Province b, Regioni c "
				+ "WHERE a.idProvincia = b.id AND "
				+ "b.idRegione = c.id AND "
				+ "nome_comune like :term "
				+ "ORDER BY nome_comune ASC ";
		
		list = getSession().createQuery(query4).setParameter("term", term+"%").list();
        return list;
	}
    
    
}
