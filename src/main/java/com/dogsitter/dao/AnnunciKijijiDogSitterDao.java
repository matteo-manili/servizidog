package com.dogsitter.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dogsitter.model.AnnunciKijijiDogSitter;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface AnnunciKijijiDogSitterDao extends GenericDao<AnnunciKijijiDogSitter, Long> {
	
	
	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter();
	
	AnnunciKijijiDogSitter get(Long id);
	
	AnnunciKijijiDogSitter getAnnuncioByTelefono(String telefono);
	
	AnnunciKijijiDogSitter saveAnnuncioDogSitter(AnnunciKijijiDogSitter annunciKijijiDogSitter);
	
	AnnunciKijijiDogSitter saveAnnuncioDogSitterNoFlush(AnnunciKijijiDogSitter annunciKijijiDogSitter);

	AnnunciKijijiDogSitter eliminaAnnuncioDogSitter(AnnunciKijijiDogSitter annuncio);
	
	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_IMMAGINI();

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_AGGIUSTA_ANNUNCI();

	List<String> getAnnunciKijijiDogSitter_VALORI_DISTINCT();

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_LUOGO(String luogo);

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_RICERCA();

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_RICERCA_MAPPA();

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_SITEMAP();

	Long getAnnunciKijijiDogSitter_COUNT();

	Long getAnnunciDogSitter_MAX_ID();

	List<AnnunciKijijiDogSitter> getAnnunciDogSitter_IMMAGINI_LIMIT(long first,long max);

	Long getAnnunciKijijiDogSitter_TOTALE_COUNT();

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_DATE_ATTIVA_ANNUNCI_NUOVI(Date inizio, Date fine);

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_DATE_DISATTIVA_ANNUNCI_VECCHI(Date vecchieDa);

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_DATE_FUTURE(Date oggi);

	List<AnnunciKijijiDogSitter> getAnnunciKijijiDogSitter_DATE_NULL();

	int getAnnunciKijijiDogSitter_DATE_ATTIVA_ANNUNCI_NUOVI_UPDATE(Date inizio,Date fine);

	int getAnnunciKijijiDogSitter_DATE_DISATTIVA_ANNUNCI_VECCHI_UPDATE(Date vecchieDa);

	int getAnnunciKijijiDogSitter_SET_NOME_IMMAGINE_NULL();

	List<Object[]> getAnnunciKijijiDogSitter_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI(ArrayList<Long> annunciSalvati_e_aggiornati, int first, int max);

	Long getAnnunciKijijiDogSitter_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI_COUNT(ArrayList<Long> annunciSalvati_e_aggiornati);


	
}
