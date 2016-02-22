package com.dogsitter.webapp.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import org.springframework.web.multipart.MultipartFile;

import com.dogsitter.Constants;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 * Questa classe passandogli un indirizzo crea una parte del link adatto agli standard seo, ad esempio:
 * http://www.servizidog.it/servizidog/adozione/la-cinquina-bufalotta/via-alberto-lollio/3
 */


public final class CreaImmaginiAnnunciAdozione {
	private static final Log log = LogFactory.getLog(CreaImmaginiAnnunciAdozione.class);
	
	
	public static void salvaImmaginiAnnunciInFolderTmpImageAnnunci(ServletContext servletContext, List<AnnunciCercaPadroneAdozione> listAdozioneAnnunci) 
			throws Exception, IOException {
		
		String imagesDirectory = servletContext.getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI_ANNUNCI);
		Iterator<AnnunciCercaPadroneAdozione> annunci_adozione_ite = listAdozioneAnnunci.iterator();
		
		while (annunci_adozione_ite.hasNext()) {
			AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione = annunci_adozione_ite.next();

				if(annunciCercaPadroneAdozione.getImmagine1().length < Constants.MAX_SIZE_FILE_IMAGE){
				
					byte[] immagineByte = annunciCercaPadroneAdozione.getImmagine1();
					String nomeImmagine = annunciCercaPadroneAdozione.getNomeImmagine();
					File file = new File(imagesDirectory+"/"+nomeImmagine);
					
					//immagine normale
					FileUtils.writeByteArrayToFile(file, immagineByte);
					//FileUtils.writeByteArrayToFile(new File(imagesDirectory+"/"+nomeImmagine), annunciCercaPadroneAdozione.getImmagine1());
					
					//immagine piccola
					ByteArrayInputStream bis = new ByteArrayInputStream(immagineByte);
					BufferedImage image = ImageIO.read(bis);
					
					BufferedImage resizedImage = Scalr.resize(image, 80);
					
					int tot = nomeImmagine.length();
					String nomeImmThum = nomeImmagine.substring(0, tot - 4);
					nomeImmThum = nomeImmThum+ "-thumbnail.png";
					file = new File(imagesDirectory + "/" + nomeImmThum);
					
					ImageIO.write(resizedImage, "png", file);
					
					/*
					//immagine piccola
					BufferedImage thumbnail = Scalr.resize(ImageIO.read( file ), 80); //grandezza thumbnail in pixel
					
					tot = nomeImmagine.length();
					nomeImmThum = nomeImmagine.substring(0, tot - 4);
					nomeImmThum = nomeImmThum+ "-thumbnail.png";
					thumbnailFile = new File(imagesDirectory + "/" + nomeImmThum);
					ImageIO.write(thumbnail, "png", thumbnailFile);
	                */
				}

		}
	}
	
	
}
