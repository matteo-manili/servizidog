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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@Entity
@Table(name = "dog_sitter")
public class DogSitter extends BaseObject implements Serializable {
	private static final long serialVersionUID = 4510751785085449362L;

	private Long id;

	private String titolo;
	private String descrizione;

	private Date dataInizioDisponib;
	private Date dataFineDisponib;

	private String indirizzoCompleto;
	private Address address = new Address();
	private String telefono;
	private boolean visualizzaCivico;
	
	private String tariffa;
	
	private String urlProfilo;
	 
	private Date ultimaModifica;
	
	public DogSitter(String titolo, String descrizione, double lng, double lat) {
		super();
		this.titolo = titolo;
		this.descrizione = descrizione;
	}

	public DogSitter() {
		// TODO Auto-generated constructor stub
	}
	
	
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


/*
	private Set<Image> image;
	
	@OneToMany
	 @JoinTable(name = "dogsitter_image",
	 joinColumns = @JoinColumn(name = "id_dogsitter"),
	 inverseJoinColumns = @JoinColumn(name = "id", nullable = false))
	 
	public Set<Image> getImage() {
		return image;
	}

	public void setImage(Set<Image> image) {
		this.image = image;
	}
	
	*/

	//--------------------FINE IMAGE ----------------------------
	/*
   
   private User user;
	
	@ManyToOne
	@JoinColumn(name = "id_dogsitter", nullable = false)
	public User getUser() {
		return user;
	}

    @Lob
    private byte[] picture;

	
    @Column
	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
				
    */		
	
	/*
	@ManyToMany
	 @JoinTable(name = "user_tipo_ruoli",
	 joinColumns = @JoinColumn(name="dog_sitter_id"))
	public Set<TipoRuoliServiceDog> getTipoRuoli() {
		return tipoRuoli;
	}
	
	public void setTipoRuoli(Set<TipoRuoliServiceDog> tipoRuoli) {
		this.tipoRuoli = tipoRuoli;
	}

	*/
	//--------------------------------------
	
	
	
	@Transient
	public boolean informazioniGeneraliInserite() {
		if(titolo != null && descrizione != null  && indirizzoCompleto != null  && urlProfilo != null  &&  
				/* tariffa != null &&  dataInizioDisponib != null && dataFineDisponib != null && */
				address.getAddress() != null && address.getCity() != null && address.getLat() != 0 && address.getLng() != 0 ){
			return true;
		}else{
			return false;
		}
	}
	
	@Transient
	public boolean infoGenerali() {
		if(titolo != null && descrizione != null  && indirizzoCompleto != null  && 
				address.getAddress() != null && address.getCity() != null && address.getLat() != 0 && address.getLng() != 0 ){
			return true;
		}else{
			return false;
		}
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_dogsitter")
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(name = "ultima_modifica")
	public Date getUltimaModifica() {
		return ultimaModifica;
	}

	public void setUltimaModifica(Date ultimaModifica) {
		this.ultimaModifica = ultimaModifica;
	}

	public String getTariffa() {
		return tariffa;
	}

	public void setTariffa(String tariffa) {
		this.tariffa = tariffa;
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

	@Column(length = 64)
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	@Column(length = 500)
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		
		descrizione = StringUtils.strip(descrizione);
		this.descrizione = descrizione;
		
	}
	
	@Column(name = "url_profilo")
    public String getUrlProfilo() {
		return urlProfilo;
	}

	public void setUrlProfilo(String urlProfilo) {
		this.urlProfilo = urlProfilo;
	}

	
	
	@Column(name="inizio_disponibilita")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDataInizioDisponib() {
		return dataInizioDisponib;
	}

	public void setDataInizioDisponib(Date dataInizioDisponib) {
		this.dataInizioDisponib = dataInizioDisponib;
	}
	
    @Column(name="fine_disponibilita")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getDataFineDisponib() {
		return dataFineDisponib;
	}

	public void setDataFineDisponib(Date dataFineDisponib) {
		this.dataFineDisponib = dataFineDisponib;
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
