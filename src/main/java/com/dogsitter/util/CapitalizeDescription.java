package com.dogsitter.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public final class CapitalizeDescription {

	public static String LettereMaiuscoleDopoilPunto(String descrizione){
	descrizione = StringUtils.lowerCase(descrizione);
	int pos = 0;
	boolean capitalize = true;
	StringBuilder sb = new StringBuilder(descrizione);
	while (pos < sb.length()) {
	    if (sb.charAt(pos) == '.') {
	        capitalize = true;
	    } else if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
	        sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
	        capitalize = false;
	    }
	    pos++;
	}
	return descrizione = sb.toString();
	}

}
