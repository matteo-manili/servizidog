package com.dogsitter.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;




/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@Entity
@Table(name = "annunci_dogsitter")
public class AnnunciKijijiDogSitter extends BaseObject implements Serializable {

	private static final long serialVersionUID = -1074534378316003023L;


	public AnnunciKijijiDogSitter() {
		// TODO Auto-generated constructor stub
	}
	
	public AnnunciKijijiDogSitter(final String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }
	
	private Long idAnnuncio;
	private String nomeUtente;
	private String descrizione;
	private String telefono;
	private String luogo;
	private String tariffa;
	private double lng; 
	private double lat;
	
	private Date dataPubblicazione;
	private String dataPubblicazioneString;
	
	private String urlProfilo;
	
	private byte[] immagine1;
	private byte[] immagine3;
	private byte[] immagine2;
	
	private String nomeImmagine;
	
	private boolean attivo;


	@Transient
	public boolean infoGenerali() {
		if(nomeUtente != null && descrizione != null  && telefono != null && !telefono.equals("") && luogo != null && tariffa != null  && 
				!nomeUtente.equals("") && !descrizione.equals("")  && !telefono.equals("") && !luogo.equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	@Transient
	public boolean infoGeneraliPerRicerca_e_Sitemap_e_Immagini() {
		if(nomeUtente != null && descrizione != null  && telefono != null && !telefono.equals("") && luogo != null && tariffa != null && urlProfilo != null &&  
				!nomeUtente.equals("") && !descrizione.equals("")  && !telefono.equals("") && !luogo.equals("") && !urlProfilo.equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	@Transient
	public boolean infoGeneraliPerRicercaMappa() {
		if(nomeUtente != null && descrizione != null  && telefono != null && !telefono.equals("") && luogo != null && tariffa != null && urlProfilo != null && lat != 0 && lng != 0 &&
				!nomeUtente.equals("") && !descrizione.equals("")  && !telefono.equals("") && !luogo.equals("") && !urlProfilo.equals("")){
			return true;
		}else{
			return false;
		}
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_annuncio")
	public Long getIdAnnuncio() {
		return idAnnuncio;
	}

	public void setIdAnnuncio(Long idAnnuncio) {
		this.idAnnuncio = idAnnuncio;
	}
	
	@Column(unique = true, nullable = false)
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@Column(nullable = false)
	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	
	@Column(length = 10000, nullable = false)
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) throws UnsupportedEncodingException {
		this.descrizione = descrizione;
	}
	
	@Column(nullable = false)
	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}


	public String getTariffa() {
		return tariffa;
	}

	public void setTariffa(String tariffa) {
		this.tariffa = tariffa;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getDataPubblicazioneString() {
		return dataPubblicazioneString;
	}

	public void setDataPubblicazioneString(String dataPubblicazioneString) {
		this.dataPubblicazioneString = dataPubblicazioneString;
	}

	@Column(name = "url_profilo")
	public String getUrlProfilo() {
		return urlProfilo;
	}

	public void setUrlProfilo(String urlProfilo) {
		this.urlProfilo = urlProfilo;
	}

	
	@Lob
	@Column(columnDefinition="mediumblob")
	public byte[] getImmagine1() {
		return immagine1;
	}

	public void setImmagine1(byte[] immagine1) {
		this.immagine1 = immagine1;
	}

	@Lob
	@Column(columnDefinition="mediumblob")
	public byte[] getImmagine3() {
		return immagine3;
	}

	public void setImmagine3(byte[] immagine3) {
		this.immagine3 = immagine3;
	}

	@Lob
	@Column(columnDefinition="mediumblob")
	public byte[] getImmagine2() {
		return immagine2;
	}

	public void setImmagine2(byte[] immagine2) {
		this.immagine2 = immagine2;
	}

	public String getNomeImmagine() {
		return nomeImmagine;
	}

	public void setNomeImmagine(String nomeImmagine) {
		this.nomeImmagine = nomeImmagine;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
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
