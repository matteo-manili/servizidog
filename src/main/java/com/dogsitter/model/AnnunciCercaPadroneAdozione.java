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
import javax.persistence.UniqueConstraint;




/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@Entity
@Table(name = "annunci_adozione", uniqueConstraints= @UniqueConstraint(columnNames={"id_titolo_cane", "id_email_utente"}))
public class AnnunciCercaPadroneAdozione extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = -2212763355942471938L;


	public AnnunciCercaPadroneAdozione() {
		// TODO Auto-generated constructor stub
	}
	
	public AnnunciCercaPadroneAdozione(final String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }
	
	private Long idAnnuncio;
	
	private String titoloCane;
	private String email;
	
	private String nomeUtente;
	private String telefono;
	private String luogo;
	private String dataPubblicazioneString;
	private String descrizione;
	
	private String nomeImmagine;
	private byte[] immagine1;
	
	private double lng; 
	private double lat;
	
	private Date dataPubblicazione;
	
	private String urlProfilo;
	
	private boolean attivo;
	
	@Transient
	public boolean infoMinime_x_salvare() {
		if(titoloCane != null && !titoloCane.equals("") && email != null && !email.equals("") &&
				nomeUtente != null && !nomeUtente.equals("") && telefono != null && !telefono.equals("") &&
						luogo != null && !luogo.equals("") && dataPubblicazioneString != null && !dataPubblicazioneString.equals("") &&
								descrizione != null && !descrizione.equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	@Transient
	public boolean infoMinime_x_sitemap_immagini_ricerca() {
		if(titoloCane != null && !titoloCane.equals("") && email != null && !email.equals("") &&
				nomeUtente != null && !nomeUtente.equals("") && telefono != null && !telefono.equals("") &&
						luogo != null && !luogo.equals("") && dataPubblicazioneString != null && !dataPubblicazioneString.equals("") &&
								descrizione != null && !descrizione.equals("") && urlProfilo != null && !urlProfilo.equals("") ){
			return true;
		}else{
			return false;
		}
	}
	
	@Transient
	public boolean infoMinime_x_mappa() {
		if(titoloCane != null && !titoloCane.equals("") && email != null && !email.equals("") &&
				nomeUtente != null && !nomeUtente.equals("") && telefono != null && !telefono.equals("") &&
						luogo != null && !luogo.equals("") && dataPubblicazioneString != null && !dataPubblicazioneString.equals("") &&
								descrizione != null && !descrizione.equals("") && urlProfilo != null && !urlProfilo.equals("") && lat != 0 && lng != 0){
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
	
	@Column(name = "id_titolo_cane", nullable = false)
	public String getTitoloCane() {
		return titoloCane;
	}

	public void setTitoloCane(String titoloCane) {
		this.titoloCane = titoloCane;
	}
	
	@Column(name = "id_email_utente", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(length = 10000, nullable = false)
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
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
