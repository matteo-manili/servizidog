package com.dogsitter.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@Entity
@Table(name = "associazione")
public class Associazione extends BaseObject implements Serializable {
	private static final long serialVersionUID = -2697089704935133111L;
	
	private Long id;
	private String nome;
	private String descrizione;
	private String website;
	private String telefono;
	private String email;
	
	private String indirizzoCompleto;
	private Address address = new Address();
	private boolean visualizzaCivico;
	private String urlProfilo;
	private Date ultimaModifica;
	

    //-------------------- USER--------------------------

	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_user", unique = false, nullable = true)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
	@Transient
	public boolean informazioniGeneraliInserite() {
		if(nome != null && descrizione != null  && indirizzoCompleto != null  && urlProfilo != null  &&  
				address.getAddress() != null && address.getCity() != null && address.getLat() != 0 && address.getLng() != 0 ){
			return true;
		}else{
			return false;
		}
	}
	
	@Transient
	public boolean infoGenerali() {
		if(nome != null && descrizione != null  && indirizzoCompleto != null  && 
				address.getAddress() != null && address.getCity() != null && address.getLat() != 0 && address.getLng() != 0 ){
			return true;
		}else{
			return false;
		}
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_associazione")
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		
		nome = StringUtils.strip(nome);
		this.nome = nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		
		descrizione = StringUtils.strip(descrizione);
		this.descrizione = descrizione;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		
		website = StringUtils.strip(website);
    	website = StringUtils.lowerCase(website);
		this.website = website;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		
		email = StringUtils.strip(email);
		email = StringUtils.lowerCase(email);
		this.email = email;
	}
	
	@Column(name = "ultima_modifica")
	public Date getUltimaModifica() {
		return ultimaModifica;
	}

	public void setUltimaModifica(Date ultimaModifica) {
		this.ultimaModifica = ultimaModifica;
	}

	
	@Column(name = "indirizzo_completo")
	public String getIndirizzoCompleto() {
		return indirizzoCompleto;
	}

	public void setIndirizzoCompleto(String indirizzoCompleto) {
		this.indirizzoCompleto = indirizzoCompleto;
	}



	@Embedded
    @IndexedEmbedded
    public Address getAddress() {
        return address;
    }
	
	public void setAddress(Address address) {
        this.address = address;
    }
	
	@Column(name = "visualizza_civico", columnDefinition = "boolean default false")
	public boolean isVisualizzaCivico() {
		return visualizzaCivico;
	}

	public void setVisualizzaCivico(boolean visualizzaCivico) {
		this.visualizzaCivico = visualizzaCivico;
	}
	
	@Column(name = "url_profilo")
    public String getUrlProfilo() {
		return urlProfilo;
	}

	public void setUrlProfilo(String urlProfilo) {
		this.urlProfilo = urlProfilo;
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
