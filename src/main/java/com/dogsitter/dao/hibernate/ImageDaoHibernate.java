/*
 * The MIT License
 *
 * Copyright 2013 jdmr.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.dogsitter.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.dogsitter.dao.ImageDao;
import com.dogsitter.model.Image;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jdmr
 */
@EnableTransactionManagement
@Repository("ImageDao")
public class ImageDaoHibernate extends GenericDaoHibernate<Image, Long> implements ImageDao {
    
	public ImageDaoHibernate(){
		super(Image.class);
	}
    

	@SuppressWarnings("unchecked")
	@Override
	public List<Image> listImageUserTipoServizio(long idUser, long idTipo) {
    	List<Image> listImages = new ArrayList<Image>();
    	listImages = getSession().createCriteria(Image.class).add(Restrictions.eq("user.id", idUser)).add(Restrictions.eq("tipoRuoli.id", idTipo)).list(); 
        return listImages;
	}
    
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Image> listImagebyUser(long idUser) {
    	List<Image> listImages = new ArrayList<Image>();
    	listImages = getSession().createCriteria(Image.class).add(Restrictions.eq("user.id", idUser)).list(); 
        return listImages;
	}
	
	

    @Override
    public Image impostaImmaginePreferita(Long id){
    	Image image = (Image) getSession().get(Image.class, id);
    	return image;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Image> controllaImmaginePreferitaExist(long idUser, long idTipo) {
    	
    	List<Image> listImages = new ArrayList<Image>();//tipoRuoli
    	listImages = getSession().createCriteria(Image.class).
    			add(Restrictions.eq("user.id", idUser)).add(Restrictions.eq("tipoRuoli.id", idTipo)).
    			add(Restrictions.eq("immaginePreferita", true)).list(); 
    	
    	return listImages;
    }
    


    @Override
    @Transactional(readOnly = true)
    public List<Image> list() {
        Query query = getSession().getNamedQuery("images");
        @SuppressWarnings("unchecked")
		List<Image> images = query.list();
        return images;
    }

    
    
    @Override
    public Image create(Image image) {
        getSession().saveOrUpdate(image);
        //getSession().flush();
        
        return image;
    }
    

    
    @Override
    @Transactional(readOnly = true)
    public Image get(Long id) {
        Image image = (Image) getSession().get(Image.class, id);
        return image;
    }
    
    
    
    
    @SuppressWarnings("unchecked")
	@Override
    @Transactional(readOnly = true)
    public List<Image> getImmaginePrefeita(long idUser, long idTipo) {
        List<Image> listImages = new ArrayList<Image>();
    	listImages = getSession().createCriteria(Image.class).add(Restrictions.eq("user.id", idUser)).add(Restrictions.eq("tipoRuoli.id", idTipo)).add(Restrictions.eq("immaginePreferita", true)).list(); 
        return listImages;
    }
    
    
    

    @Override
    public void delete(Image image) {
        getSession().delete(image);
    }
    
}
