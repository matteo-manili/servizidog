package com.dogsitter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@Entity
//@Table(name = "immagini")

@Table(name = "immagini", uniqueConstraints=
@UniqueConstraint(columnNames = {"newFilename", "thumbnailFilename"})) 

public class Immagini_store extends BaseObject implements Serializable {
    private static final long serialVersionUID = -7855250733213829871L;


	private Long id;
	

    private String newFilename;
	private String thumbnailFilename;
	
	private byte[] imageGrande;
	private byte[] imagePiccola;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_immagine")
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false)
	public String getNewFilename() {
		return newFilename;
	}
	
	public void setNewFilename(String newFilename) {
		this.newFilename = newFilename;
	}
	
	@Column(nullable = false)
	public String getThumbnailFilename() {
		return thumbnailFilename;
	}

	public void setThumbnailFilename(String thumbnailFilename) {
		this.thumbnailFilename = thumbnailFilename;
	}

	@Lob
	@Column(columnDefinition="mediumblob", nullable = false)
	public byte[] getImageGrande() {
		return imageGrande;
	}

	public void setImageGrande(byte[] imageGrande) {
		this.imageGrande = imageGrande;
	}

	@Lob
	@Column(columnDefinition="mediumblob", nullable = false)
	public byte[] getImagePiccola() {
		return imagePiccola;
	}

	public void setImagePiccola(byte[] imagePiccola) {
		this.imagePiccola = imagePiccola;
	}


	


	
	
	
	/* (non-Javadoc)
	 * @see com.dogsitter.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
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
