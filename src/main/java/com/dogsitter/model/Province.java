package com.dogsitter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Entity
@Table(name = "province")
public class Province extends BaseObject {

    private static final long serialVersionUID = 1L;
	private Long id;
    private String nomeProvincia;
    private Long idRegione;
    private String siglaProvincia;
    

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="nome_provincia", length=50)
	public String getNomeProvincia() {
		return nomeProvincia;
	}
	public void setNomeProvincia(String nomeProvincia) {
		this.nomeProvincia = nomeProvincia;
	}
	
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdRegione() {
		return idRegione;
	}
	public void setIdRegione(Long idRegione) {
		this.idRegione = idRegione;
	}
	
	
	
	@Column(name="sigla_provincia", length=2)
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	
	
	
	@Override
	public String toString() {
		return null;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
}
