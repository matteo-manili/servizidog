package com.dogsitter.dao;

import java.util.List;
import com.dogsitter.model.Immagini_store;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface ImmaginiDao extends GenericDao<Immagini_store, Long> {
	
	List<Immagini_store> getImmagini();
	
	Immagini_store get(Long id);
	
	Immagini_store saveImmagine(Immagini_store immagini);

	Immagini_store eliminaImmagine(Immagini_store immagini);

}
