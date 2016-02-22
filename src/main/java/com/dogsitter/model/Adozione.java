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
@Table(name = "adozione")
public class Adozione extends BaseObject implements Serializable {
	private static final long serialVersionUID = -3479894106453827926L;
	
	private Long id;
	private String descrizione;
	private String telefono;
	private String indirizzoCompleto;
	private Address address = new Address();
	private boolean visualizzaCivico;
	private String urlProfilo;
	private Date ultimaModifica;
	
	
	//-------------------- CANE --------------------------
	/*
	private Set<Cane> cane;
	
	
	@OneToMany
	@JoinTable(name = "adozione_gatto",
		joinColumns = @JoinColumn(name = "id_adozione"),
		inverseJoinColumns = @JoinColumn(name = "id_gatto", nullable = false))
	
	
	
	@OneToMany
	@JoinColumn(name = "id_adozione", nullable = false)
	public Set<Cane> getCane() {
		return cane;
	}
	
	
	public void setCane(Set<Cane> cane) {
		this.cane = cane;
	}

*/
	
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
		if(descrizione != null  && indirizzoCompleto != null  && urlProfilo != null  &&  
				address.getAddress() != null && address.getCity() != null && address.getLat() != 0 && address.getLng() != 0 ){
			return true;
		}else{
			return false;
		}
	}
	
	@Transient
	public boolean infoGenerali() {
		if(descrizione != null  && indirizzoCompleto != null  && 
				address.getAddress() != null && address.getCity() != null && address.getLat() != 0 && address.getLng() != 0 ){
			return true;
		}else{
			return false;
		}
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_adozione")
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length = 500)
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		
		descrizione = StringUtils.strip(descrizione);
		this.descrizione = descrizione;
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
	
	
	
	@Column(name = "telefono")
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
