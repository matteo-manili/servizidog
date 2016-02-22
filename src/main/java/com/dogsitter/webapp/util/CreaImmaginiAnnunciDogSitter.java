package com.dogsitter.webapp.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imgscalr.Scalr;

import com.dogsitter.Constants;
import com.dogsitter.model.AnnunciKijijiDogSitter;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 * Questa classe passandogli un indirizzo crea una parte del link adatto agli standard seo, ad esempio:
 * http://www.servizidog.it/servizidog/adozione/la-cinquina-bufalotta/via-alberto-lollio/3
 */


public final class CreaImmaginiAnnunciDogSitter {
	private static final Log log = LogFactory.getLog(CreaImmaginiAnnunciDogSitter.class);
	
	
	public static void salvaImmaginiAnnunciInFolderTmpImageAnnunci(ServletContext servletContext, List<AnnunciKijijiDogSitter> listDogSitterAnnunci) 
			throws Exception, IOException {
		
		String imagesDirectory = servletContext.getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI_ANNUNCI);
		Iterator<AnnunciKijijiDogSitter> annunci_dog_sitter_ite = listDogSitterAnnunci.iterator();

		while (annunci_dog_sitter_ite.hasNext()) {
			AnnunciKijijiDogSitter annunciKijijiDogSitter = annunci_dog_sitter_ite.next();
			String nomeImmagine = annunciKijijiDogSitter.getNomeImmagine();
			File file = new File(imagesDirectory+"/"+nomeImmagine+"-1"+".jpg");
			
			//immagine normale
			FileUtils.writeByteArrayToFile(file, annunciKijijiDogSitter.getImmagine1());
				
			//immagine piccola
			BufferedImage thumbnail = Scalr.resize(ImageIO.read( file ), 80); //grandezza thumbnail in pixel
	        String thumbnailFilename = nomeImmagine+ "-thumbnail-1.png";
	
	        File thumbnailFile = new File(imagesDirectory + "/" + thumbnailFilename);
	        ImageIO.write(thumbnail, "png", thumbnailFile);
	
			if(annunciKijijiDogSitter.getImmagine2() != null ){
				file = new File(imagesDirectory+"/"+nomeImmagine+"-2"+".jpg");
				//immagine normale
				FileUtils.writeByteArrayToFile(file, annunciKijijiDogSitter.getImmagine2());
				
				//immagine piccola
				thumbnail = Scalr.resize(ImageIO.read( file ), 80); //grandezza thumbnail in pixel
	            thumbnailFilename = nomeImmagine+ "-thumbnail-2.png";
	
	            thumbnailFile = new File(imagesDirectory + "/" + thumbnailFilename);
	            ImageIO.write(thumbnail, "png", thumbnailFile);
			}if(annunciKijijiDogSitter.getImmagine3() != null ){
				file = new File(imagesDirectory+"/"+nomeImmagine+"-3"+".jpg");
				//immagine normale
				FileUtils.writeByteArrayToFile(file, annunciKijijiDogSitter.getImmagine3());
				
				//immagine piccola
				thumbnail = Scalr.resize(ImageIO.read( file ), 80); //grandezza thumbnail in pixel
	            thumbnailFilename = nomeImmagine+ "-thumbnail-3.png";
	
	            thumbnailFile = new File(imagesDirectory + "/" + thumbnailFilename);
	            ImageIO.write(thumbnail, "png", thumbnailFile);
			}
		}
	}
	
	
}
