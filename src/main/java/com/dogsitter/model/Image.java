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
package com.dogsitter.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;



/**
 *
 * @author jdmr
 */
@Entity
@Table(name = "images")
@NamedQueries({
    @NamedQuery(name = "images", query = "select i from Image i order by i.id")
})
@JsonIgnoreProperties({"id","thumbnailFilename","newFilename","contentType","dateCreated","lastUpdated","user","tipoRuoli"})
public class Image extends BaseObject implements Serializable {
    private static final long serialVersionUID = -2452384498567415783L;
    
	private Long id;
	private String name;
    private String thumbnailFilename;
    private String newFilename;
    private String contentType;
    private Long size;
    private Long thumbnailSize;
    private Date dateCreated;
    private Date lastUpdated;
    private boolean immaginePreferita;
    private String url;
    
    private String thumbnailUrl;
    private String deleteUrl;
    private String deleteType;
    private String valueImagePreferita;
    
    private String checkBoxId;
    
   
    
    
    private User user;
    private TipoRuoliServiceDog tipoRuoli;

	@ManyToOne
	@JoinColumn(name = "id_user", unique = false, nullable = true)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_tipo", unique = false, nullable = true)
    public TipoRuoliServiceDog getTipoRuoli() {
		return tipoRuoli;
	}
	public void setTipoRuoli(TipoRuoliServiceDog tipoRuoli) {
		this.tipoRuoli = tipoRuoli;
	}
    
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    
    public String getThumbnailFilename() {
        return thumbnailFilename;
    }


    public void setThumbnailFilename(String thumbnailFilename) {
        this.thumbnailFilename = thumbnailFilename;
    }


    public String getNewFilename() {
        return newFilename;
    }


    public void setNewFilename(String newFilename) {
        this.newFilename = newFilename;
    }


    public String getContentType() {
        return contentType;
    }


    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    
    @Column(name = "size_")
    public Long getSize() {
        return size;
    }


    public void setSize(Long size) {
        this.size = size;
    }


    public Long getThumbnailSize() {
        return thumbnailSize;
    }


    public void setThumbnailSize(Long thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

    
    //@Temporal(TemporalType.TIMESTAMP) originale ma da problemi nella esportazione dei dati dal client db HeidiSQL
    @Temporal(TemporalType.DATE)
    //@Temporal(TemporalType.TIME)
    public Date getDateCreated() {
        return dateCreated;
    }


    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    

    //@Temporal(TemporalType.TIMESTAMP) originale ma da problemi nella esportazione dei dati dal client db HeidiSQL
    @Temporal(TemporalType.DATE)
    //@Temporal(TemporalType.TIME)
    public Date getLastUpdated() {
        return lastUpdated;
    }


    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isImmaginePreferita() {
		return immaginePreferita;
	}

	public void setImmaginePreferita(boolean immaginePreferita) {
		this.immaginePreferita = immaginePreferita;
	}

	@Transient
	public String getValueImagePreferita() {
		return valueImagePreferita;
	}

	public void setValueImagePreferita(String valueImagePreferita) {
		this.valueImagePreferita = valueImagePreferita;
	}

	
	@Transient
    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    @Transient
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Transient
    public String getDeleteUrl() {
        return deleteUrl;
    }


    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    @Transient
    public String getDeleteType() {
        return deleteType;
    }


    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }
    
    @Transient
    public String getCheckBoxId() {
		return checkBoxId;
	}
	public void setCheckBoxId(String checkBoxId) {
		this.checkBoxId = checkBoxId;
	}
	
	
	@Override
    public String toString() {
        return "Image{" + "name=" + name + ", thumbnailFilename=" + thumbnailFilename + ", newFilename=" + newFilename + ", contentType=" + contentType + ", url=" + url + ", thumbnailUrl=" + thumbnailUrl + ", deleteUrl=" + deleteUrl + ", deleteType=" + deleteType + '}';
    }

	/* (non-Javadoc)
	 * @see com.dogsitter.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.dogsitter.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
    
    
}
