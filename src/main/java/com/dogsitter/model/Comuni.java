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
@Table(name = "comuni")
public class Comuni extends BaseObject {

    private static final long serialVersionUID = 196130647726505457L;
	private Long id;
    private String nomeComune;
    private Long idProvincia;
    private Long idRegione;
    private String catasto;
	
    /* ESEMPIO Classe Utente
     * ........
    @OneToOne 
    protected InfoUtente info;
    */
    
    
    /* ESEMPIO (Classe InfoUtente)
    @Entity
	public class InfoUtente
	{
	@Id
	protected Long id;
	protected String info;
	protected String info2;
	 
	...
	 
	@OneToOne(mappedBy="info", optional="false");
	protected Utente utente;
	 
	...
	}
    L’annotazione @OneToOne su utente ha due aspetti interessanti: il primo è mappedBy="info" che comunica al container che il corrispondente riferimento in 
    		Utente è la variabile info, il secondo aspetto è il parametro optional che settato a false significa che non può esistere un oggetto InfoUtente 
    		senza un Utente ad esso collegato.
    */


    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name="nome_comune", length=50)
	public String getNomeComune() {
		return nomeComune;
	}
	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}
	
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}
	
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdRegione() {
		return idRegione;
	}
	public void setIdRegione(Long idRegione) {
		this.idRegione = idRegione;
	}
	
	
	@Column(name="catasto", length=50)
	public String getCatasto() {
		return catasto;
	}
	public void setCatasto(String catasto) {
		this.catasto = catasto;
	}
	
	
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
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
