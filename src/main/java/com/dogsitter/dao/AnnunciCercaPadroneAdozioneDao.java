package com.dogsitter.dao;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dogsitter.model.AnnunciCercaPadroneAdozione;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface AnnunciCercaPadroneAdozioneDao extends GenericDao<AnnunciCercaPadroneAdozione, Long> {
	
	
	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione();
	
	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_AGGIUSTA_ANNUNCI();
	
	AnnunciCercaPadroneAdozione get(Long id);
	
	AnnunciCercaPadroneAdozione getAnnuncioBy_Titolo_e_Mail(String titoloCane, String emailUtente);
	
	AnnunciCercaPadroneAdozione saveAnnuncioAdozione(AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione);
	
	AnnunciCercaPadroneAdozione saveAnnuncioAdozioneNoFlush(AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione);

	AnnunciCercaPadroneAdozione eliminaAnnuncioAdozione(AnnunciCercaPadroneAdozione annuncio);

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_IMMAGINI();

	List<String> getAnnunciCercaPadroneAdozione_VALORI_DISTINCT();

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_LUOGO(String luogo);

	List<String> getAnnunciCercaPadroneAdozione_DESCRIONI_UNIVOCHE();

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_DESCRIZIONE(String descrizione);

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_RICERCA();

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_RICERCA_MAPPA();
/*
	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_SITEMAP_IMMAGINI(); */

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_SITEMAP();

	Long getAnnunciCercaPadroneAdozione_COUNT();

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_TUTTE_DESCRIZIONI();

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_DATE_DISATTIVA_ANNUNCI_VECCHI(Date vecchieDa);

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_DATE_ATTIVA_ANNUNCI_NUOVI(Date inizio, Date fine);

	Long getAnnunciCercaPadroneAdozione_TOTALE_COUNT();

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_IMMAGINI_LIMIT(long first, long max);

	Long getAnnunciCercaPadroneAdozione_MAX_ID();

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_AGGIUSTA_DATA_PUBBLICAZIONE();

	List<AnnunciCercaPadroneAdozione> getAnnunciCercaPadroneAdozione_DATE_FUTURE(Date oggi);

	int getAnnunciCercaPadroneAdozione_DATE_ATTIVA_ANNUNCI_NUOVI_UPDATE(Date inizio, Date fine);

	int getAnnunciCercaPadroneAdozione_DATE_DISATTIVA_ANNUNCI_VECCHI_UPDATE(Date vecchieDa);

	int getAnnunciKijijiDogSitter_SET_NOME_IMMAGINE_NULL();

	List<Object[]> getAnnunciCercaPadroneAdozione_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI(ArrayList<Long> annunciSalvati_e_aggiornati, int first, int max);

	Long getAnnunciCercaPadroneAdozione_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI_COUNT(ArrayList<Long> annunciSalvati_e_aggiornati);

	

	
}
