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
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import com.dogsitter.util.CapitalizeDescription;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */

@Entity
@Table(name = "cane")
public class Cane extends BaseObject implements Serializable {
	private static final long serialVersionUID = -498035603964146302L;
	
	private Long id;
	private String nomeCane;
	private String sesso;
	private String carattere;
	private String storiadelcane;
	private Integer eta;
	private Date annoDinascita; // (non visualizzato calcolato automaticamente)
	private String taglia; // molto piccola, piccola, medio, grande, molto grande
	private String pelo; // raso, medio, lungo
	
	public Cane() {

	}
	
	public Cane(String nomeCane, String carattere) {
		super();
		this.nomeCane = nomeCane;
		this.carattere = carattere;
	}
	
	
    //-------------------- ADOZIONE --------------------------

    private Adozione adozione;

	@ManyToOne
	@JoinColumn(name = "id_adozione", unique = false, nullable = true)
	public Adozione getAdozione() {
		return adozione;
	}

	public void setAdozione(Adozione adozione) {
		this.adozione = adozione;
	}
	
	//-------------------- ASSOCIAZIONE --------------------------

    private Associazione associazione;

	@ManyToOne
	@JoinColumn(name = "id_associazione", unique = false, nullable = true)
	public Associazione getAssociazione() {
		return associazione;
	}

	public void setAssociazione(Associazione associazione) {
		this.associazione = associazione;
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cane")
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(name="nome_cane", length = 64)
	public String getNomeCane() {
		return nomeCane;
	}
	
	public void setNomeCane(String nomeCane) {
		nomeCane = StringUtils.strip(nomeCane);
		nomeCane = WordUtils.capitalizeFully(nomeCane);
		this.nomeCane = nomeCane;
	}
	
	@Column(length = 20)
	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	@Column(length = 300)
	public String getCarattere() {
		return carattere;
	}

	public void setCarattere(String carattere) {
		
		carattere = StringUtils.strip(carattere);
		carattere = CapitalizeDescription.LettereMaiuscoleDopoilPunto(carattere);
		this.carattere = carattere;
	}

	@Column(name="storia_del_cane", length = 300)
	public String getStoriadelcane() {
		return storiadelcane;
	}

	public void setStoriadelcane(String storiadelcane) {
		
		storiadelcane = StringUtils.strip(storiadelcane);
		storiadelcane = CapitalizeDescription.LettereMaiuscoleDopoilPunto(storiadelcane);
		this.storiadelcane = storiadelcane;
	}

	public Integer getEta() {
		return eta;
	}

	public void setEta(Integer eta) {
		this.eta = eta;
	}

	@Column(name="anno_nascita")
	public Date getAnnoDinascita() {
		return annoDinascita;
	}

	public void setAnnoDinascita(Date annoDinascita) {
		this.annoDinascita = annoDinascita;
	}

	@Column(length = 20)
	public String getTaglia() {
		return taglia;
	}

	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}

	@Column(length = 20)
	public String getPelo() {
		return pelo;
	}

	public void setPelo(String pelo) {
		this.pelo = pelo;
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
