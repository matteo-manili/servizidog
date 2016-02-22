package com.dogsitter.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import com.dogsitter.Constants;
import com.dogsitter.dao.ImageDao;
import com.dogsitter.dao.ImmaginiDao;
import com.dogsitter.model.Image;
import com.dogsitter.model.Immagini_store;


/**
 * @author Matteo - matteo.manili@gmail.com
 *

 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SalvaImmaginiUtentiBackUp implements Job, Serializable {
	private static final long serialVersionUID = -2249507358751610872L;
	public static DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss aa", Locale.ITALIAN);
	private static final Log log = LogFactory.getLog(SalvaImmaginiUtentiBackUp.class);
	
	//DATABASE DI BACKUP
	public static ApplicationContext contextImmaginiBackUP_Dao = 
    		new ClassPathXmlApplicationContext("App-Database-Immagini-BACK_UP-Spring-Module.xml");
	public static ImmaginiDao immaginiBackUP_Dao = (ImmaginiDao) contextImmaginiBackUP_Dao.getBean("ImmaginiDao");
	
	
	//DATABASE SERVIZIDOG.IT
	public static ApplicationContext contextImagesDao = 
    		new ClassPathXmlApplicationContext("App-Database-Spring-Module.xml");
	public static ImageDao imageDao = (ImageDao) contextImagesDao.getBean("ImageDao");
	
	
	

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		String data = df.format(new Date());
		System.out.println("#######################################################################################################");
		System.out.println("############# INIZIO PROCEDURA SalvaImmaginiUtentiBackUp "+data+" ######################");
		System.out.println("#######################################################################################################");
		
		try {
			ServletContext servletContext = (ServletContext) context.getMergedJobDataMap().get("ServletContext");
			String storageDirectory = servletContext.getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI);
			Image image;
			List<Image> listImages = imageDao.list();
			Iterator<Image> iteImage = listImages.iterator();
			Immagini_store immaginiStored;
			
			while(iteImage.hasNext()){
				image = iteImage.next();
				
				File fileImageGrande = new File(storageDirectory+"/"+image.getNewFilename());
				File fileImagePicc = new File(storageDirectory+"/"+image.getThumbnailFilename());
				
				byte byteImageGrande[] = new byte[(int)fileImageGrande.length()];
				byte byteImagePiccola[] = new byte[(int)fileImagePicc.length()];
				
				try{
					FileInputStream fileGrandeInputStream = new FileInputStream(fileImageGrande);
					fileGrandeInputStream.read(byteImageGrande);
					fileGrandeInputStream.close();
								         
					FileInputStream filePiccoloInputStream = new FileInputStream(fileImagePicc);
					filePiccoloInputStream.read(byteImagePiccola);
					filePiccoloInputStream.close();
			         
					
				}catch(FileNotFoundException fnfE){
					log.debug("FileNotFoundException SalvaImmaginiBackUp.JobExecutionContext");
					fnfE.printStackTrace();
			    }catch(IOException ioe){
					log.debug("IOException SalvaImmaginiBackUp.JobExecutionContext");
					ioe.printStackTrace();
			    } catch(Exception e) {
		        	log.debug("Exception SalvaImmaginiBackUp.JobExecutionContext");
		        	e.printStackTrace();
		        }
		        
				try{
					immaginiStored = new Immagini_store();
					immaginiStored.setNewFilename(image.getNewFilename());
					immaginiStored.setThumbnailFilename(image.getThumbnailFilename());
					immaginiStored.setImageGrande(byteImageGrande);
					immaginiStored.setImagePiccola(byteImagePiccola);
					
					immaginiBackUP_Dao.saveImmagine(immaginiStored);
				
				
				}catch(DataIntegrityViolationException dupluc){
					System.out.println("......SalvaImmaginiBackUp IMMAGINE DUPLICATA..........");
				}
			} //fine while
			
			log.debug("QUARTS SalvaImmaginiUtentiBackUp [OK]");
			
			
		}catch(SecurityException se){
			System.out.println("SecurityException SalvaImmaginiBackUp.JobExecutionContext");
			log.debug("SecurityException SalvaImmaginiBackUp.JobExecutionContext");
			se.printStackTrace();
	    } catch(Exception e) {
        	System.out.println("Exception SalvaImmaginiBackUp.JobExecutionContext");
        	log.debug("Exception SalvaImmaginiBackUp.JobExecutionContext");
        	e.printStackTrace();
        }
        
	}

	
	
	
	
}
