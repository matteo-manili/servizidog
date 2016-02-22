package com.dogsitter;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.dao.GestioneApplicazioneDao;
import com.dogsitter.dao.UserDao;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.model.GestioneApplicazione;
import com.dogsitter.util.util_BUKOWSKI;
import com.dogsitter.webapp.util.Attiva_Disattiva_Annunci;
import com.dogsitter.webapp.util.Avvio_CaricaImmagini;
import com.dogsitter.webapp.util.CreaImmaginiAnnunciDogSitter;
import com.dogsitter.webapp.util.CreaUrlSlugify;
import com.dogsitter.webapp.util.OrdinaCoordinateCerchio_Annunci;
import com.google.code.geocoder.model.GeocodeResponse;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 * La classe è schedulata per avviarsi ogni 4 ore dal momento dello start dell'applicaton server. 
 * Raccoglie gli annunci dal sito www.kijiji.it e li memorizza.
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CatturaAnnuncioKijiji implements Job {
	private static final Log log = LogFactory.getLog(CatturaAnnuncioKijiji.class);

	public static ApplicationContext contextDao = 
    		new ClassPathXmlApplicationContext("App-Database-Spring-Module.xml");
	
	public static AnnunciKijijiDogSitterDao annunciDao = (AnnunciKijijiDogSitterDao) contextDao.getBean("AnnunciKijijiDogSitterDao");
	public static UserDao userDao = (UserDao) contextDao.getBean("userDao");
	public static GestioneApplicazioneDao gestioneAppDao = (GestioneApplicazioneDao) contextDao.getBean("GestioneApplicazioneDao");
	
	public static long TOTALE_ANNUNCI = 0;
	public static int TOTALE_ANNUNCI_AGGIUNTI = 0;
	public static DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss aa", Locale.ITALIAN);

	public static ArrayList<Long> annunciSalvati_e_aggiornati = new ArrayList<Long>();
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		System.out.println("QUARTS JobExecutionContext.... CatturaAnnuncioKijiji");
		log.debug("QUARTS JobExecutionContext.... CatturaAnnuncioKijiji");
		try{

			TOTALE_ANNUNCI = annunciDao.getAnnunciKijijiDogSitter_COUNT();
			
			INIZIO_PRO();

			GestioneApplicazione ATTIVA_CATTURA_DOGSITTER = gestioneAppDao.getGestioneApplicazioneName("ATTIVA_CATTURA_DOGSITTER");
			
			if (ATTIVA_CATTURA_DOGSITTER.isAttivo() == true){
				
				//#################################################################
				//#################################################################
				
				inizio( ATTIVA_CATTURA_DOGSITTER.getPaginaInizio(), ATTIVA_CATTURA_DOGSITTER.getPaginaFine() );
				AGGIUSTA_ANNUNCI_CoordinateANDsetUrlANDsetTariffa();
				
				//ATTIVO E DISATTIVO GLI ANNUNCI IN BASE A UNA DURATA DI TEMPO
				Attiva_Disattiva_Annunci.dogSitter(annunciDao, gestioneAppDao);
				
				//POSIZIONO GLI ANNUNCI CHE HANNO LO STESSO LUOGO IN MANIERA CIRCOLARE SULLA MAPPA
				OrdinaCoordinateCerchio_Annunci.inizio("DOGSITTER");
				
				
				//Creo immaginiAnnunci nella cartella images
				log.debug("quartz CreaImmaginiAnnunciDogSitter.salvaImmaginiInFolderImages...");
				ServletContext servletContext = (ServletContext) context.getMergedJobDataMap().get("ServletContext");
				Avvio_CaricaImmagini caricaImmaginiAnnunciAdozione = new Avvio_CaricaImmagini();
				caricaImmaginiAnnunciAdozione.caricaImmaginiAnnunci_salvati_e_aggiornati_DogSitter(annunciDao, annunciSalvati_e_aggiornati, servletContext);
				log.debug("CaricaImmagini Annunci DogSitter [OK]");
				
				System. gc();
				
				//#################################################################
				//#################################################################
				
			}else{
				System.out.println("ATTIVA_CATTURA_DOGSITTER="+ ATTIVA_CATTURA_DOGSITTER.isAttivo());
			}
			FINE_PRO();
			
			
		}catch(Exception ee){
			System.out.println("ERRORE IN CatturaAnnuncioKijiji.execute ");
			log.debug("ERRORE IN CatturaAnnuncioKijiji.execute ");
			ee.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		//ELIMINA ANNUCIO
		//annunciDao.eliminaAnnuncioDogSitter( annunciDao.get((long) 1339) );
		//Amante degli Animali offre servizio di dog sitter
		//annucio eliminato 1339
		//ANNUNCI ATTUALI= 524
				
		TOTALE_ANNUNCI = annunciDao.getAnnunciKijijiDogSitter_COUNT();
		
		INIZIO_PRO();
		
		GestioneApplicazione ATTIVA_CATTURA_DOGSITTER = gestioneAppDao.getGestioneApplicazioneName("ATTIVA_CATTURA_DOGSITTER");
		
		//if (ATTIVA_CATTURA_DOGSITTER.isAttivo() == true){
			
			inizio( 3,1 ); //43
			//prendiLinksRicerca("");
			//prendiInfoPagina("");
			AGGIUSTA_ANNUNCI_CoordinateANDsetUrlANDsetTariffa();
			
			Attiva_Disattiva_Annunci.dogSitter(annunciDao, gestioneAppDao);
			
			OrdinaCoordinateCerchio_Annunci.inizio("DOGSITTER");
			
			//PROVE_PORCO_DIO();
			
		//}
		
		FINE_PRO();
    }
	

	public static void PROVE_PORCO_DIO() {
		try {
			DateFormat dfStr = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
			String dataString;
	
			long annunci_TOTALI = annunciDao.getAnnunciKijijiDogSitter_TOTALE_COUNT();
			System.out.println("annunci_TOTALI= "+annunci_TOTALI);
			
			long annunciAttivi_COUNT = annunciDao.getAnnunciKijijiDogSitter_COUNT();
			System.out.println("annunciAttivi_COUNT= "+annunciAttivi_COUNT);
			//----------------
			
			/*
			int restult = annunciDao.getAnnunciKijijiDogSitter_SET_NOME_IMMAGINE_NULL();
			System.out.println("immagini messse  a null ="+restult);
			*/
			
			/*
			System.out.println("setto le date a null........");
			List<AnnunciKijijiDogSitter> listAnnunciNull = annunciDao.getAnnunciKijijiDogSitter_DATE_NULL();
			System.out.println("listAnnunciFuturi.size= "+listAnnunciNull.size());
			Iterator<AnnunciKijijiDogSitter> null_annunci_ite =  listAnnunciNull.iterator();
			while(null_annunci_ite.hasNext()){
				AnnunciKijijiDogSitter annuncio = null_annunci_ite.next();
				
				AnnunciKijijiDogSitter modificaAnnuncio = annunciDao.get(annuncio.getIdAnnuncio());
				modificaAnnuncio.setDataPubblicazione(null);
				annunciDao.saveAnnuncioDogSitter(modificaAnnuncio);
			}
			*/
			
			//----------------
			/*
			System.out.println("predo annunci con data futura........");
			Calendar c = Calendar.getInstance();
			Date inizio = c.getTime();
			
			List<AnnunciKijijiDogSitter> listAnnunciFuturi = annunciDao.getAnnunciKijijiDogSitter_DATE_FUTURE(inizio);
			System.out.println("listAnnunciFuturi.size= "+listAnnunciFuturi.size());
			Iterator<AnnunciKijijiDogSitter> futuri_annunci_ite =  listAnnunciFuturi.iterator();
			while(futuri_annunci_ite.hasNext()){
				AnnunciKijijiDogSitter annuncio = futuri_annunci_ite.next();
				
				
				AnnunciKijijiDogSitter modificaAnnuncio = annunciDao.get(annuncio.getIdAnnuncio());
				modificaAnnuncio.setDataPubblicazione(null);
				
				
				annunciDao.saveAnnuncioDogSitterNoFlush(modificaAnnuncio);
				
			
			}
			*/
			
			
			//------------------------
			/*
			System.out.println("prendo date string e salvo la data pubblicazione........");
			Iterator<AnnunciCercaPadroneAdozione> aggiusta_annunci_ite =  annunciDao.getAnnunciCercaPadroneAdozione_AGGIUSTA_DATA_PUBBLICAZIONE().iterator();
			while(aggiusta_annunci_ite.hasNext()){
			AnnunciCercaPadroneAdozione annuncio = aggiusta_annunci_ite.next();
			
				dataString = annuncio.getDataPubblicazioneString(); //"January 2, 2010";
				DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ITALIAN);
				Date date = format.parse(dataString);
				
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				date = c.getTime();
				
				AnnunciCercaPadroneAdozione modificaAnnuncio = annunciDao.get(annuncio.getIdAnnuncio());
				modificaAnnuncio.setDataPubblicazione(date);
				annunciDao.saveAnnuncioAdozione(modificaAnnuncio);
				//System.out.println(".....setDataPubblicazione() salvato.... "+date);
			}
			System.out.println(".............   fineeeeeeeeee [OK]");
			 */
			
			
			
			
		
		
		}catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			System.out.println("Exception PROVE_PORCO_DIO");
		}
		
	}
	
	
	
	
	public static void inizio(int PAGINE_INIZIO, int PAGINE_FINE){
		
		int NUMERO_PAGINE_INIZIO = PAGINE_INIZIO; //originale 3
		int NUMERO_PAGINE_FINE = PAGINE_FINE; 
		
		String urlLinkRicerca = "http://www.kijiji.it/"+Constants.DOG_SITTER+"/";
		for ( ; NUMERO_PAGINE_INIZIO >= NUMERO_PAGINE_FINE; NUMERO_PAGINE_INIZIO--){ // could have used i, doesn't matter.
			String si_ParamPage="?p="+NUMERO_PAGINE_INIZIO+"&entryPoint=sb"; String no_ParamPage="?entryPoint=sb";
			if (NUMERO_PAGINE_INIZIO == 1){
				System.out.println("############ PAGINA "+urlLinkRicerca+no_ParamPage+" ##############");
				prendiLinksRicerca(urlLinkRicerca+no_ParamPage);
			}else{
				System.out.println("############ PAGINA "+urlLinkRicerca+si_ParamPage+" ##############");
				prendiLinksRicerca(urlLinkRicerca+si_ParamPage);
			}
	    }
		
		
		NUMERO_PAGINE_INIZIO = 1; //originale 1 totale 15
		urlLinkRicerca = "http://www.kijiji.it/"+Constants.DOGSITTER+"/";
		for ( ; NUMERO_PAGINE_INIZIO >= 1; NUMERO_PAGINE_INIZIO--){ // could have used i, doesn't matter.
			String si_ParamPage="?p="+NUMERO_PAGINE_INIZIO+"&entryPoint=sb"; String no_ParamPage="?entryPoint=sb";
			if (NUMERO_PAGINE_INIZIO == 1){
				System.out.println("############ PAGINA "+urlLinkRicerca+no_ParamPage+" ##############");
				prendiLinksRicerca(urlLinkRicerca+no_ParamPage);
			}else{
				System.out.println("############ PAGINA "+urlLinkRicerca+si_ParamPage+" ##############");
				prendiLinksRicerca(urlLinkRicerca+si_ParamPage);
			}
	    }

	}
	
	public static void AGGIUSTA_ANNUNCI_CoordinateANDsetUrlANDsetTariffa(){
		
		AnnunciKijijiDogSitter annunciKijijiDogSitter = null;
		AnnunciKijijiDogSitter annuncioModifica = null;
		
		String luogo = "";
		String dataString = "";
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		Iterator<AnnunciKijijiDogSitter> annunci_dog_sitter_ite = annunciDao.getAnnunciKijijiDogSitter_AGGIUSTA_ANNUNCI().iterator();
		while (annunci_dog_sitter_ite.hasNext()) {
			annunciKijijiDogSitter = annunci_dog_sitter_ite.next();
				
			annuncioModifica = new AnnunciKijijiDogSitter();
			annuncioModifica = annunciDao.get(annunciKijijiDogSitter.getIdAnnuncio());
			
			//Faccio maiuscolo il nome utente
			String nomeUtente = annunciKijijiDogSitter.getNomeUtente();
			nomeUtente = StringUtils.lowerCase(nomeUtente);
			nomeUtente = WordUtils.capitalize(nomeUtente);
			annuncioModifica.setNomeUtente(nomeUtente); 
			
			if (annunciKijijiDogSitter.getDataPubblicazione() == null ){
				try {
					dataString = annunciKijijiDogSitter.getDataPubblicazioneString(); //"January 2, 2010";
					
					Date date;
					if (dataString.contains("Oggi")){
						Calendar c = Calendar.getInstance();
						date = c.getTime();
						
						annuncioModifica.setDataPubblicazione(date);
						//System.out.println(".....setDataPubblicazione() salvato.... "+date);
					}else if (dataString.contains("Ieri")){
						Calendar c = Calendar.getInstance();
						c.add(Calendar.DATE , -1);
						date = c.getTime();
						
						annuncioModifica.setDataPubblicazione(date);
						//System.out.println(".....setDataPubblicazione() salvato.... "+date);
					}else{
						DateFormat format = new SimpleDateFormat("d MMMM, hh:mm", Locale.ITALIAN);
						date = format.parse(dataString);
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						c.set(Calendar.YEAR, year);
						
						if ( c.getTime().after(Calendar.getInstance().getTime()) ){
							
							c.add(Calendar.YEAR, -1);
							//System.out.println("ci vado");
						}
						
						date = c.getTime();

						annuncioModifica.setDataPubblicazione(date);
						//System.out.println(".....setDataPubblicazione() salvato.... "+date);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (annunciKijijiDogSitter.getTariffa() == null || annunciKijijiDogSitter.getTariffa().equals("") || annunciKijijiDogSitter.getTariffa().equals("0")){
				annuncioModifica.setTariffa(Constants.PREZZO_MEDIO_DOGSITTER);
				//System.out.println(".....setUrlTariffa() salvato....");
			}
			
			String city = null;
			if (annunciKijijiDogSitter.getLat() == 0 && annunciKijijiDogSitter.getLng() == 0){
				luogo = annunciKijijiDogSitter.getLuogo();
				luogo = StringUtils.replace(luogo, "Altro", "");
				//System.out.println("luogo= "+ luogo);
				try{
					GeocodeResponse geocoderResponse = util_BUKOWSKI.prendiInfo_GEO_GOOGLE(luogo);
					if (geocoderResponse != null){
						//List<GeocoderAddressComponent> addressComponents = geocoderResponse.getResults().get(0).getAddressComponents();
						//System.out.println(geocoderResponse.getResults().get(0).getAddressComponents());
						List<String> types = geocoderResponse.getResults().get(0).getAddressComponents().get(0).getTypes();
						for (String type : types) {
							if ("locality".equals(type) ) {
								city = geocoderResponse.getResults().get(0).getAddressComponents().get(0).getLongName();
								//System.out.println(city);
							}
						}

						annuncioModifica.setLuogo(geocoderResponse.getResults().get(0).getFormattedAddress());
						annuncioModifica.setLat( geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat().doubleValue() );
						annuncioModifica.setLng( geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng().doubleValue() );

						//System.out.println(".....salvate coordinate....");
					}
				
				}catch(IndexOutOfBoundsException dd){
					//dd.printStackTrace();
					//System.out.println("IndexOutOfBoundsException coordinate google");
				}
			}
			
			if (annunciKijijiDogSitter.getUrlProfilo() == null || annunciKijijiDogSitter.getUrlProfilo().equals("")){
				try {

					String cityUrl = "";
					if (city != null){
						cityUrl = city;
					}else{
						cityUrl = annuncioModifica.getLuogo();
					}
					annuncioModifica.setUrlProfilo( CreaUrlSlugify.creaUrlAnnunci("dogsitter-annunci", cityUrl, annuncioModifica.getNomeUtente(), 
							annuncioModifica.getIdAnnuncio()));
					
				} catch (IOException e) {
					System.out.println("IOException setUrlProfilo()");
				}
			}
			
			AnnunciKijijiDogSitter annSalvato = annunciDao.saveAnnuncioDogSitterNoFlush(annuncioModifica);
			//System.out.println("...annungio aggiustato= "+annSalvato.getIdAnnuncio());
		} // fine while
		
	}
	
    public static void prendiLinksRicerca(String urlPaginaLinks){
		URL  url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;
	    try {
	    	//url = new URL("http://www.kijiji.it/dog+sitter/?entryPoint=sb");
	    	url = new URL(urlPaginaLinks);
	    	
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String fileHtml = "";
	        while ((line = br.readLine()) != null) {
	        	fileHtml = fileHtml + line;
	        }

	        prendiLinkAnnunci(fileHtml);
	        
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
	    }
	}

	public static void prendiLinkAnnunci(String HTMLSTring){
		ArrayList<String> arrLinks = new ArrayList<String>();
		Document html = Jsoup.parse(HTMLSTring);
		Elements links = html.select("a"); // a with href
		
		//System.out.println(links.get(20));

		Iterator<Element> aa = links.iterator();
		String linkStr = "";
		
		for(int i=0 ; aa.hasNext() ; i++){
			//String linkStr = link.toString();
			linkStr = aa.next().toString();
			
			int tot = linkStr.length();
			int posHref = linkStr.indexOf("href");
			posHref= posHref + 6;
			linkStr = linkStr.substring(posHref, tot);
			
			int posPrimaVirgoletta = linkStr.toString().indexOf('"');
			linkStr = linkStr.substring(0, posPrimaVirgoletta);
			
			if(linkStr.contains("http") ){
				if (linkStr.contains(Constants.DOG_SITTER)  
						|| linkStr.contains(Constants.DOGSITTER) 
						|| linkStr.contains(Constants.PET_SITTER) 
						|| linkStr.contains(Constants.PETSITTER) 
						|| (linkStr.contains(Constants.DOG ) && linkStr.contains(Constants.SITTER))
						|| (linkStr.contains(Constants.PET ) && linkStr.contains(Constants.SITTER))
				){
					//System.out.println("num= "+ i + linkStr);
					arrLinks.add(linkStr);
					//prendiInfoPagina(linkStr);
				}
			}
		}
		
		//System.out.println("arrLinks size= "+arrLinks.size());
		Collections.reverse(arrLinks);
		Iterator<String> iteAnnunci = arrLinks.iterator();
		
		while (iteAnnunci.hasNext()){
			prendiInfoPagina(iteAnnunci.next());
		}
	}
	
	public static void prendiInfoPagina(String urlString){
		URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;
	    try {
	    	
	    	//String paramValue = "/dog+sitter/?p=2&amp;entryPoint=sb";
	    	//url = new java.net.URL(yourURLStr);
	    	
	    	/* SI TELEONO  */  //url = new URL("http://www.kijiji.it/annunci/cerco-lavoro-servizi/milano-annunci-vimodrone/dog-sitter/90039347");
	        /* NO TELEONO  */  //url = new URL("http://www.kijiji.it/annunci/cerco-lavoro-servizi/alessandria-annunci-novi-ligure/dog-sitter/89887264");
	    	//System.out.println("aaaa");
	    	//urlString = ("http://www.kijiji.it/annunci/cerco-lavoro-servizi/varese-annunci-busto-arsizio/dog-sitter-pensione-per-cani-e-gatti-pet-sitter/87833848");
	    	
	    	//System.out.println(urlString);
	    	
	    	url = new URL(urlString);
	    	
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String fileHtml = "";
	        while ((line = br.readLine()) != null) {
	        	fileHtml = fileHtml + line;
	        }
	        //System.out.println(fileHtml);
	        smistamento(fileHtml);
	        
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
	    }
	}
	
	//QUI DEVE ENTRARE L'URL DI UNA PAGINA - ANNUNCIO
	public static void smistamento(String HTMLSTring){
		
		AnnunciKijijiDogSitter anncuncioKiji = new AnnunciKijijiDogSitter();
		Document html = Jsoup.parse(HTMLSTring);
		String nomeImmagineFileName = UUID.randomUUID().toString();
		
		try{
			if (html.select("div.value").size() == 3){
				
				anncuncioKiji.setNomeUtente(
						prendiNomeUtente(HTMLSTring, 0));
				
				String desc = prendiDescrizione(HTMLSTring);
				anncuncioKiji.setDescrizione(desc);
				
				anncuncioKiji.setTelefono(
						prendiTelefono(HTMLSTring));
				
				anncuncioKiji.setTariffa(
						prendiTariffa(HTMLSTring));
				
				String luogo = prendiCitta(HTMLSTring, 1);
				anncuncioKiji.setLuogo(luogo);
	
				anncuncioKiji.setDataPubblicazioneString(
				prendiDataPubblicazione(HTMLSTring, 2));
				
				anncuncioKiji.setImmagine1(prendiImmaginiAnnuncio(HTMLSTring, 0));
				if(prendiImmaginiAnnuncio(HTMLSTring, 0) != null){
					anncuncioKiji.setNomeImmagine(nomeImmagineFileName);
				}else{
					anncuncioKiji.setNomeImmagine(null);
				}
				anncuncioKiji.setImmagine2(prendiImmaginiAnnuncio(HTMLSTring, 1));
				anncuncioKiji.setImmagine3(prendiImmaginiAnnuncio(HTMLSTring, 2));
			}
			
			if (html.select("div.value").size() == 4){
				
				anncuncioKiji.setNomeUtente(
						prendiNomeUtente(HTMLSTring, 1));
				
				String desc = prendiDescrizione(HTMLSTring);
				anncuncioKiji.setDescrizione(desc);
				
				anncuncioKiji.setTelefono(
						prendiTelefono(HTMLSTring));
				
				anncuncioKiji.setTariffa(
						prendiTariffa(HTMLSTring));
				
				String luogo = prendiCitta(HTMLSTring, 2);
				anncuncioKiji.setLuogo(luogo);
				
				anncuncioKiji.setDataPubblicazioneString(
						prendiDataPubblicazione(HTMLSTring, 3));
				
				anncuncioKiji.setImmagine1(prendiImmaginiAnnuncio(HTMLSTring, 0));
				if(prendiImmaginiAnnuncio(HTMLSTring, 0) != null){
					anncuncioKiji.setNomeImmagine(nomeImmagineFileName);
				}else{
					anncuncioKiji.setNomeImmagine(null);
				}
				anncuncioKiji.setImmagine2(prendiImmaginiAnnuncio(HTMLSTring, 1));
				anncuncioKiji.setImmagine3(prendiImmaginiAnnuncio(HTMLSTring, 2));
			}
			
			if (html.select("div.value").size() == 5){
				
				anncuncioKiji.setNomeUtente(
						prendiNomeUtente(HTMLSTring, 2));
						
				String desc = prendiDescrizione(HTMLSTring);
				anncuncioKiji.setDescrizione(desc);
						
				anncuncioKiji.setTelefono(
						prendiTelefono(HTMLSTring));
				
				anncuncioKiji.setTariffa(
						prendiTariffa(HTMLSTring));
				
				//prendiCitta(HTMLSTring, 3);
				
				String luogo = prendiIndirizzo(HTMLSTring, 1);
				anncuncioKiji.setLuogo(luogo);
				
				anncuncioKiji.setDataPubblicazioneString(
						prendiDataPubblicazione(HTMLSTring, 4));
				
				anncuncioKiji.setImmagine1(prendiImmaginiAnnuncio(HTMLSTring, 0));
				if(prendiImmaginiAnnuncio(HTMLSTring, 0) != null){
					anncuncioKiji.setNomeImmagine(nomeImmagineFileName);
				}else{
					anncuncioKiji.setNomeImmagine(null);
				}
				anncuncioKiji.setImmagine2(prendiImmaginiAnnuncio(HTMLSTring, 1));
				anncuncioKiji.setImmagine3(prendiImmaginiAnnuncio(HTMLSTring, 2));
			}
			
			anncuncioKiji.setAttivo(true);
			
			if(anncuncioKiji.infoGenerali()){
				AnnunciKijijiDogSitter annuncioSalvato = annunciDao.saveAnnuncioDogSitter(anncuncioKiji);
				
				System.out.println("............ID Annuncio salvato: "+annuncioSalvato.getIdAnnuncio());
				//+" "+ annuncioSalvato.getNomeUtente() +" "+ annuncioSalvato.getDescrizione() +" "+ annuncioSalvato.getTelefono());
				
				annunciSalvati_e_aggiornati.add(annuncioSalvato.getIdAnnuncio());
				TOTALE_ANNUNCI_AGGIUNTI = TOTALE_ANNUNCI_AGGIUNTI + 1;

			}else{
				//System.out.println("............CatturaAnnuncioKijiji INFORMAZIONI MANCANTI..............");
			}
			
			
			
		}catch (MalformedURLException mUelExc) {
			System.out.println("............MalformedURLException smistamento");
		}catch(HibernateJdbcException jdbcExcept){
			System.out.println("............HibernateJdbcException smistamento");
			//jdbcExcept.printStackTrace();
		}catch(UnsupportedEncodingException dupluc){
			System.out.println("............UnsupportedEncodingException smistamento");
		}catch(DataIntegrityViolationException dupluc){
			try {
				System.out.println("............aggiorno l'annuncio duplicato......");
				AnnunciKijijiDogSitter annuncioEsistente = annunciDao.getAnnuncioByTelefono(anncuncioKiji.getTelefono());
				
				annuncioEsistente.setDescrizione(anncuncioKiji.getDescrizione());
				annuncioEsistente.setNomeUtente(anncuncioKiji.getNomeUtente());
				annuncioEsistente.setTariffa(anncuncioKiji.getTariffa());
				annuncioEsistente.setLuogo(anncuncioKiji.getLuogo());
				annuncioEsistente.setImmagine1(anncuncioKiji.getImmagine1());
				if(anncuncioKiji.getImmagine1() != null){
					annuncioEsistente.setNomeImmagine(nomeImmagineFileName);
				}else{
					anncuncioKiji.setNomeImmagine(null);
				}
				annuncioEsistente.setImmagine2(anncuncioKiji.getImmagine2());
				annuncioEsistente.setImmagine3(anncuncioKiji.getImmagine3());
				
				annuncioEsistente.setDataPubblicazioneString(anncuncioKiji.getDataPubblicazioneString());
				annuncioEsistente.setDataPubblicazione(null); //così che ripassa per l'aggiustatore link e riaggionra la data ultima modifica
			
				AnnunciKijijiDogSitter annuncioAggiornato = annunciDao.saveAnnuncioDogSitterNoFlush(annuncioEsistente);
				annunciSalvati_e_aggiornati.add(annuncioAggiornato.getIdAnnuncio());
				
			} catch (IllegalArgumentException ia) {
				System.out.println("............IllegalArgumentException smistamento annuncio duplicato");
			} catch (UnsupportedEncodingException e) {
				System.out.println("............UnsupportedEncodingException smistamento annuncio duplicato");
			}catch (Exception e) {
				System.out.println("............Exception smistamento annuncio duplicato");
			}
			
			
		} catch (IOException e) {
			System.out.println("............IOException smistamento");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("............Exception smistamento");
		}
	}
	
	
	public static byte[] prendiImmaginiAnnuncio(String HTMLSTring, int numImmagine) throws IOException, MalformedURLException{
		ArrayList<String> arrLinksImg = new ArrayList<String>();
		Document html = Jsoup.parse(HTMLSTring);
		Elements links = html.select("img"); // a with href
		
		Iterator<Element> aa = links.iterator();
		String linkStr = "";
		
		for(int i=0 ; aa.hasNext() ; i++){
			//String linkStr = link.toString();
			linkStr = aa.next().toString();
			
			if (linkStr.contains("orig.jpg")){
				int tot = linkStr.length();
				int posImg = linkStr.indexOf("img");
				posImg= posImg + 11;
				linkStr = linkStr.substring(posImg, tot);
				
				
				int posPrimaVirgoletta = linkStr.toString().indexOf('"');
				linkStr = linkStr.substring(0, posPrimaVirgoletta);
				arrLinksImg.add(linkStr);
			}
		}
		
		if (arrLinksImg != null && arrLinksImg.size() > numImmagine && arrLinksImg.get(numImmagine) != null){
			String urlImmagine =  "http://"+arrLinksImg.get(numImmagine);
			
			URL url = new URL(urlImmagine);
			BufferedImage originalImage =  ImageIO.read(url); 
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( originalImage, "jpg", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			
			if (imageInByte.length < Constants.MAX_SIZE_FILE_IMAGE){ //1 MB
				return imageInByte;
				
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	public static String prendiTariffa(String HTMLSTring){
		Document html = Jsoup.parse(HTMLSTring);
		if(!html.select("div.vip-price").isEmpty() ){
			Element links = html.select("div.vip-price").get(0);
			int tot = links.html().length();
			String hellWrld = links.html(). substring(0, tot);
			int posPrimoSpazio = hellWrld.toString().indexOf(' ');
			hellWrld = hellWrld. substring(0, posPrimoSpazio);
			//System.out.println("prendiTariffa= "+hellWrld );
			
			return hellWrld;
		}else{
			return "0";
		}
	}

	public static String  prendiTelefono(String HTMLSTring){
		Document html = Jsoup.parse(HTMLSTring);
		Element links = html.select("div.mod-cta-body").get(0);
		//System.out.println("wee= "+links.html().contains("data-inverted")); // contains('data-inverted') );
		if(links.html().contains("data-inverted")){
			int tot = links.toString().length();
			String hellWrld = links.toString(). substring(83, tot);
			int posPrimaVirgoletta = hellWrld.toString().indexOf('"');
			hellWrld = hellWrld. substring(0, posPrimaVirgoletta);
			String rigira = new StringBuilder(hellWrld).reverse().toString();
			
			//Rimuovo i caratteri non numerici e speciali dal telefono
			rigira = rigira.replaceAll("[^0-9]", "").replaceAll("\\s+", "+");

			return rigira;
		}else{
			//System.out.println("prendiTelefono= NON DISPONIBILE" );
			return "";
		}
	}

	public static String prendiDataPubblicazione(String HTMLSTring, int posiz){
		Document html = Jsoup.parse(HTMLSTring);
		Element masthead = html.select("div.value").get(posiz);
		//System.out.println("prendiDataPubblicazione= "+masthead.html() );
		return masthead.text();
	}
	
	public static String prendiNomeUtente(String HTMLSTring, int posiz){
		Document html = Jsoup.parse(HTMLSTring);
		Element masthead = html.select("div.value").get(posiz);
		//System.out.println("prendiNomeUtente= "+masthead.html() );
		//Faccio maiuscolo il nome utente
		String nomeUtente = StringUtils.lowerCase(masthead.text());
		nomeUtente = WordUtils.capitalize(nomeUtente);
		return nomeUtente;
	}
	
	public static String prendiDescrizione(String HTMLSTring) throws UnsupportedEncodingException{
		Document html = Jsoup.parse(HTMLSTring);
		//String bla = html.body().getElementsByTag("p").text();
		Element masthead = html.select("p.ki-view-ad-description").first();
		//String desc = masthead.html();
		String desc = masthead.text(); //NUOVO DA USARE !!!

		desc = util_BUKOWSKI.NormalizzaString(desc);

		//desc = Normalizer.normalize(desc, Normalizer. Form.);
		//System.out.println("DESCRIZIONE= "+desc);
		
		return desc;
	}
	
	public static String prendiIndirizzo(String HTMLSTring, int posiz){
		Document html = Jsoup.parse(HTMLSTring);
		//Element links = html.select("a[href]").get(29); // a with href
		Element links = html.select("div.value").get(posiz);
		int tot = links.toString().length();
		
		if (tot > 63){
			String indirizzo = links.toString(). substring(63, tot);
			int posPrimaVirgoletta = indirizzo.toString().indexOf('"');
			indirizzo = indirizzo. substring(0, posPrimaVirgoletta);
			//System.out.println("prendiIndirizzo= "+indirizzo );
			return indirizzo;
		}else{
			return "";
		}
		
	}
	
	public static String prendiCitta(String HTMLSTring, int posiz){
		Document html = Jsoup.parse(HTMLSTring);
		Element masthead = html.select("div.value").get(posiz);
		String citta = masthead.text();
		//System.out.println("prendiCitta= "+citta);
		return citta;
	}
	

	public static void INIZIO_PRO(){
		String data = df.format(new Date());
		System.out.println("###### INIZIO PROCEDURA CatturaAnnuncioKijiji "+data+" ######");
		System.out.println("###### ANNUNCI ATTUALI= "+TOTALE_ANNUNCI +" ######");
	}
	
	public static void FINE_PRO(){
		String data = df.format(new Date());
		System.out.println("###### FINE PROCEDURA CatturaAnnuncioKijiji "+data+" ######");
		System.out.println("###### ANNUNCI INIZIALI= "+TOTALE_ANNUNCI +" ANNUNCI AGGIUNTI= "+TOTALE_ANNUNCI_AGGIUNTI+" ######");
	}

}
