package com.dogsitter.dao.hibernate;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import net.sf.ehcache.hibernate.HibernateUtil;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;



/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@EnableTransactionManagement
@Repository("AnnunciCercaPadroneAdozioneDao")
public class AnnunciCercaPadroneAdozioneDaoHibernate extends GenericDaoHibernate<AnnunciCercaPadroneAdozione, Long> implements AnnunciCercaPadroneAdozioneDao {

	private Criterion criterion_ANNUNCIO_ATTIVO = Restrictions.eq("attivo", true);
	
	public AnnunciCercaPadroneAdozioneDaoHibernate() {
		super(AnnunciCercaPadroneAdozione.class);
	}
	
	
	
	
	@Override
    @Transactional(readOnly = true)
	public AnnunciCercaPadroneAdozione get(Long id){
		AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione = (AnnunciCercaPadroneAdozione) getSession().get(AnnunciCercaPadroneAdozione.class, id);
		return annunciCercaPadroneAdozione;
	}
	
	/*
	@Override
    @Transactional(readOnly = true)
	public AnnunciCercaPadroneAdozione get_ID(Long id){

		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
						.add(Projections.property("idAnnuncio"), "idAnnuncio")
						.add(Projections.property("attivo"), "attivo"))
						.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
                .add(Restrictions.eq("idAnnuncio", id));
		
		return (AnnunciCercaPadroneAdozione) criteria.uniqueResult();
	}
	*/

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione() {
		return getSession().createCriteria(AnnunciCercaPadroneAdozione.class).list();
	}
	
	
	@Override
    @Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public AnnunciCercaPadroneAdozione getAnnuncioBy_Titolo_e_Mail(String titoloCane, String emailUtente) {
		List<AnnunciCercaPadroneAdozione> annunci = getSession().createCriteria(AnnunciCercaPadroneAdozione.class).add( 
				Restrictions.and(Restrictions.eq("titoloCane", titoloCane), Restrictions.eq("email", emailUtente))).list() ;
        if (annunci == null || annunci.isEmpty()) {
        	return null;
        } else {
            return annunci.get(0);
        }
    }
	
	
	@Override
	@Transactional
	public Long getAnnunciCercaPadroneAdozione_COUNT() {
		return (Long) getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.rowCount())
				.add(criterion_ANNUNCIO_ATTIVO).uniqueResult();
	}
	
	@Override
	@Transactional
	public Long getAnnunciCercaPadroneAdozione_TOTALE_COUNT() {
		return (Long) getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_AGGIUSTA_ANNUNCI() {

		Criterion criterion1 = Restrictions.or(Restrictions.isNull("urlProfilo"), Restrictions.eq("urlProfilo", ""));
		Criterion criterion2 = Restrictions.or(Restrictions.isNull("dataPubblicazione"));
		Criterion criterion3 = Restrictions.or(Restrictions.eq("lat", new Double(0)), Restrictions.eq("lng", new Double(0)) );
	
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
			.setProjection(Projections.projectionList()
				.add(Projections.property("idAnnuncio"), "idAnnuncio")
				.add(Projections.property("telefono"), "telefono")
				.add(Projections.property("dataPubblicazione"), "dataPubblicazione")
				.add(Projections.property("dataPubblicazioneString"), "dataPubblicazioneString")
				.add(Projections.property("lat"), "lat")
				.add(Projections.property("lng"), "lng")
				.add(Projections.property("luogo"), "luogo")
				.add(Projections.property("urlProfilo"), "urlProfilo"))
			.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
			.add(Restrictions.or( criterion1, criterion2, criterion3 ));
		 return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> getAnnunciCercaPadroneAdozione_VALORI_DISTINCT() {
		Criteria criteria2 = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection( Projections.distinct(Projections.property("luogo") )).add(criterion_ANNUNCIO_ATTIVO);
		 return criteria2.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_LUOGO(String luogo) {

		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				.add(Restrictions.eq("luogo", luogo)).add(criterion_ANNUNCIO_ATTIVO);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> getAnnunciCercaPadroneAdozione_DESCRIONI_UNIVOCHE() {
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection( Projections.distinct( Projections.property("descrizione") ));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_DESCRIZIONE(String descrizione) {
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))

				.add(Restrictions.eq("descrizione", descrizione));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_TUTTE_DESCRIZIONI() {
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("descrizione"), "descrizione")
					.add(Projections.property("idAnnuncio"), "idAnnuncio"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class));
		return criteria.list();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_RICERCA() {
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("titoloCane"), "titoloCane")
					.add(Projections.property("luogo"), "luogo")
					.add(Projections.property("descrizione"), "descrizione")
					.add(Projections.property("telefono"), "telefono")
					.add(Projections.property("urlProfilo"), "urlProfilo")
					.add(Projections.property("nomeImmagine"), "nomeImmagine")
					.add(Projections.property("idAnnuncio"), "idAnnuncio"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				.add(criterion_ANNUNCIO_ATTIVO)
				.addOrder(Order.asc("dataPubblicazione")); //dall'annuncio più vecchio...
		return criteria.list();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_RICERCA_MAPPA() {
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio")
					.add(Projections.property("titoloCane"), "titoloCane")
					.add(Projections.property("luogo"), "luogo")
					.add(Projections.property("telefono"), "telefono")
					.add(Projections.property("urlProfilo"), "urlProfilo")
					.add(Projections.property("nomeImmagine"), "nomeImmagine")
					.add(Projections.property("lat"), "lat")
					.add(Projections.property("lng"), "lng"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				.add(Restrictions.and( Restrictions.gt("lat", 0.0 ), Restrictions.gt("lng", 0.0 ))).add(criterion_ANNUNCIO_ATTIVO)
				.addOrder(Order.asc("dataPubblicazione")); //dall'annuncio più vecchio...
		return criteria.list();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_IMMAGINI() {

		Criterion criterion1 = Restrictions.and(criterion_ANNUNCIO_ATTIVO, Restrictions.isNotNull("immagine1"), Restrictions.isNotNull("nomeImmagine"));
		
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("immagine1"), "immagine1")
					.add(Projections.property("nomeImmagine"), "nomeImmagine"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				.add( criterion1 )
				//.setFirstResult(0)
				//.setMaxResults(500)
				;
		
		
		return criteria.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_IMMAGINI_LIMIT(long first, long max) {
		
		Criterion criterion1 = Restrictions.and(Restrictions.isNotNull("immagine1"), Restrictions.isNotNull("nomeImmagine"));
		Criterion criterion2 = Restrictions.between("idAnnuncio", first, max);
		
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("immagine1"), "immagine1")
					.add(Projections.property("nomeImmagine"), "nomeImmagine"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				.add( criterion1 ).add( criterion2 );
				//.setFirstResult(first)
				//.setMaxResults(max);
		
		
		return criteria.list();
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public Long getAnnunciCercaPadroneAdozione_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI_COUNT(ArrayList<Long> annunciSalvati_e_aggiornati) {
		
    	String queryString = "SELECT COUNT(idAnnuncio) FROM AnnunciCercaPadroneAdozione  "
    			+ "WHERE idAnnuncio IN (:listIDAnnunci) AND immagine1 IS NOT NULL AND nomeImmagine IS NOT NULL ";
    	Query query = getSession().createQuery(queryString);
    	query.setParameterList("listIDAnnunci", annunciSalvati_e_aggiornati);
    	
    	return  (Long) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getAnnunciCercaPadroneAdozione_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI(ArrayList<Long> annunciSalvati_e_aggiornati, int first, int max) {
		
    	String queryString = "SELECT annunci.idAnnuncio, annunci.immagine1, annunci.nomeImmagine FROM AnnunciCercaPadroneAdozione AS annunci "
    			+ "WHERE annunci.idAnnuncio IN (:listIDAnnunci) AND annunci.immagine1 IS NOT NULL AND annunci.nomeImmagine IS NOT NULL ";
    	Query query = getSession().createQuery(queryString) .setFirstResult(first).setMaxResults(max); 
    	query.setParameterList("listIDAnnunci", annunciSalvati_e_aggiornati);
    	
    	//ScrollableResults resultScroll = query.scroll(ScrollMode.FORWARD_ONLY);
    	
    	return  (List<Object[]>) query. list();  
	}
	
	
	
	
	@Override
	@Transactional(readOnly = true)
	public Long getAnnunciCercaPadroneAdozione_MAX_ID() {
		/*
		DetachedCriteria maxId = DetachedCriteria.forClass(AnnunciCercaPadroneAdozione.class)
			    .setProjection( Projections.max("idAnnuncio") );
		getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
	    .add( Property.forName("idAnnuncio").eq(maxId) )
	    .list();
		*/
		
		return (Long) getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection( Projections.max("idAnnuncio") ).uniqueResult();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_SITEMAP() {
		
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("urlProfilo"), "urlProfilo")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				
				.add(criterion_ANNUNCIO_ATTIVO);
		return criteria.list();
	}
	

	
	/*
	@Override
	@Transactional(readOnly = true)
	public Long getAnnunciCercaPadroneAdozione_DATE_COUNT(Date inizio, Date fine) {
		
		Criterion criterion1 = Restrictions.isNotNull("dataPubblicazione");
		Criterion criterion2 = Restrictions.le("dataPubblicazione", inizio); 
		Criterion criterion3 = Restrictions.ge("dataPubblicazione", fine);
		
		return (Long) getSession().createCriteria(AnnunciCercaPadroneAdozione.class).add( criterion1 ).add(criterion2).add(criterion3)
				.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_DATE(Date inizio, Date fine) {
		
		//Conjunction and = Restrictions.conjunction(); //è una prova!
		
		Criterion criterion1 = Restrictions.isNotNull("dataPubblicazione");
		Criterion criterion2 = Restrictions.le("dataPubblicazione", inizio); 
		Criterion criterion3 = Restrictions.gt("dataPubblicazione", fine);
		
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione")
					.add(Projections.property("titoloCane"), "titoloCane"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				
				.add( criterion1 ).add(criterion2).add(criterion3)
				.addOrder(Order.desc("dataPubblicazione"));
		
		return criteria.list();
	}
	*/
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_DATE_FUTURE(Date oggi) {
		
		Criterion criterion1 = Restrictions.and( Restrictions.isNotNull("dataPubblicazione"), Restrictions.ge("dataPubblicazione", oggi));

		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				.add( criterion1 )
				.addOrder(Order.desc("dataPubblicazione"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_AGGIUSTA_DATA_PUBBLICAZIONE() {
		
		Criterion criterion1 = Restrictions.isNotNull("dataPubblicazioneString");

		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio")
					.add(Projections.property("dataPubblicazioneString"), "dataPubblicazioneString")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				.add( criterion1 );
		return criteria.list();
	}
	
	@Override
	@Transactional
	public int getAnnunciKijijiDogSitter_SET_NOME_IMMAGINE_NULL()  {
        String aa = "update AnnunciCercaPadroneAdozione SET nomeImmagine = NULL "
        		+ "WHERE immagine1 IS NULL";
        Query query2 = getSession().createQuery(aa);
		return query2.executeUpdate();
	}
	
	@Override
	@Transactional
	public int getAnnunciCercaPadroneAdozione_DATE_ATTIVA_ANNUNCI_NUOVI_UPDATE(Date inizio, Date fine)  {
    	/*
    	Query query = getSession().createQuery("from AnnunciCercaPadroneAdozione where id_annuncio = '1' ");
    	List list = query.list();
        */
    	
    	/*
        Query query1 = getSession().createQuery("update AnnunciCercaPadroneAdozione set attivo = :attivoPar " +
				"where id_annuncio = :id_annuncioPar");
		query1.setParameter("attivoPar", true);
		query1.setParameter("id_annuncioPar", 1L);
		int result1 = query1.executeUpdate();
        System.out.println("result="+result1);
        */
        
        String aa = "update AnnunciCercaPadroneAdozione set attivo = :attivoPar "
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
	public int getAnnunciCercaPadroneAdozione_DATE_DISATTIVA_ANNUNCI_VECCHI_UPDATE(Date vecchieDa) {
		
    	String aa = "update AnnunciCercaPadroneAdozione set attivo = :attivoPar "
        		+ "WHERE dataPubblicazione IS NOT NULL AND "
        		+ "dataPubblicazione <= :vecchieDaPar  ";
        
        Query query2 = getSession().createQuery(aa);
        query2.setParameter("attivoPar", false);
		query2.setParameter("vecchieDaPar", vecchieDa);
		
		return query2.executeUpdate();
	}
    
    //----------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_DATE_ATTIVA_ANNUNCI_NUOVI(Date inizio, Date fine) { //non lo uso più
		
		//Conjunction and = Restrictions.conjunction(); //è una prova!
		Criterion criterionBetweenDate = Restrictions.and(Restrictions.isNotNull("dataPubblicazione"), Restrictions.between("dataPubblicazione", fine, inizio));

		Criterion criterion1 = Restrictions.isNotNull("dataPubblicazione");
		Criterion criterion2 = Restrictions.le("dataPubblicazione", inizio); 
		Criterion criterion3 = Restrictions.ge("dataPubblicazione", fine);
		Criterion criterion4 = Restrictions.between("dataPubblicazione", fine, inizio  );
		
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				.add( criterionBetweenDate )
				.addOrder(Order.desc("dataPubblicazione"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_DATE_DISATTIVA_ANNUNCI_VECCHI(Date vecchieDa) { //non lo uso più
		
		//Conjunction and = Restrictions.conjunction(); //DA PROVARE !!!
		
		Criterion criterion1 = Restrictions.isNotNull("dataPubblicazione");
		Criterion criterion2 = Restrictions.lt("dataPubblicazione", vecchieDa);
		
		Criterion criterionTOT = Restrictions.and( criterion1, criterion2);
		
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class))
				.add( criterionTOT )
				.addOrder(Order.desc("dataPubblicazione"));
		return criteria.list();
	}
	
	/*
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_ID_ATTIVO() {

		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("idAnnuncio"), "idAnnuncio")
					.add(Projections.property("attivo"), "attivo"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class));
		return criteria.list();
	}
	*/
	
	/*
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_SITEMAP_IMMAGINI() {
		Criteria criteria = getSession().createCriteria(AnnunciCercaPadroneAdozione.class)
				.setProjection(Projections.projectionList()
					.add(Projections.property("immagine1"), "immagine1")
					.add(Projections.property("nomeImmagine"), "nomeImmagine")
					.add(Projections.property("urlProfilo"), "urlProfilo")
					.add(Projections.property("dataPubblicazione"), "dataPubblicazione"))
				.setResultTransformer(Transformers.aliasToBean(AnnunciCercaPadroneAdozione.class));
		return criteria.list();
	}
*/

	@Override
	@Transactional
	public AnnunciCercaPadroneAdozione saveAnnuncioAdozione(AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione) throws DataIntegrityViolationException, HibernateJdbcException {
		getSession(). saveOrUpdate(annunciCercaPadroneAdozione);
		getSession().flush();
		return annunciCercaPadroneAdozione;
	}


	@Override
	@Transactional
	public AnnunciCercaPadroneAdozione saveAnnuncioAdozioneNoFlush(AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione) throws HibernateJdbcException {
		getSession(). saveOrUpdate(annunciCercaPadroneAdozione);
		//getSession().flush();
		return annunciCercaPadroneAdozione;
	}


	@Override
	@Transactional
	public AnnunciCercaPadroneAdozione eliminaAnnuncioAdozione(AnnunciCercaPadroneAdozione annuncio) {
		getSession(). delete(annuncio);
		getSession().flush();
		System.out.println("annucio eliminato "+annuncio.getIdAnnuncio());
		return annuncio;
		
		
	}





	
	

}
