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
import java.util.NoSuchElementException;
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

import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.dao.GestioneApplicazioneDao;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.GestioneApplicazione;
import com.dogsitter.util.util_BUKOWSKI;
import com.dogsitter.webapp.util.AnnunciAdozione_CancellaAnnunciDuplicati;
import com.dogsitter.webapp.util.Attiva_Disattiva_Annunci;
import com.dogsitter.webapp.util.Avvio_CaricaImmagini;
import com.dogsitter.webapp.util.CreaUrlSlugify;
import com.dogsitter.webapp.util.OrdinaCoordinateCerchio_Annunci;
import com.google.code.geocoder.model.GeocodeResponse;

/**
 * @author Matteo - matteo.manili@gmail.com
 * 
 * La classe è schedulata per avviarsi ogni 4 ore dal momento dello start dell'applicaton server. 
 * Raccoglie gli annunci dal sito www.ilcercapadrone.it e li memorizza.
 *
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CatturaAnnuncioCercapadrone implements Job {
	private static final Log log = LogFactory.getLog(CatturaAnnuncioCercapadrone.class);
	
	public static ApplicationContext contextDao = 
    		new ClassPathXmlApplicationContext("App-Database-Spring-Module.xml");
	
	public static AnnunciCercaPadroneAdozioneDao annunciDao = (AnnunciCercaPadroneAdozioneDao) contextDao.getBean("AnnunciCercaPadroneAdozioneDao");
	public static GestioneApplicazioneDao gestioneAppDao = (GestioneApplicazioneDao) contextDao.getBean("GestioneApplicazioneDao");
	
	public static long TOTALE_ANNUNCI = 0;
	public static int TOTALE_ANNUNCI_AGGIUNTI = 0;
	public static DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss aa", Locale.ITALIAN);
	
	public static ArrayList<Long> annunciSalvati_e_aggiornati = new ArrayList<Long>();
	 
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.debug("QUARTS JobExecutionContext.... CatturaAnnuncioCercapadrone");
		try{
			TOTALE_ANNUNCI = annunciDao.getAnnunciCercaPadroneAdozione_COUNT();
			INIZIO_PRO();
			
			GestioneApplicazione ATTIVA_CATTURA_ADOZIONE = gestioneAppDao.getGestioneApplicazioneName("ATTIVA_CATTURA_ADOZIONE");
			
			if (ATTIVA_CATTURA_ADOZIONE.isAttivo() == true){
				
				//#################################################################
				//#################################################################
				
				inizio( ATTIVA_CATTURA_ADOZIONE.getPaginaInizio(), ATTIVA_CATTURA_ADOZIONE.getPaginaFine() );
				AGGIUSTA_ANNUNCI_CoordinateANDsetUrlANDsetTariffa();
			
				//CANCELLO GLI ANNUNCI PIU' VECCHI CON LE DESCRIZIONI DUPLICATE
				AnnunciAdozione_CancellaAnnunciDuplicati.inizio();
				
				//ATTIVO E DISATTIVO GLI ANNUNCI IN BASE A UNA DURATA DI TEMPO
				Attiva_Disattiva_Annunci.cercaPadrone(annunciDao, gestioneAppDao);
				
				//POSIZIONO GLI ANNUNCI CHE HANNO LO STESSO LUOGO IN MANIERA CIRCOLARE SULLA MAPPA
				OrdinaCoordinateCerchio_Annunci.inizio("ADOZIONE");
				
				
				log.debug("quartz CreaImmaginiAnnunciAdozione.salvaImmaginiInFolderImages...");
				ServletContext servletContext = (ServletContext) context.getMergedJobDataMap().get("ServletContext");
				Avvio_CaricaImmagini caricaImmaginiAnnunciAdozione = new Avvio_CaricaImmagini();
				caricaImmaginiAnnunciAdozione.caricaImmaginiAnnunci_salvati_e_aggiornati_Adozione(annunciDao, annunciSalvati_e_aggiornati, servletContext);
				log.debug("CaricaImmagini Annunci Adozione [OK]");
				
				System. gc();
				
				//#################################################################
				//#################################################################
				
			}else{
				log.debug("ATTIVA_CATTURA_ADOZIONE="+ ATTIVA_CATTURA_ADOZIONE.isAttivo());
			}
			
			FINE_PRO();
			
		}catch(Exception ee){
			System.out.println("ERRORE IN CatturaAnnuncioCercapadrone.execute ");
			log.debug("ERRORE IN CatturaAnnuncioCercapadrone.execute ");
			ee.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		//ELIMINA ANNUCIO
		//annunciDao.eliminaAnnuncioDogSitter( annunciDao.get((long) 1339) );
		//Amante degli Animali offre servizio di dog sitter
		//annucio eliminato 1339
		//ANNUNCI ATTUALI= 524
		TOTALE_ANNUNCI = annunciDao.getAnnunciCercaPadroneAdozione_COUNT();
		INIZIO_PRO();
		GestioneApplicazione ATTIVA_CATTURA_ADOZIONE = gestioneAppDao.getGestioneApplicazioneName("ATTIVA_CATTURA_ADOZIONE");
		//if (ATTIVA_CATTURA_ADOZIONE.isAttivo() == true){
			//inizio( 1,1 ); //283

			//AGGIUSTA_ANNUNCI_CoordinateANDsetUrlANDsetTariffa();
			
			//AnnunciAdozione_CancellaAnnunciDuplicati.inizio();
			
			//Attiva_Disattiva_Annunci.cercaPadrone(annunciDao, gestioneAppDao);
			
			//OrdinaCoordinateCerchio_Annunci.inizio("ADOZIONE");
			
			PROVE_PORCO_DIO();
		//}
		FINE_PRO();
    }
	
	

	public static void PROVE_PORCO_DIO() {
		try {
			DateFormat dfStr = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
			GestioneApplicazione ATTIVA_CATTURA_ADOZIONE = gestioneAppDao.getGestioneApplicazioneName("ATTIVA_CATTURA_ADOZIONE");
			int numeroMesiAnnuncioAttivo =  ATTIVA_CATTURA_ADOZIONE.getNumeroMesiAnnuncioAttivo();
			System.out.println("numero_Mesi_Annuncio_Attivo= "+numeroMesiAnnuncioAttivo);
			
			String dataString;
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			Date inizio = c.getTime();
			
			dataString = dfStr.format(inizio);
			System.out.println("data inizio= "+dataString);
			
			c.add (Calendar.MONTH, -numeroMesiAnnuncioAttivo);
			Date fine = c.getTime();
			
			dataString = dfStr.format(fine);
			System.out.println("data fine= "+dataString);
			
			annunciSalvati_e_aggiornati.add(9830L);
			annunciSalvati_e_aggiornati.add(10052L);
			annunciSalvati_e_aggiornati.add(10679L);
			
			annunciSalvati_e_aggiornati.add(9970L);
			annunciSalvati_e_aggiornati.add(9971L);
			annunciSalvati_e_aggiornati.add(9972L);
			annunciSalvati_e_aggiornati.add(9973L);
			annunciSalvati_e_aggiornati.add(9979L);
			annunciSalvati_e_aggiornati.add(9981L);
			annunciSalvati_e_aggiornati.add(9474L);
			annunciSalvati_e_aggiornati.add(9237L);
			annunciSalvati_e_aggiornati.add(5486L);
			annunciSalvati_e_aggiornati.add(9894L);
			
			
			annunciSalvati_e_aggiornati.add(9844L);
			annunciSalvati_e_aggiornati.add(9845L);
			annunciSalvati_e_aggiornati.add(9847L);
			annunciSalvati_e_aggiornati.add(2683L);
			annunciSalvati_e_aggiornati.add(9851L);
			annunciSalvati_e_aggiornati.add(9852L);
			annunciSalvati_e_aggiornati.add(9853L);
			annunciSalvati_e_aggiornati.add(9854L);
			annunciSalvati_e_aggiornati.add(9855L);
			annunciSalvati_e_aggiornati.add(9856L);
			
			annunciSalvati_e_aggiornati.add(9799L);
			annunciSalvati_e_aggiornati.add(9800L);
			annunciSalvati_e_aggiornati.add(9802L);
			annunciSalvati_e_aggiornati.add(9807L);
			annunciSalvati_e_aggiornati.add(9808L);
			annunciSalvati_e_aggiornati.add(8095L);
			annunciSalvati_e_aggiornati.add(9729L);
			annunciSalvati_e_aggiornati.add(9730L);
			annunciSalvati_e_aggiornati.add(9731L);
			annunciSalvati_e_aggiornati.add(9733L);
			
			annunciSalvati_e_aggiornati.add(9738L);
			annunciSalvati_e_aggiornati.add(11102L);
			annunciSalvati_e_aggiornati.add(11103L);
			annunciSalvati_e_aggiornati.add(11107L);
			annunciSalvati_e_aggiornati.add(11109L);
			annunciSalvati_e_aggiornati.add(11111L);
			annunciSalvati_e_aggiornati.add(11112L);
			annunciSalvati_e_aggiornati.add(11115L);
			annunciSalvati_e_aggiornati.add(11116L);
			annunciSalvati_e_aggiornati.add(11151L);
			
			
			
			int maxImmagini = 5;
			Long countResutl = annunciDao.getAnnunciCercaPadroneAdozione_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI_COUNT(annunciSalvati_e_aggiornati);
			System.out.println("countResutl=" +countResutl);
			
			
			for(int first = 0; first < countResutl; ){
				System.out.println("first="+first);
				
				List<Object[]> objList = annunciDao.getAnnunciCercaPadroneAdozione_IMMAGINI_ANNUNCI_SALVATI_E_AGGIORNATI(annunciSalvati_e_aggiornati, first, maxImmagini);
				List<AnnunciCercaPadroneAdozione> listAdozioneAnnunci = new ArrayList<AnnunciCercaPadroneAdozione>();
				for(Object[] annuncio: objList){
		    		AnnunciCercaPadroneAdozione annuncioAdozione = new AnnunciCercaPadroneAdozione();
		    		annuncioAdozione.setIdAnnuncio( (Long)annuncio[0] );
		    		annuncioAdozione.setImmagine1( (byte[])annuncio[1] );
		    		annuncioAdozione.setNomeImmagine( (String)annuncio[2] );
		            listAdozioneAnnunci.add(annuncioAdozione);
		        }
				
				Iterator<AnnunciCercaPadroneAdozione> annunci_adozione_ite = listAdozioneAnnunci.iterator();
				while (annunci_adozione_ite.hasNext()) {
					AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione = annunci_adozione_ite.next();
					if(annunciCercaPadroneAdozione.getImmagine1().length < Constants.MAX_SIZE_FILE_IMAGE){
					
						byte[] immagineByte = annunciCercaPadroneAdozione.getImmagine1();
						String nomeImmagine = annunciCercaPadroneAdozione.getNomeImmagine();
						String idAnnuncio  = annunciCercaPadroneAdozione.getIdAnnuncio().toString();
						System.out.println("idAnnuncio=" + idAnnuncio +" nome immagine=" + nomeImmagine );
					}
				}
				
				first = first + maxImmagini;
				System.out.println("----------------------");
			
			}
			
			/*
			int restult = annunciDao.getAnnunciCercaPadroneAdozione_RICERCA().size();
			System.out.println("getAnnunciCercaPadroneAdozione_RICERCA_MAPPA ="+restult);
			*/
			
			/*
			int restult = annunciDao.getAnnunciKijijiDogSitter_SET_NOME_IMMAGINE_NULL();
			System.out.println("immagini messse  a null ="+restult);
			*/
			
			
			/*
			DateFormat dfStr = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
			String dataString;
			long annunci_TOTALI = annunciDao.getAnnunciCercaPadroneAdozione_TOTALE_COUNT();
			System.out.println("annunci_TOTALI= "+annunci_TOTALI);
			
			long annunciAttivi_COUNT = annunciDao.getAnnunciCercaPadroneAdozione_COUNT();
			System.out.println("annunciAttivi_COUNT= "+annunciAttivi_COUNT);
			//------------------------------
			 

			System.out.println("predo annunci con data futura........");
			List<AnnunciCercaPadroneAdozione> listAnnunciFuturi = annunciDao.getAnnunciCercaPadroneAdozione_DATE_FUTURE(inizio);
			System.out.println("listAnnunciFuturi.size= "+listAnnunciFuturi.size());
			
			Iterator<AnnunciCercaPadroneAdozione> futuri_annunci_ite =  listAnnunciFuturi.iterator();
			while(futuri_annunci_ite.hasNext()){
				AnnunciCercaPadroneAdozione annuncio = futuri_annunci_ite.next();
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
			System.out.println("Exception PROVE_PORCO_DIO");
			ex.printStackTrace();
		}
	}


	

	public static void inizio(int PAGINA_INIZIO, int PAGINE_FINE){
		String urlLinkRicerca = "http://www.ilcercapadrone.it/index.php/categorie_annunci/cani/";
		                         //http://www.ilcercapadrone.it/index.php/categorie_annunci/cani/page/2/
		for ( ; PAGINA_INIZIO >= PAGINE_FINE; PAGINA_INIZIO--){ // could have used i, doesn't matter.
			String si_ParamPage="page/"+PAGINA_INIZIO+"/";
			if (PAGINA_INIZIO == 1){
				System.out.println("############ PAGINA "+urlLinkRicerca+" ##############");
				prendiLinksPaginaRicerca(urlLinkRicerca);
			}else{
				System.out.println("############ PAGINA "+urlLinkRicerca+si_ParamPage+" ##############");
				prendiLinksPaginaRicerca(urlLinkRicerca+si_ParamPage);
			}
	    }
	}

	public static void AGGIUSTA_ANNUNCI_CoordinateANDsetUrlANDsetTariffa(){
		AnnunciCercaPadroneAdozione annunciCercaPadroneAdozione = null;
		AnnunciCercaPadroneAdozione annuncioModifica = null;
		
		String luogo = "";
		String dataString = "";
		//int year = Calendar.getInstance().get(Calendar.YEAR);
		
		List<AnnunciCercaPadroneAdozione> annunciAdoz = annunciDao.getAnnunciCercaPadroneAdozione_AGGIUSTA_ANNUNCI();
		Iterator<AnnunciCercaPadroneAdozione> annunci_adozione_ite = annunciAdoz.iterator();
		
		while (annunci_adozione_ite.hasNext()) {
			annunciCercaPadroneAdozione = annunci_adozione_ite.next();
			//if(annunciCercaPadroneAdozione.infoMinime_x_salvare() ){
				
				annuncioModifica = new AnnunciCercaPadroneAdozione();
				annuncioModifica = annunciDao.get(annunciCercaPadroneAdozione.getIdAnnuncio());
				
				String telefono = annunciCercaPadroneAdozione.getTelefono();
				if (telefono != null){
				telefono = telefono.replaceAll("Tel. ", "");
				annuncioModifica.setTelefono( telefono );
				}else{
					annuncioModifica.setTelefono( "" );
				}
				if (annunciCercaPadroneAdozione.getDataPubblicazione() == null ){
					try {
						dataString = annunciCercaPadroneAdozione.getDataPubblicazioneString(); //"January 2, 2010";
						DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ITALIAN);
						Date date = format.parse(dataString);
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						//c.set(Calendar.YEAR, year);
						date = c.getTime();
						annuncioModifica.setDataPubblicazione(date);
						//System.out.println(".....setDataPubblicazione() salvato.... "+date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						System.out.println("ParseException AGGIUSTA_ANNUNCI_CoordinateANDsetUrlANDsetTariffa");
					}
				}

				String city = null;
				if (annunciCercaPadroneAdozione.getLat() == 0 && annunciCercaPadroneAdozione.getLng() == 0){
					luogo = annunciCercaPadroneAdozione.getLuogo();
					luogo = StringUtils.replace(luogo, "Altro", "");
					
					//luogo = StringUtils.replace(luogo, "(", ",");
					//luogo = StringUtils.replace(luogo, ")", ",");
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

							//System.out.println(".....coordinate salvate.....");
						}
					
					}catch(IndexOutOfBoundsException dd){
						//dd.printStackTrace();
						//System.out.println("IndexOutOfBoundsException coordinate google");
					}
				}
				
				if (annunciCercaPadroneAdozione.getUrlProfilo() == null || annunciCercaPadroneAdozione.getUrlProfilo().equals("")){
					try {
						String cityUrl = "";
						if (city != null){
							cityUrl = city;
						}else{
							cityUrl = annuncioModifica.getLuogo();
						}

						annuncioModifica.setUrlProfilo( CreaUrlSlugify.creaUrlAnnunci("adozione-annunci", cityUrl, annuncioModifica.getTitoloCane(), 
								annuncioModifica.getIdAnnuncio()));
						
					} catch (IOException e) {
						System.out.println("IOException setUrlProfilo()");
					}
				}

				//AnnunciCercaPadroneAdozione annSalvato = 
						annunciDao.saveAnnuncioAdozioneNoFlush(annuncioModifica);
				//System.out.println("...annuncio aggiustato= "+annSalvato.getIdAnnuncio());
			//}
		} // fine while

	}
	
	public static void prendiLinksPaginaRicerca(String urlPaginaLinks){
		try{
			ArrayList<String> arrLinks = new ArrayList<String>();
			
			Elements links = HTMLpagina( urlPaginaLinks ).select("a"); // a with href
			Iterator<Element> aa = links.iterator();
			for(int i=0 ; aa.hasNext() ; i++){
				//String linkStr = link.toString();
				String linkStr = aa.next().toString();
				String linksPaginarRic = pulisciLink(linkStr, 6, "http://www.ilcercapadrone.it/index.php/annuncio/", "www.ilcercapadrone.it/wp-content/uploads");
				if(linksPaginarRic != null){
					arrLinks.add( linksPaginarRic );
				}
			}
			
			Iterator<String> iteAnnunci = arrayListLinksPuliti_E_Rigirati(arrLinks).iterator();
			//Iterator<String> iteAnnunci = arrLinks.iterator();
			while ( iteAnnunci.hasNext() ){
				smistamento(iteAnnunci.next());
			}
			
		}catch(NoSuchElementException ee){
			System.out.println("NoSuchElementException prendiLinksPaginaRicerca");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public static void smistamento(String urlString){
		AnnunciCercaPadroneAdozione anncuncioAdozione = new AnnunciCercaPadroneAdozione();
		String nomeImmagineFileName = UUID.randomUUID().toString();
		ArrayList<Object> arrListImage;
		arrListImage = new ArrayList<Object>();
		try{
			//devo aprire un altro link per recuparare il nome utente dell'insersionsta...
			anncuncioAdozione.setNomeUtente( prendiNomeUtente(urlString) );
			
			Document html = HTMLpagina(urlString);
			if( html != null){
				anncuncioAdozione.setTitoloCane( prendiTitoloCane(html) );
				anncuncioAdozione.setEmail( prendiEmail(html) );
				anncuncioAdozione.setTelefono( prendiTelefono(html) );
				anncuncioAdozione.setLuogo( prendiLuogo(html) );
				anncuncioAdozione.setDataPubblicazioneString( prendiDataPubblicazione(html) );
				anncuncioAdozione.setDescrizione( prendiDescrizione(html) );
				
				//anncuncioAdozione.setImmagine1(prendiImmaginiAnnuncio(html));
				//anncuncioAdozione.setNomeImmagine(newImmagineFileName);
				
				anncuncioAdozione.setAttivo(true);
				
				arrListImage = prendiImmaginiAnnuncio(html);
				
				if(arrListImage != null){
					anncuncioAdozione.setNomeImmagine( nomeImmagineFileName+(String)arrListImage.get(0) );
					anncuncioAdozione.setImmagine1( (byte[])arrListImage.get(1) );
				}else{
					anncuncioAdozione.setNomeImmagine(null);
				}

			if(anncuncioAdozione.infoMinime_x_salvare()){
				//System.out.println("SALVO L'ANNUNCIO!!!");
				AnnunciCercaPadroneAdozione annuncioSalvato = annunciDao.saveAnnuncioAdozione(anncuncioAdozione);
				System.out.println("............ID Annuncio salvato: "+annuncioSalvato.getIdAnnuncio());
						//+" "+ annuncioSalvato.getNomeUtente() +" "+ annuncioSalvato.getDescrizione() +" "+ annuncioSalvato.getTelefono());
				
				annunciSalvati_e_aggiornati.add(annuncioSalvato.getIdAnnuncio());
				TOTALE_ANNUNCI_AGGIUNTI = TOTALE_ANNUNCI_AGGIUNTI + 1;
			}else{
				//System.out.println("............CatturaAnnuncioCercapadrone INFORMAZIONI MANCANTI..............");
			}
		}
			
		}
		catch (MalformedURLException mUelExc) {
			System.out.println("............MalformedURLException smistamento");
			mUelExc.printStackTrace();
		}
		catch(HibernateJdbcException jdbcExcept){
			System.out.println("............HibernateJdbcException smistamento");
		}
		catch(UnsupportedEncodingException dupluc){
			System.out.println("............UnsupportedEncodingException smistamento");
		}
		catch(DataIntegrityViolationException dupluc){
			try {
				System.out.println("............Aggiorno l'annuncio duplicato......");
				AnnunciCercaPadroneAdozione annuncioEsistente = annunciDao.getAnnuncioBy_Titolo_e_Mail(anncuncioAdozione.getTitoloCane(), anncuncioAdozione.getEmail());
				
				annuncioEsistente.setTelefono( anncuncioAdozione.getTelefono() );
				annuncioEsistente.setLuogo( anncuncioAdozione.getLuogo() );
				annuncioEsistente.setDataPubblicazioneString( anncuncioAdozione.getDataPubblicazioneString() );
				annuncioEsistente.setDescrizione( anncuncioAdozione.getDescrizione() );
				annuncioEsistente.setImmagine1( anncuncioAdozione.getImmagine1() );
				//annuncioEsistente.setNomeImmagine(nomeImmagineFileName);
				
				if(arrListImage != null){
					anncuncioAdozione.setNomeImmagine( nomeImmagineFileName+(String)arrListImage.get(0) );
					anncuncioAdozione.setImmagine1( (byte[])arrListImage.get(1) );
				}else{
					anncuncioAdozione.setNomeImmagine(null);
				}
				//------------------------------
				
				annuncioEsistente.setDataPubblicazione(null); //così che ripassa per l'aggiustatore link e riaggionra la data ultima modifica
				annuncioEsistente.setUrlProfilo(null);
				annuncioEsistente.setLat(0);
				annuncioEsistente.setLng(0);
				
				AnnunciCercaPadroneAdozione annuncioAggiornato = annunciDao.saveAnnuncioAdozioneNoFlush(annuncioEsistente);
				annunciSalvati_e_aggiornati.add(annuncioAggiornato.getIdAnnuncio());
				
			} catch (IllegalArgumentException ia) {
				System.out.println("............IllegalArgumentException smistamento annuncio duplicato");
			}catch (Exception e) {
				System.out.println("............Exception smistamento annuncio duplicato");
			}
		}
		catch (IndexOutOfBoundsException ia) {
			System.out.println("............IndexOutOfBoundsException smistamento");
		}catch (IOException eIOE) {
			System.out.println("............IOException smistamento");
			eIOE.printStackTrace();
		}catch (Exception exc) {
			System.out.println("............Exception smistamento");
			exc.printStackTrace();
		}
		
	}
	
	
	public static ArrayList<Object> prendiImmaginiAnnuncio(Document html) throws IOException, MalformedURLException{
		
		Elements links = html.select("a"); // a with href
		Iterator<Element> aa = links.iterator();
		
		for(int i=0 ; aa.hasNext() ; i++){
			//String linkStr = link.toString();
			ArrayList<Object> arrListImage = new ArrayList<Object>();
			String linkStr = aa.next().toString();
			String linksPaginarRic = pulisciLink(linkStr, 6, "http://www.ilcercapadrone.it/wp-content/uploads/", "");
			if(linksPaginarRic != null){
				if (linksPaginarRic.contains("jpg") || linksPaginarRic.contains("png")){
					URL url = new URL(linksPaginarRic);
					BufferedImage originalImage = ImageIO.read(url);
					
					if(linksPaginarRic.contains("jpg") || linksPaginarRic.contains("png")){
						String formatoImm = "";
						if(linksPaginarRic.contains("jpg")){
							formatoImm = "jpg";
						}else{
							formatoImm = "png";
						}
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write( originalImage, formatoImm, baos );
						baos.flush();
						byte[] imageInByte = baos.toByteArray();
						baos.close();
						if (imageInByte.length < Constants.MAX_SIZE_FILE_IMAGE){
							//System.out.println("...url immagine= "+linksPaginarRic);
							arrListImage.add("."+formatoImm);
							arrListImage.add(imageInByte);
							return arrListImage;
						}
						baos = null;
						imageInByte = null;
					}
					originalImage = null;
				}
			}
		}
		return null;
	}
	
	public static String prendiNomeUtente(String urlPaginaLinks) throws IOException, IndexOutOfBoundsException {
		try{
			//System.out.println( urlPaginaLinks );
			ArrayList<String> arrLinks = new ArrayList<String>();
			Elements links = HTMLpagina( urlPaginaLinks ).select("a"); // a with href
			Iterator<Element> aa = links.iterator();
			for(int i=0 ; aa.hasNext() ; i++){
				String linkStr = aa.next().toString();
				String bodyUtente = pulisciLink(linkStr, 6, "http://www.ilcercapadrone.it/index.php/author/", "");
				if ( bodyUtente != null){
					Document html = HTMLpagina( bodyUtente );
					if( html != null){
						Element elem = html.select("h1.entry-title").get(0);
						//System.out.println("...prendiNomeUtente= "+elem.text());
						//Faccio maiuscolo il nome utente
						String nomeUtente = StringUtils.lowerCase(elem.text());
						nomeUtente = WordUtils.capitalize(nomeUtente);
						return nomeUtente;
					}
				}
			}
		}catch(NoSuchElementException ee){
			System.out.println("NoSuchElementException prendiNomeUtente");
		}
		return "";
	}
	
	public static String prendiTitoloCane(Document html) throws UnsupportedEncodingException{
		if( html != null){
			Element elem = html.select("h1.entry-title").get(0);
			String titoloCane = elem.text();
			
			int tot = titoloCane.length();
			if (tot > 80){
				titoloCane = titoloCane.substring(0, 80);
			}
			if(titoloCane != null && !titoloCane.equals("")){
				titoloCane = util_BUKOWSKI.NormalizzaString(titoloCane);
				titoloCane = StringUtils.strip(titoloCane);
				titoloCane = StringUtils.upperCase(titoloCane);
				//System.out.println("...prendiTitoloCane= "+titoloCane);
				return titoloCane;
			}else{
				return null;
			}
		}
		return null;
	}
	
	public static String prendiEmail(Document html){
		if( html != null){
			Elements links = html.select("a"); // a with href
			Iterator<Element> aa = links.iterator();
			for(int i=0 ; aa.hasNext() ; i++){
				String linkStr = aa.next().toString();
				String email = pulisciLink(linkStr, 13, "mailto", "@");
				if ( email != null && !email.equals("")){
					//System.out.println("...prendiEmail= "+email);
					return email;
				}
			}
		}
		return null;
	}
	
	public static String prendiTelefono(Document html){
		if( html != null){
			Element elem = html.select("div.entry-meta").get(1);
			//Rimuovo i caratteri non numerici e speciali dal telefono
			String telefono = elem.text();
			//telefono = telefono.replaceAll("[^0-9]", "").replaceAll("\\s+", "+");
			telefono = telefono.replaceAll("Tel. ", "");
			
			return telefono;
			}
		return "";
	}
	
	public static String prendiLuogo(Document html) throws IndexOutOfBoundsException{
		if( html != null){
			Element elem = html.select("div.indirizzo-canile").get(0);
			String luogo = elem.text(); //NUOVO DA USARE !!!
			//System.out.println("...prendiLuogo= "+luogo);
			return luogo;
			}
		return "";
	}
	
	public static String prendiDataPubblicazione(Document html){
		if( html != null){
			Element elem = html.select("time.entry-date").get(0);
			String data = elem.text(); //NUOVO DA USARE !!!
			//System.out.println("...prendiDataPubblicazione= "+data);
			return data;
			}
		return "";
	}
	
	public static String prendiDescrizione(Document html) throws UnsupportedEncodingException {
		if( html != null){
			Element elem = html.select("div.entry-content").get(0);
			Elements elem2 = elem.select("h3");
			String descrizione = elem.text();
			String daTogliere = elem2.text();
			descrizione = descrizione.replace(daTogliere, "");
			descrizione = StringUtils.stripStart(descrizione, null);
			descrizione = util_BUKOWSKI.NormalizzaString(descrizione);
			return descrizione;
		}
		return "";
	}
	
	public static String pulisciLink(String linkStr, int posizione, String contiene, String contiene_2){
		
    	try{
			int tot = linkStr.length();
			int posHref = linkStr.indexOf("href");
			
			if(linkStr.contains( contiene ) && linkStr.contains( contiene_2 )){
			
				posHref= posHref + posizione;
				linkStr = linkStr.substring(posHref, tot);
				
				int posPrimaVirgoletta = linkStr.toString().indexOf('"');
				linkStr = linkStr.substring(0, posPrimaVirgoletta);

				return linkStr;
			}
		}catch(Exception ee){
			System.out.println("Exception pulisciLink");
		}
		return null;
    }
	
	public static Document HTMLpagina(String urlString) throws IOException{
		if(urlString != null){
			URL url;
		    InputStream is = null;
		    BufferedReader br;
		    String line;
		    try {
		    	url = new URL(urlString);
		        is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        //br = new BufferedReader(new InputStreamReader(is));
		        String fileHtml = "";
		        while ((line = br.readLine()) != null) {
		        	fileHtml = fileHtml + line;
		        }
		        br.close();is.close();
		        return Jsoup.parse(fileHtml);
		        
		        //Document document = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
		        
		    } catch (MalformedURLException mue) {
		    	System.out.println("MalformedURLException prendiHTMLpagina");
		    } 
		}
		return null;

	}
	
	public static ArrayList<String> arrayListLinksPuliti_E_Rigirati(ArrayList<String> arrLinks){
		//elimino i duplicati
		//arrLinks = new ArrayList<String>(new LinkedHashSet<String>(arrLinks));
		//System.out.println("arrLinks size= "+arrLinks.size());
		Collections.reverse(arrLinks);

		return arrLinks;
	}

	public static void INIZIO_PRO(){
		String data = df.format(new Date());
		System.out.println("###### INIZIO PROCEDURA CatturaAnnuncioCercapadrone "+data+" ######");
		System.out.println("###### ANNUNCI ATTUALI= "+TOTALE_ANNUNCI +" ######");
	}
	
	public static void FINE_PRO(){
		String data = df.format(new Date());
		System.out.println("###### FINE PROCEDURA CatturaAnnuncioCercapadrone "+data+" ######");
		System.out.println("###### ANNUNCI INIZIALI= "+TOTALE_ANNUNCI +" ANNUNCI AGGIUNTI= "+TOTALE_ANNUNCI_AGGIUNTI+" ######");
	}
	
	
}
