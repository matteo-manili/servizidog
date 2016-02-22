package com.dogsitter.dao.hibernate;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;



/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@EnableTransactionManagement
@Repository("AnnunciKijijiDogSitterDao")
public class AnnunciKijijiDogSitterDaoHibernate extends GenericDaoHibernate<AnnunciKijijiDogSitter, Long> implements AnnunciKijijiDogSitterDao {

	
	private Criterion criterion_ANNUNCIO_ATTIVO = Restrictions.eq("attivo", true);
	
	public AnnunciKijijiDogSitterDaoHibernate() {
		super(AnnunciKijijiDogSitter.class);
	}
	
	
	
	
	@Override
    @Transactional(readOnly = true)
	public AnnunciKijijiDogSitter get(Long id){
		AnnunciKijijiDogSitter annunciKijijiDogSitter = (AnnunciKijijiDogSitter) getSession().get(AnnunciKijijiDogSitter.class, id);
		return annunciKijijiDogSitter;
	}
	
	
	
	@Override
    @Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public AnnunciKijijiDogSitter getAnnuncioByTelefono(String telefono) {
		List<AnnunciKijijiDogSitter> annunci = getSession().createCriteria(AnnunciKijijiDogSitter.class).add(Restrictions.eq("telefono", telefono)).list();
        if (annunci == null || annunci.isEmpty()) {
        	return null;
        } else {
            return annunci.get(0);
        }
    }
	
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter() {
		return getSession().createCriteria(AnnunciKijijiDogSitter.class).list();
	}
	

