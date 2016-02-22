package com.dogsitter.service;

import java.util.List;

import com.dogsitter.dao.ImageDao;
import com.dogsitter.model.Image;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface ImageManager extends GenericManager<Image, Long> {
	
	void setImageDao(ImageDao imageDao);
	
	
	
	
	Image getImmaginePrefeita(long userId, long idTipo);
	
	boolean controllaImmaginePreferitaExistHome(long idUser, long idTipo);
	
	void rimuoviTutteImmagineByUser(long idUser);
	
	List<Image> listImageUserTipoServizio(long idUser, long idTipo);

	void impostaImmaginePreferita(Long id, long idUser, long idTipo);
	
	boolean controllaImmaginePreferitaExist(long idUser, long idTipo);
	
	void impostaPreferitaPrimaDiCancellare(Image imageDel);
	
	void impostaImmaginePreferitaButton(Image image);

    List<Image> list();
    
    Image create(Image image);
    
    void delete(Image image);
    
    Image get(Long id);



}
