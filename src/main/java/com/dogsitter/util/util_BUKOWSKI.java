package com.dogsitter.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public final class util_BUKOWSKI {

	public static String NormalizzaString(String stringa) {

		
		return stringa;
	}
	
	public static String NormalizzaString_2(String stringa) {

		//System.out.println(stringa);
		
		Charset utf8charset = Charset.forName("Windows-1252");
		Charset iso88591charset = Charset.forName("ISO-8859-1");
		
		//stringa = Normalizer.normalize(stringa, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		
		stringa = stringa.replace('’', '\'');
		stringa = stringa.replace('–', '-');
		stringa = stringa.replace('—', '-');
		stringa = stringa.replace('”', '\'');
		stringa = stringa.replace('“', '\'');
		stringa = stringa.replace("…", "...");
		
		/*
		stringa = stringa.replace("â", "");
		stringa = stringa.replace("Â", "");
		stringa = stringa.replace("Â", "");
		stringa = stringa.replace("€", "");
		stringa = stringa.replace("œ", "");
		*/
		//stringa = stringa.replaceAll("\\u00a0", "");
		//stringa = stringa.replaceAll("[^\\p{L}\\p{N} ]+", "\"");
		//stringa = stringa.replaceAll("\\u00E2\\u20AC\\u0153?", "\"");
		
		
		//guessEncoding(stringa.getBytes());
		
		ByteBuffer inputBuffer = java.nio.ByteBuffer.wrap(stringa.getBytes());
		
		// decode UTF-8
		CharBuffer data = utf8charset.decode(inputBuffer);
		// encode ISO-8559-1
		ByteBuffer outputBuffer = iso88591charset.encode(data);
		
		byte[] outputData = outputBuffer.array();
		stringa = new String(outputData);
	    
		//System.out.println(stringa);
		//System.out.println("");
		
		return stringa;
	}
	
	public static String guessEncoding(byte[] bytes) {
		
		System.out.println("weeeee");
	    String DEFAULT_ENCODING = "UTF-8";
	    org.mozilla.universalchardet.UniversalDetector detector =
	        new org.mozilla.universalchardet.UniversalDetector(null);
	    detector.handleData(bytes, 0, bytes.length);
	    detector.dataEnd();
	    String encoding = detector.getDetectedCharset();
	    detector.reset();
	    if (encoding == null) {
	        encoding = DEFAULT_ENCODING;
	    }
	    return encoding;
	}
	
	
	public static GeocodeResponse prendiInfo_GEO_GOOGLE(String luogo)  {
		try{
			if (luogo != null){
				if (!luogo.equals("") && !luogo.contains("~Altre zone") && !luogo.contains("=")){
					final Geocoder geocoder = new Geocoder();
					GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(luogo+", italia").setLanguage("it").getGeocoderRequest();
					GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
					return geocoderResponse;
				}
			}
		}catch(IOException ee){
			return null;
		}catch(IndexOutOfBoundsException aa){
			return null;
		}
		return null;
	}
	
}
