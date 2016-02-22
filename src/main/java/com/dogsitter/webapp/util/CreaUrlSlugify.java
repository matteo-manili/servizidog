package com.dogsitter.webapp.util;

import java.io.IOException;

import com.dogsitter.model.Address;
import com.github.slugify.Slugify;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 * Questa classe passandogli un indirizzo crea una parte del link adatto agli standard seo, ad esempio:
 * http://www.servizidog.it/servizidog/adozione/la-cinquina-bufalotta/via-alberto-lollio/3
 */


public final class CreaUrlSlugify {

	public static String creaUrl(String urlBase, Address address, long id) throws IOException{
		//creo la url profilo in base all'indirizzo
		//esempio: http://www.servizidog.it/servizidog/adozione/la-cinquina-bufalotta/via-alberto-lollio/3
		Slugify slugify = new Slugify();
		String urlSlug = null;
    	urlSlug = slugify.slugify(urlBase) +"/"+
    				slugify.slugify(address.getCity()) +"/"+ 
    				slugify.slugify(address.getAddress()) +"/"+ 
    				slugify.slugify(String.valueOf(id));
		return urlSlug;
	}

	public static String creaUrlAnnunci(String urlBase, String luogo, String nome, long id) throws IOException{
		//creo la url profilo in base all'indirizzo
		//esempio: http://www.servizidog.it/servizidog/adozione/la-cinquina-bufalotta/via-alberto-lollio/3
		Slugify slugify = new Slugify();
		String urlSlug = null;
    	urlSlug = slugify.slugify(urlBase) +"/"+
    				slugify.slugify(luogo) +"/"+ 
    				slugify.slugify(nome) +"/"+ 
    				slugify.slugify(String.valueOf(id));
		return urlSlug;
	}
	
}
