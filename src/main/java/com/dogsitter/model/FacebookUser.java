package com.dogsitter.model;

import java.io.Serializable;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class FacebookUser implements Serializable {

	private static final long serialVersionUID = -153528348401166543L;

	public FacebookUser() {
		// TODO Auto-generated constructor stub
	}
	
	private String id;
	private String nomeCompleto;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private String locale;
	private Object picture;
	private Object cover;
	
	

	public boolean informazioniGeneraliRicevute() {
		if(!id.equals("") && !nomeCompleto.equals("") && !firstName.equals("") && !lastName.equals("") && !email.equals("")) {
			return true;
		}else{
			return false;
		}
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Object getPicture() {
		return picture;
	}

	public void setPicture(Object picture) {
		this.picture = picture;
	}

	public Object getCover() {
		return cover;
	}

	public void setCover(Object cover) {
		this.cover = cover;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
