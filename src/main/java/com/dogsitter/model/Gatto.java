package com.dogsitter.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@Entity
@Table(name = "gatto")
public class Gatto extends BaseObject implements Serializable {
	private static final long serialVersionUID = 2549135579865256167L;

	private Long id;
	private String nomeGatto;
	private String carattere;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_gatto")
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	@Column(name="nome_gatto", length = 64)
	public String getNomeGatto() {
		return nomeGatto;
	}

	public void setNomeGatto(String nomeGatto) {
		this.nomeGatto = nomeGatto;
	}

	

	@Column(length = 500)
	public String getCarattere() {
		return carattere;
	}

	public void setCarattere(String carattere) {
		this.carattere = carattere;
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
