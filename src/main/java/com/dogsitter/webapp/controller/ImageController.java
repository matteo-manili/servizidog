/*
 * The MIT License
 *
 * Copyright 2013 jdmr.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.dogsitter.webapp.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.dogsitter.Constants;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.DogSitter;
import com.dogsitter.model.Image;
import com.dogsitter.model.TipoRuoliServiceDog;
import com.dogsitter.model.User;
import com.dogsitter.service.CreaSitemapManager;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 *
 * @author jdmr
 */
@Controller
public class ImageController extends BaseFormController {
    
	private CreaSitemapManager creaSitemapManager;
	
	@Autowired
	public void setCreaSitemapManager(CreaSitemapManager creaSitemapManager) {
		this.creaSitemapManager = creaSitemapManager;
	}

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> list(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("sono in ImageController.list GET");

    	//originale!!!!!
        //List<Image> list = getImageManager().list();
    	List<Image> list = null;

        //matteo.....
    	String classTipoUser = request.getParameter("tipoUtente");
    	
    	User user = null;
    	
    	//TipoRuoliServiceDog tipoRuoli = null;
    	
    	
    	user = getUserManager().getUserByUsername(request.getRemoteUser());
		long idUser = user.getId();
		long idTipo = 0;
    	
    	if(classTipoUser.equals("DogSitter")){
    		idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGSITTER_ROLE).getId();
    	}
    	if(classTipoUser.equals("DogHost")){
    		idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGHOST_ROLE).getId();
    	}
    	if(classTipoUser.equals("Adozione")){
    		idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ADOZIONE_ROLE).getId();
    	}
    	if(classTipoUser.equals("Associazione")){
    		idTipo = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ASSOCIAZIONE_ROLE).getId();
    	}
    	
    	
    	list = getImageManager().listImageUserTipoServizio(idUser, idTipo);
    	
    	
    	
        for(Image image : list) {
        	image.setUrl(request.getContextPath()+"/picture/"+image.getId());
            image.setThumbnailUrl(request.getContextPath()+"/thumbnail/"+image.getId());
            image.setDeleteUrl(request.getContextPath()+"/delete/"+image.getId());
            image.setDeleteType("DELETE");
            
            //image.setImmaginePreferita(image.isImmaginePreferita());
            //proprietà non del database (@Transient)
            image.setValueImagePreferita(image.getId().toString());
        }
        Map<String, Object> files = new HashMap<>();
        files.put("files", list);

        return files;
    }
    
    
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
    	System.out.println("sono in ImageController.upload POST");
    	String classTipoUser = request.getParameter("tipoUtente");
    	User user = null;
    	TipoRuoliServiceDog tipoRuoli = null;
    	long idUser = 0;
		long idTipo = 0;
		String storageDirectory = getServletContext().getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI);
		Iterator<String> itr = null;
        MultipartFile mpf;
        List<Image> list = new LinkedList<>();

		if(classTipoUser != null){
			try{
		    	if(classTipoUser.equals("DogSitter")){
		    		user = getUserManager().getUserByUsername(request.getRemoteUser());
		    		tipoRuoli = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGSITTER_ROLE);
		    		
		    		idUser = user.getId();
		    		idTipo = tipoRuoli.getId();
		    		
		    		//aggiorno la data ultima modifica e aggiorno la sitemap.xml
		    		DogSitter dogSitterMod = getDogSitterManager().getDogSitterByUser(idUser);
		    		dogSitterMod.setUltimaModifica(new Date());
		    		getDogSitterManager().saveDogSitter(dogSitterMod);
		    		//creo la Sitemap.xml
		            //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
		    	}
		    	
		    	if(classTipoUser.equals("DogHost")){
		    		user = getUserManager().getUserByUsername(request.getRemoteUser());
		    		tipoRuoli = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGHOST_ROLE);
		    		
		    		idUser = user.getId();
		    		idTipo = tipoRuoli.getId();
		
		    		//aggiorno la data ultima modifica e aggiorno la sitemap.xml
		    		DogHost dogHostMod = getDogHostManager().getDogHostByUser(idUser);
		    		dogHostMod.setUltimaModifica(new Date());
		    		getDogHostManager().saveDogHost(dogHostMod);
		    		//creo la Sitemap.xml
		            //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
		    	}
		    	
		    	if(classTipoUser.equals("Adozione")){
		    		user = getUserManager().getUserByUsername(request.getRemoteUser());
		    		tipoRuoli = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ADOZIONE_ROLE);
		    		
		    		idUser = user.getId();
		    		idTipo = tipoRuoli.getId();
		
		    		//aggiorno la data ultima modifica e aggiorno la sitemap.xml
		    		Adozione adozioneMod = getAdozioneManager().getAdozioneByUser(idUser);
		    		adozioneMod.setUltimaModifica(new Date());
		    		getAdozioneManager().saveAdozione(adozioneMod);
		    		//creo la Sitemap.xml
		            //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
		    	}
		    	
		    	if(classTipoUser.equals("Associazione")){
		    		user = getUserManager().getUserByUsername(request.getRemoteUser());
		    		tipoRuoli = getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.ASSOCIAZIONE_ROLE);
		    		
		    		idUser = user.getId();
		    		idTipo = tipoRuoli.getId();
		
		    		//aggiorno la data ultima modifica e aggiorno la sitemap.xml
		    		Associazione associazioneMod = getAssociazioneManager().getAssociazioneByUser(idUser);
		    		associazioneMod.setUltimaModifica(new Date());
		    		getAssociazioneManager().saveAssociazione(associazioneMod);
		    		//creo la Sitemap.xml
		            //creaSitemapManager.creaSitemapProUrlProfili(getServletContext());
		    	}
		    	
		        itr = request.getFileNames();
			}catch(Exception exc){
				System.out.println("Exception 1 ImageController.upload POST");
				exc.printStackTrace();
			}
		}

        while (itr != null && itr.hasNext() ){
            mpf = request.getFile(itr.next());
            String newFilenameBase = UUID.randomUUID().toString();
            String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
            String newFilename = newFilenameBase + originalFileExtension;
            //String storageDirectory = fileUploadDirectory;
            String contentType = mpf.getContentType();
            
            File folderTmp = new File(storageDirectory); //path directory /tmpImage
	        File newFile = new File(storageDirectory + "/" + newFilename); //path directory /tmpImage e file

	        try {
	        	if (!Files.exists(folderTmp.toPath())) {
	        		System.out.println("LA CARTELLA NON ESISTE creo la directory newFile.getParentFile().mkdirs()="+ 
	        		newFile.getParentFile().mkdirs());
	        	}

                mpf.transferTo(newFile); //salvo il file originale
                
                //ridimensiono a immagine grande 
                /*
                BufferedImage thumbnailGande = Scalr.resize(ImageIO.read(newFile), 600);
                String imageBigFilename = newFilenameBase + ".png";
                
                File imageBigFile = new File(storageDirectory + "/" + imageBigFilename);
                ImageIO.write(thumbnailGande, "png", imageBigFile);
                */
                
                //ridimensiono a immagine piccola
                BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 80); //grandezza thumbnail in pixel
                String thumbnailFilename = newFilenameBase + "-thumbnail.png";

                File thumbnailFile = new File(storageDirectory + "/" + thumbnailFilename);
                ImageIO.write(thumbnail, "png", thumbnailFile);
                
                //elimino il file originale
                //newFile.delete();
                
                Image image = new Image();
                
                /*
                image.setName(mpf.getOriginalFilename());
                image.setNewFilename(imageBigFilename);
                image.setThumbnailFilename(thumbnailFilename);
                */
                
                image.setName(mpf.getOriginalFilename());
                image.setNewFilename(newFilename);
                image.setThumbnailFilename(thumbnailFilename);
                
                image.setContentType(contentType);
                image.setSize(mpf.getSize());
                image.setThumbnailSize(thumbnailFile.length());
                image.setUser(user);
                image.setTipoRuoli(tipoRuoli);

                //se non ce nessuna immagine preferita settare quella con l'Id piu basso come immagine preferita!
                if(getImageManager().controllaImmaginePreferitaExist(idUser, idTipo)){
                	image.setImmaginePreferita(true);
                }
                
                image = getImageManager().create(image); // SALVO L'IMMAGINE NEL DATABASE
                
                //proprietà non del database (@Transient)
                image.setCheckBoxId(image.getId().toString());
                image.setValueImagePreferita(image.getId().toString());
                image.setUrl(request.getContextPath()+"/picture/"+image.getId());
                image.setThumbnailUrl(request.getContextPath()+"/thumbnail/"+image.getId());
                image.setDeleteUrl(request.getContextPath()+"/delete/"+image.getId());
                image.setDeleteType("DELETE");

                list.add(image);
                
                if(request.getParameter("imagePreferita") != null){
                	long idImage = Long.parseLong(request.getParameter("imagePreferita"));
        	        getImageManager().impostaImmaginePreferita(idImage, idUser, idTipo);
                }

            }catch(IOException ioe) {
            	System.out.println("IOException ImageController.upload POST");
            	ioe.printStackTrace();
            }
            catch(Exception e) {
            	System.out.println("Exception 2 ImageController.upload POST");
            	e.printStackTrace();
            	newFile.delete();
            }
        } //fine while
        
        if(list!=null){
	        Map<String, Object> files = new HashMap<>();
	        files.put("files", list);
	        return files;
        }else{
        	return null;
        }
        
        
    }
    
    
    
    @RequestMapping(value = "/impostaImmaginePreferitaButton", method = RequestMethod.GET)
    public @ResponseBody String impostaImmPreferitaButton(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("sono in ImageController.impostaImmPreferitaButton GET");
    	try {
    		if (!request.getParameter("id").equals("undefined")){
	    		long idImage = Long.parseLong(request.getParameter("id"));
		        Image image = getImageManager().get(idImage);
		        getImageManager().impostaImmaginePreferitaButton(image);
		        return image.getName();
    		}else{
    			return "";
    		}
    	} catch(Exception e) {
    		System.out.println("Exception in ImageController.impostaImmPreferitaButton");
            e.printStackTrace();
            return "";
        }
    }
    
    
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody List<Map<String, Object>> delete(@PathVariable Long id) {
    	try {
    		String fileUploadDirectory = getServletContext().getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI);
    	    String fileImagesDirectory = getServletContext().getRealPath(Constants.IMAGES_DIRECTORY);
    		
	        Image image = getImageManager().get(id);
	        getImageManager().impostaPreferitaPrimaDiCancellare(image);
	        
	        File imageFile = new File(fileUploadDirectory+"/"+image.getNewFilename());
	        imageFile.delete();
	        File thumbnailFile = new File(fileUploadDirectory+"/"+image.getThumbnailFilename());
	        thumbnailFile.delete();
	        
	        getImageManager().delete(image);
	        List<Map<String, Object>> results = new ArrayList<>();
	        Map<String, Object> success = new HashMap<>();
	        success.put("success", true);
	        results.add(success);
	        return results;
	        
    	} catch(Exception e) {
    		System.out.println("Exception in ImageController.delete");
            e.printStackTrace();
            
            List<Map<String, Object>> results = new ArrayList<>();
            Map<String, Object> success = new HashMap<>();
            success.put("success", false);
            results.add(success);
            return results;
            
        }
    }
    

    
	@RequestMapping(value = "/thumbnail/{id}", method = RequestMethod.GET)
    public void thumbnail(HttpServletResponse response, HttpServletRequest request, @PathVariable Long id) {
		String fileUploadDirectory = getServletContext().getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI);
	    String fileImagesDirectory = getServletContext().getRealPath(Constants.IMAGES_DIRECTORY);
	    try {
	        Image image = getImageManager().get(id);
	        File imageFile = new File(fileUploadDirectory+"/"+image.getThumbnailFilename());
	        response.setContentType(image.getContentType());
	        response.setContentLength(image.getThumbnailSize().intValue());
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
            is.close(); //chiudo lo stram altrimenti i file rimangono appesi
            
        }catch(FileNotFoundException fne){
        	System.out.println("FileNotFoundException ImageController.thumbnail");
        	
	        InputStream inStream = null;
    	    File afile = new File(fileImagesDirectory+"/logo-thumbnail.png");
    	    try {
    	    	response.setContentType("image/png");
    	        response.setContentLength(1784);
    	    	
				inStream = new FileInputStream(afile);
	    	    //outStream = new FileOutputStream(bfile);
				IOUtils.copy(inStream, response.getOutputStream());
	    	    inStream.close();
    	    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
    	    	System.out.println("FileNotFoundException 2 ImageController.thumbnail");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("FileNotFoundException IOException ImageController.thumbnail");
				e.printStackTrace();
			}
        }
        catch(IOException ioe) {
        	System.out.println("IOException 1 ImageController.thumbnail");
        	ioe.printStackTrace();
        }
        catch(NullPointerException nullPont) {
        	System.out.println("NullPointerException 1 ImageController.thumbnail");
        	//nullPont.printStackTrace();
        }
        catch(Exception e) {
        	System.out.println("Exception 1 ImageController.thumbnail");
        	InputStream inStream = null;
    	    File afile = new File(fileImagesDirectory+"/logo-thumbnail.png");
    	    try {
    	    	response.setContentType("image/png");
    	        response.setContentLength(1784);
    	    	
				inStream = new FileInputStream(afile);
	    	    //outStream = new FileOutputStream(bfile);
				IOUtils.copy(inStream, response.getOutputStream());
	    	    inStream.close();
    	    } catch (FileNotFoundException ecc) {
				// TODO Auto-generated catch block
    	    	System.out.println("Exception FileNotFoundException ImageController.thumbnail");
				ecc.printStackTrace();
			} catch (IOException ess) {
				// TODO Auto-generated catch block
				System.out.println("Exception IOException ImageController.thumbnail");
				ess.printStackTrace();
			}
        }
    }
    
	
	@RequestMapping(value = "/thumbnailAnnuncioDogSitter/{id}", method = RequestMethod.GET)
    public void thumbnailAnnuncioDogSitter(HttpServletResponse response, HttpServletRequest request, @PathVariable Long id) {
		String fileUploadDirectory = getServletContext().getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI_ANNUNCI);
	    String fileImagesDirectory = getServletContext().getRealPath(Constants.IMAGES_DIRECTORY);
	    try {
	        AnnunciKijijiDogSitter imageAnnuncio = getAnnunciKijijiDogSitterManager().get(id);
	        
	        String thumbnailFilename = imageAnnuncio.getNomeImmagine()+ "-thumbnail-1.png";
	        
	        File imageFile = new File(fileUploadDirectory+"/"+imageAnnuncio.getNomeImmagine()+ "-thumbnail-1.png");
	        response.setContentType("image/png");
	        //response.setContentLength(image.getThumbnailSize().intValue());
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
            is.close(); //chiudo lo stram altrimenti i file rimangono appesi
            
        }catch(FileNotFoundException fne){
        	System.out.println("Exception ImageController.thumbnailAnnuncioDogSitter");
        	System.out.println("id immagine annuncio= "+id);
        	
	        InputStream inStream = null;
    	    File afile = new File(fileImagesDirectory+"/logo-thumbnail.png");
    	    try {
    	    	response.setContentType("image/png");
    	        response.setContentLength(1784);
				inStream = new FileInputStream(afile);
				IOUtils.copy(inStream, response.getOutputStream());
	    	    inStream.close();
    	    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
    	    	System.out.println("FileNotFoundException 2 ImageController.thumbnailAnnuncioDogSitter");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("IOException 2 ImageController.thumbnailAnnuncioDogSitter");
				e.printStackTrace();
			}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	@RequestMapping(value = "/immagineGrandeAnnuncioDogSitter/{numImage}/{id}", method = RequestMethod.GET)
    public void immagineGrandeAnnuncioDogSitter(HttpServletResponse response, HttpServletRequest request, @PathVariable int numImage, @PathVariable Long id) {
		String fileUploadDirectory = getServletContext().getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI_ANNUNCI);
	    String fileImagesDirectory = getServletContext().getRealPath(Constants.IMAGES_DIRECTORY);
	    
	    try {
	        AnnunciKijijiDogSitter imageAnnuncio = getAnnunciKijijiDogSitterManager().get(id);
	        File imageFile = new File(fileUploadDirectory+"/"+imageAnnuncio.getNomeImmagine()+ "-"+numImage+".jpg");
	        response.setContentType("image/jpg");
	        //response.setContentLength(image.getThumbnailSize().intValue());
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
            is.close(); //chiudo lo stram altrimenti i file rimangono appesi
            
        }catch(FileNotFoundException fne){
        	System.out.println("Exception ImageController.immagineGrandeAnnuncioDogSitter");
        	
	        InputStream inStream = null;
    	    File afile = new File(fileImagesDirectory+"/logo-thumbnail.png");
    	    try {
    	    	response.setContentType("image/png");
    	        response.setContentLength(1784);
				inStream = new FileInputStream(afile);
				IOUtils.copy(inStream, response.getOutputStream());
	    	    inStream.close();
    	    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
    	    	System.out.println("FileNotFoundException 2 ImageController.thumbnail");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("IOException 2 ImageController.thumbnail");
				e.printStackTrace();
			}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	@RequestMapping(value = "/immagineGrandeAnnuncioAdozione/{id}", method = RequestMethod.GET)
    public void immagineGrandeAnnuncioAdozione(HttpServletResponse response, HttpServletRequest request, @PathVariable Long id) {
		String fileUploadDirectory = getServletContext().getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI_ANNUNCI);
	    String fileImagesDirectory = getServletContext().getRealPath(Constants.IMAGES_DIRECTORY);
	    
	    try {
	        AnnunciCercaPadroneAdozione imageAnnuncioAdozione = getAnnunciCercaPadroneAdozioneManager().get(id);
	        
	        String nomeImmagine = imageAnnuncioAdozione.getNomeImmagine();
	        
	    	int tot = nomeImmagine.length();
			String formatoImmagine = nomeImmagine.substring(tot-3, tot);
	        
	        File imageFile = new File(fileUploadDirectory+"/"+nomeImmagine );
	        response.setContentType("image/"+formatoImmagine);

            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
            is.close(); //chiudo lo stram altrimenti i file rimangono appesi
            
        }catch(FileNotFoundException fne){
        	System.out.println("Exception ImageController.immagineGrandeAnnuncioDogSitter");
        	
	        InputStream inStream = null;
    	    File afile = new File(fileImagesDirectory+"/logo-thumbnail.png");
    	    try {
    	    	response.setContentType("image/png");
    	        response.setContentLength(1784);
				inStream = new FileInputStream(afile);
				IOUtils.copy(inStream, response.getOutputStream());
	    	    inStream.close();
    	    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
    	    	System.out.println("FileNotFoundException 2 ImageController.thumbnail");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("IOException 2 ImageController.thumbnail");
				e.printStackTrace();
			}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/thumbnailAnnuncioAdozione/{id}", method = RequestMethod.GET)
    public void thumbnailAnnuncioAdozione(HttpServletResponse response, HttpServletRequest request, @PathVariable Long id) {
		String fileUploadDirectory = getServletContext().getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI_ANNUNCI);
	    String fileImagesDirectory = getServletContext().getRealPath(Constants.IMAGES_DIRECTORY);
	    try {
	    	AnnunciCercaPadroneAdozione imageAnnuncioAdozione = getAnnunciCercaPadroneAdozioneManager().get(id);
	    	String nomeImmagine = imageAnnuncioAdozione.getNomeImmagine();
	    	int tot = nomeImmagine.length();
			String nomeImmThum = nomeImmagine.substring(0, tot-4);
			nomeImmThum = nomeImmThum+ "-thumbnail.png";
			
	        File imageFile = new File(fileUploadDirectory+"/"+nomeImmThum);
	        response.setContentType("image/png");

            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
            is.close(); //chiudo lo stram altrimenti i file rimangono appesi
            
        }catch(FileNotFoundException fne){
        	System.out.println("Exception ImageController.thumbnailAnnuncioAdozione");
        	System.out.println("id immagine annuncio= "+id);
        	
	        InputStream inStream = null;
    	    File afile = new File(fileImagesDirectory+"/logo-thumbnail.png");
    	    try {
    	    	response.setContentType("image/png");
    	        response.setContentLength(1784);
				inStream = new FileInputStream(afile);
				IOUtils.copy(inStream, response.getOutputStream());
	    	    inStream.close();
    	    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
    	    	System.out.println("FileNotFoundException 2 ImageController.thumbnailAnnuncioAdozione");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("IOException 2 ImageController.thumbnailAnnuncioAdozione");
				e.printStackTrace();
			}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    
    @RequestMapping(value = "/picture/{id}", method = RequestMethod.GET)
    public void picture(HttpServletResponse response, @PathVariable Long id) {
    	System.out.println("sono in ImageController ImageController.picture");
    	String fileUploadDirectory = getServletContext().getRealPath(Constants.STORAGE_DIRECTORY_IMMAGINI);
	    String fileImagesDirectory = getServletContext().getRealPath(Constants.IMAGES_DIRECTORY);
    	
        Image image = getImageManager().get(id);
        File imageFile = new File(fileUploadDirectory+"/"+image.getNewFilename());
        response.setContentType(image.getContentType());
        response.setContentLength(image.getSize().intValue());
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
            is.close(); //chiudo lo stram altrimenti i file rimangono appesi
        }catch(FileNotFoundException fne){
        	System.out.println("sono in FileNotFoundException ImageController.picture");
	        InputStream inStream = null;
    	    File afile = new File(fileImagesDirectory+"/logo-thumbnail.png");
    	    try {
    	    	response.setContentType("image/png");
    	        response.setContentLength(1784);
    	    	
				inStream = new FileInputStream(afile);
	    	    //outStream = new FileOutputStream(bfile);
				IOUtils.copy(inStream, response.getOutputStream());
	    	    inStream.close();
    	    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        catch(IOException ioe) {
        	System.out.println("sono in IOException ImageController.picture");
        	ioe.printStackTrace();
        }
        catch(Exception e) {
        	System.out.println("sono in Exception ImageController.picture");
            e.printStackTrace();
        }
    }
    

}
