package com.dogsitter.dao.hibernate;

import com.dogsitter.dao.GestioneApplicazioneDao;
import com.dogsitter.model.GestioneApplicazione;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * This class interacts with hibernate session to save/delete and
 * retrieve Role objects.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @author jgarcia (updated to hibernate 4)
 */

@EnableTransactionManagement
@Repository("GestioneApplicazioneDao")
public class GestioneApplicazioneDaoHibernate extends GenericDaoHibernate<GestioneApplicazione, Long> implements GestioneApplicazioneDao {

    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public GestioneApplicazioneDaoHibernate() {
        super(GestioneApplicazione.class);
    }

    
    @SuppressWarnings("unchecked")
	@Override
    @Transactional(readOnly = true)
    public GestioneApplicazione getGestioneApplicazioneName(String nomeFunzione) {
        List<GestioneApplicazione> gestioneApp = getSession().createCriteria(GestioneApplicazione.class).add(Restrictions.eq("name", nomeFunzione)).list();
        if (gestioneApp.isEmpty()) {
            return null;
        } else {
            return gestioneApp.get(0);
        }
    }
    
    
    @Override
	@Transactional
	public GestioneApplicazione saveGestioneApplicazione(GestioneApplicazione gestioneApplicazione) {
		getSession(). saveOrUpdate(gestioneApplicazione);
		//getSession().flush();
		return gestioneApplicazione;
	}


}
