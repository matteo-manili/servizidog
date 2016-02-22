package com.dogsitter.service.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogsitter.dao.ImageDao;
import com.dogsitter.model.Image;
import com.dogsitter.service.ImageManager;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Service("ImageManager")
public class ImageManagerImpl extends GenericManagerImpl<Image, Long> implements ImageManager {

	private ImageDao imageDao;
	
	
	@Override
    @Autowired
	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}
	
	@Override
	public List<Image> listImageUserTipoServizio(long idUser, long idTipo){
		return this.imageDao.listImageUserTipoServizio(idUser, idTipo);
	}
	
	
	@Override
	public Image getImmaginePrefeita(long userId, long idTipo){
		if(this.imageDao.getImmaginePrefeita(userId, idTipo).iterator().hasNext()){
			return this.imageDao.getImmaginePrefeita(userId, idTipo).iterator().next();
		}else{
			return null;
		}
	}
	
	@Override
	public void rimuoviTutteImmagineByUser(long idUser) {
		List<Image> listImages = new ArrayList<Image>();
    	listImages = this.imageDao.listImagebyUser(idUser);
    	Iterator<Image> iterator = listImages.iterator();
    	Image imageRemove = null;
    	while ( iterator.hasNext() ) {
    		imageRemove = new Image();
    		imageRemove = iterator.next();
    		this.imageDao.delete(imageRemove);
    	}
	}
	
	@Override
	public void impostaImmaginePreferita(Long idImagePref, long idUser, long idTipo){
		List<Image> listImages = new ArrayList<Image>();
    	listImages =  this.imageDao.listImageUserTipoServizio(idUser, idTipo);
    	Iterator<Image> iterator = listImages.iterator();
    	Image imageUpdate = null;
    	while ( iterator.hasNext() ) {
    		imageUpdate = new Image();
    		imageUpdate = iterator.next();
    		imageUpdate.setImmaginePreferita(false);
    		this.imageDao.create(imageUpdate);
    	}
		Image image = new Image();
		image = this.imageDao.impostaImmaginePreferita(idImagePref);
		image.setImmaginePreferita(true);
		this.imageDao.create(image);
	}
	
	@Override
	public void impostaImmaginePreferitaButton(Image imagePref){
		List <Image> listImage = this.listImageUserTipoServizio(imagePref.getUser().getId(), imagePref.getTipoRuoli().getId());
		if( listImage.size() > 1){
			Iterator<Image> iterator = listImage.iterator();
	    	Image imageSetFalse = null;
	    	while ( iterator.hasNext() ) {
	    		imageSetFalse = new Image();
	    		imageSetFalse = iterator.next();
	    		if (imageSetFalse.getId() != imagePref.getId()){
	    			imageSetFalse.setImmaginePreferita(false);
		    		this.imageDao.create(imageSetFalse);
	    		}
	    	}
		}
		Image imageSalvaImage = this.imageDao.get(imagePref.getId());
		imageSalvaImage.setImmaginePreferita(true);
		this.imageDao.create(imageSalvaImage);
	}
	
	@Override
	public void impostaPreferitaPrimaDiCancellare(Image imageDel){
		if(imageDel.isImmaginePreferita()){
			if( this.listImageUserTipoServizio(imageDel.getUser().getId(), imageDel.getTipoRuoli().getId()).size() > 1){
				List <Image> listImage = this.listImageUserTipoServizio(imageDel.getUser().getId(), imageDel.getTipoRuoli().getId());
				Iterator<Image> iterator = listImage.iterator();
		    	Image imageUpdate = null;
		    	while ( iterator.hasNext() ) {
		    		imageUpdate = new Image();
		    		imageUpdate = iterator.next();
		    		if (imageUpdate.getId() != imageDel.getId()){
			    		imageUpdate.setImmaginePreferita(true);
			    		this.imageDao.create(imageUpdate);
		    		}
		    	}
			}
		}
	}
	
	@Override
	public void delete(Image imageDel) {
		this.imageDao.delete(imageDel);
	}
	
	public boolean controllaImmaginePreferitaExistHome(long idUser, long idTipo) {
		Iterator<Image> ite = imageDao.controllaImmaginePreferitaExist(idUser, idTipo).iterator();
		if(ite.hasNext()){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean controllaImmaginePreferitaExist(long idUser, long idTipo) {
		List<Image> list = imageDao.controllaImmaginePreferitaExist(idUser, idTipo);
		//non modificare!
		if(list.size() == 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public List<Image> list() {
		return this.imageDao.list();
	}

	@Override
	public Image create(Image image) {
		return this.imageDao.create(image);
	}
	
	@Override
	public Image get(Long id) {
		return this.imageDao.get(id);
	}


}