	@Override
	@Transactional
	public Long getAnnunciKijijiDogSitter_COUNT() {
		return (Long) getSession().createCriteria(AnnunciKijijiDogSitter.class)
			.setProjection(Projections.rowCount())
			.add(criterion_ANNUNCIO_ATTIVO).uniqueResult();
	}
	
	
	@Override
	@Transactional
	public Long getAnnunciKijijiDogSitter_TOTALE_COUNT() {
		return (Long) getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_AGGIUSTA_ANNUNCI() {

		 Criterion criterion1 = Restrictions.or(Restrictions.isNull("urlProfilo"), Restrictions.eq("urlProfilo", ""));
		 Criterion criterion2 = Restrictions.or(Restrictions.isNull("dataPubblicazione"));
		 Criterion criterion3 = Restrictions.or(Restrictions.eq("lat", new Double(0)), Restrictions.eq("lng", new Double(0)));
		 Criterion criterion4 = Restrictions.or(Restrictions.isNull("tariffa"), Restrictions.eq("tariffa", ""), Restrictions.eq("tariffa", "0"));
		 
		 Criteria criteria = getSession().createCriteria(AnnunciKijijiDogSitter.class)
					.setProjection(Projections.projectionList()
						.add(Projections.property("idAnnuncio"), "idAnnuncio")
						.add(Projections.property("nomeUtente"), "nomeUtente")
						.add(Projections.property("dataPubblicazione"), "dataPubblicazione")
						.add(Projections.property("dataPubblicazioneString"), "dataPubblicazioneString")
						.add(Projections.property("tariffa"), "tariffa")
						.add(Projections.property("lat"), "lat")
						.add(Projections.property("lng"), "lng")
						.add(Projections.property("luogo"), "luogo")
						.add(Projections.property("urlProfilo"), "urlProfilo"))
					.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
					.add(Restrictions.or( criterion1, criterion2, criterion3, criterion4 ));
		 return criteria.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> getAnnunciKijijiDogSitter_VALORI_DISTINCT() {
		Criteria criteria2 = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection( Projections.distinct(Projections.property("luogo")))
				.add(criterion_ANNUNCIO_ATTIVO);
		 return criteria2.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_LUOGO(String luogo) {
		Criteria criteria = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.projectionList()
						.add(Projections.property("idAnnuncio"), "idAnnuncio"))
					.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
					.add(Restrictions.eq("luogo", luogo)).add(criterion_ANNUNCIO_ATTIVO);
			return criteria.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_RICERCA() {
		Criteria criteria2 = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("nomeUtente"), "nomeUtente")
					.add(Projections.property("tariffa"), "tariffa")
					.add(Projections.property("luogo"), "luogo")
					.add(Projections.property("descrizione"), "descrizione")
					.add(Projections.property("telefono"), "telefono")
					.add(Projections.property("urlProfilo"), "urlProfilo")
					.add(Projections.property("nomeImmagine"), "nomeImmagine")
					.add(Projections.property("idAnnuncio"), "idAnnuncio"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
				.add(criterion_ANNUNCIO_ATTIVO)
				.addOrder(Order.desc("dataPubblicazione")); //dall'annuncio più nuovo...
		return criteria2.list();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_RICERCA_MAPPA() {
		Criteria criteria2 = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("nomeUtente"), "nomeUtente")
					.add(Projections.property("tariffa"), "tariffa")
					.add(Projections.property("luogo"), "luogo")
					.add(Projections.property("descrizione"), "descrizione")
					.add(Projections.property("telefono"), "telefono")
					.add(Projections.property("urlProfilo"), "urlProfilo")
					.add(Projections.property("nomeImmagine"), "nomeImmagine")
					.add(Projections.property("lat"), "lat")
					.add(Projections.property("lng"), "lng")
					.add(Projections.property("idAnnuncio"), "idAnnuncio"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
				.add(Restrictions.and( Restrictions.gt("lat", 0.0 ), Restrictions.gt("lng", 0.0 ))).add(criterion_ANNUNCIO_ATTIVO)
				.addOrder(Order.desc("dataPubblicazione")); //dall'annuncio più nuovo...
		return criteria2.list();	
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_IMMAGINI() {
		
		Criterion criterion1 = Restrictions.and(criterion_ANNUNCIO_ATTIVO, Restrictions.isNotNull("immagine1"), Restrictions.isNotNull("nomeImmagine"));
		
		Criteria criteria2 = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("immagine1"), "immagine1")
					.add(Projections.property("immagine2"), "immagine2")
					.add(Projections.property("immagine3"), "immagine3")
					.add(Projections.property("nomeImmagine"), "nomeImmagine"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
				.add( criterion1 );
		
		return criteria2.list();	
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciKijijiDogSitter> getAnnunciDogSitter_IMMAGINI_LIMIT(long first, long max) {  

		Criterion criterion1 = Restrictions.and(Restrictions.isNotNull("immagine1"), Restrictions.isNotNull("nomeImmagine"));
		Criterion criterion2 = Restrictions.between("idAnnuncio", first, max);
		
		Criteria criteria = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.projectionList()
						.add(Projections.property("immagine1"), "immagine1")
						.add(Projections.property("immagine2"), "immagine2")
						.add(Projections.property("immagine3"), "immagine3")
						.add(Projections.property("nomeImmagine"), "nomeImmagine"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
				.add( criterion1 ).add( criterion2 );
		
		return criteria.list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long getAnnunciKijijiDogSitter_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI_COUNT(ArrayList<Long> annunciSalvati_e_aggiornati) {
		
    	String queryString = "SELECT COUNT(annunci.idAnnuncio) "
    			+ "FROM AnnunciKijijiDogSitter AS annunci "
    			+ "WHERE annunci.idAnnuncio IN (:listIDAnnunci) AND annunci.immagine1 IS NOT NULL AND annunci.nomeImmagine IS NOT NULL ";
    	Query query = getSession().createQuery(queryString); 
    	query.setParameterList("listIDAnnunci", annunciSalvati_e_aggiornati);
    	
    	return  (Long) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getAnnunciKijijiDogSitter_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI(ArrayList<Long> annunciSalvati_e_aggiornati, int first, int max) {
		
    	String queryString = "SELECT annunci.idAnnuncio, annunci.immagine1, annunci.immagine2, annunci.immagine3, annunci.nomeImmagine "
    			+ "FROM AnnunciKijijiDogSitter AS annunci "
    			+ "WHERE annunci.idAnnuncio IN (:listIDAnnunci) AND annunci.immagine1 IS NOT NULL AND annunci.nomeImmagine IS NOT NULL ";
    	Query query = getSession().createQuery(queryString); 
    	query.setParameterList("listIDAnnunci", annunciSalvati_e_aggiornati);
    	
    	return  (List<Object[]>) query.list();  
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long getAnnunciDogSitter_MAX_ID() {	
		return (Long) getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection( Projections.max("idAnnuncio") ).uniqueResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_SITEMAP() {
		Criteria criteria2 = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("urlProfilo"), "urlProfilo")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
				.add(criterion_ANNUNCIO_ATTIVO);
		
		return criteria2.list();	
	}

	//----------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_DATE_NULL() {
		
		Criterion criterion1 = Restrictions.and( Restrictions.isNotNull("dataPubblicazione") );

		Criteria criteria = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
				.add( criterion1 );
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_DATE_FUTURE(Date oggi) {
		
		Criterion criterion1 = Restrictions.and( Restrictions.isNotNull("dataPubblicazione"), Restrictions.ge("dataPubblicazione", oggi));

		Criteria criteria = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio")
					.add(Projections.property("dataPubblicazioneString"), "dataPubblicazioneString")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
				.add( criterion1 )
				.addOrder(Order.desc("dataPubblicazione"));
		return criteria.list();
	}
	
	//----------------------------------------
	
	@Override
	@Transactional
	public int getAnnunciKijijiDogSitter_SET_NOME_IMMAGINE_NULL()  {
        String aa = "update AnnunciKijijiDogSitter SET nomeImmagine = NULL "
        		+ "WHERE immagine1 IS NULL";
        Query query2 = getSession().createQuery(aa);
		return query2.executeUpdate();
	}
	
	@Override
	@Transactional
	public int getAnnunciKijijiDogSitter_DATE_ATTIVA_ANNUNCI_NUOVI_UPDATE(Date inizio, Date fine)  {

        String aa = "update AnnunciKijijiDogSitter set attivo = :attivoPar "
        		+ "WHERE dataPubblicazione IS NOT NULL AND "
        		+ "dataPubblicazione BETWEEN :finePar AND :inizioPar ";
        
        Query query2 = getSession().createQuery(aa);
        query2.setParameter("attivoPar", true);
        query2.setParameter("inizioPar", inizio);
		query2.setParameter("finePar", fine);
		
		return query2.executeUpdate();
	}
	
	@Override
	@Transactional
	public int getAnnunciKijijiDogSitter_DATE_DISATTIVA_ANNUNCI_VECCHI_UPDATE(Date vecchieDa) {
		
    	String aa = "update AnnunciKijijiDogSitter set attivo = :attivoPar "
        		+ "WHERE dataPubblicazione IS NOT NULL AND "
        		+ "dataPubblicazione <= :vecchieDaPar  ";
        
        Query query2 = getSession().createQuery(aa);
        query2.setParameter("attivoPar", false);
		query2.setParameter("vecchieDaPar", vecchieDa);
		
		return query2.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_DATE_ATTIVA_ANNUNCI_NUOVI(Date inizio, Date fine) { //non lo uso più
		
		//Conjunction and = Restrictions.conjunction(); //è una prova!
		Criterion criterionBetweenDate = Restrictions.and(Restrictions.isNotNull("dataPubblicazione"), Restrictions.between("dataPubblicazione", fine, inizio));

		Criterion criterion1 = Restrictions.isNotNull("dataPubblicazione");
		Criterion criterion2 = Restrictions.le("dataPubblicazione", inizio); 
		Criterion criterion3 = Restrictions.ge("dataPubblicazione", fine);
		Criterion criterionTOT = Restrictions.and( criterion1, criterion2, criterion3);
		
		Criteria criteria = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
				.add( criterionBetweenDate )
				.addOrder(Order.desc("dataPubblicazione"));
		return criteria.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_DATE_DISATTIVA_ANNUNCI_VECCHI(Date vecchieDa) { //non lo uso più
		
		//Conjunction and = Restrictions.conjunction(); //DA PROVARE !!!
		
		Criterion criterion1 = Restrictions.isNotNull("dataPubblicazione");
		Criterion criterion2 = Restrictions.lt ("dataPubblicazione", vecchieDa);
		
		Criterion criterionTOT = Restrictions.and( criterion1, criterion2);
		
		Criteria criteria = getSession().createCriteria(AnnunciKijijiDogSitter.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciKijijiDogSitter.class))
				.add( criterionTOT)
				.addOrder(Order.desc("dataPubblicazione"));
		return criteria.list();
	}
	
	
	@Override
	@Transactional
	public AnnunciKijijiDogSitter saveAnnuncioDogSitter(AnnunciKijijiDogSitter annunciKijijiDogSitter) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession(). saveOrUpdate(annunciKijijiDogSitter);
		getSession().flush();
		return annunciKijijiDogSitter;
	}


	@Override
	@Transactional
	public AnnunciKijijiDogSitter saveAnnuncioDogSitterNoFlush(AnnunciKijijiDogSitter annunciKijijiDogSitter) throws HibernateJdbcException {
		getSession(). saveOrUpdate(annunciKijijiDogSitter);
		//getSession().flush();
		return annunciKijijiDogSitter;
	}


	@Override
	@Transactional
	public AnnunciKijijiDogSitter eliminaAnnuncioDogSitter(AnnunciKijijiDogSitter annuncio) {
		getSession(). delete(annuncio);
		getSession().flush();
		System.out.println("annucio eliminato "+annuncio.getIdAnnuncio());
		return annuncio;
		
		
	}





	
	

}
