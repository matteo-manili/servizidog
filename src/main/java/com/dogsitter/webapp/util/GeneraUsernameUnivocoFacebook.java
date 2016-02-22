package com.dogsitter.webapp.util;


import java.util.Random;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 * Classe che genera automaticamente una username partendo da nome e cognome, se gia esiste aggunge un numero random
 * 
 */


public final class GeneraUsernameUnivocoFacebook {

	
	public static final String GeneraUsername(String nomeFacebook, String cognomeFacebook, boolean random) {

		//elimina i caratteri speciali e gli spazi
		nomeFacebook = nomeFacebook.replaceAll("[^\\p{L}\\p{Nd}]+", "");
		cognomeFacebook = cognomeFacebook.replaceAll("[^\\p{L}\\p{Nd}]+", "");
		
		//nomeFacebook = StringUtils.lowerCase(nomeFacebook);
		//cognomeFacebook = StringUtils.lowerCase(cognomeFacebook);
		
		//nomeFacebook = StringUtils.capitalize(nomeFacebook);
		//cognomeFacebook = StringUtils.capitalize(cognomeFacebook);
		
		if(random==false){
			String concatenatedName = nomeFacebook + cognomeFacebook;
			return concatenatedName;
			
		}else{
			
			Random generator = new Random();
			int num1 = 0;
		    num1 = generator.nextInt(999);
		    
		    String concatenatedName = nomeFacebook + cognomeFacebook + num1;
			return concatenatedName;
			
		}

			
			
	}
		

		
	
	
}
