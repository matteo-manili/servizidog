package com.dogsitter.form;

import java.io.Serializable;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
public class RuoliServizi implements Serializable {
private static final long serialVersionUID = -8838780105659989395L;

private boolean dogsitter;
private boolean dogHost;
private boolean adozione;
private boolean associazione;

public RuoliServizi() {
	// TODO Auto-generated constructor stub
}

public boolean isDogsitter() {
	return dogsitter;
}


public void setDogsitter(boolean dogsitter) {
	this.dogsitter = dogsitter;
}


public boolean isDogHost() {
	return dogHost;
}


public void setDogHost(boolean dogHost) {
	this.dogHost = dogHost;
}


public boolean isAdozione() {
	return adozione;
}


public void setAdozione(boolean adozione) {
	this.adozione = adozione;
}


public boolean isAssociazione() {
	return associazione;
}


public void setAssociazione(boolean associazione) {
	this.associazione = associazione;
}



	
	

}
