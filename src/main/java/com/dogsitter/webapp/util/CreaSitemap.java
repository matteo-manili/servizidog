package com.dogsitter.webapp.util;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

import com.dogsitter.Constants;
import com.dogsitter.dao.AdozioneDao;
import com.dogsitter.dao.AnnunciCercaPadroneAdozioneDao;
import com.dogsitter.dao.AnnunciKijijiDogSitterDao;
import com.dogsitter.dao.AssociazioneDao;
import com.dogsitter.dao.CaneDao;
import com.dogsitter.dao.DogHostDao;
import com.dogsitter.dao.DogSitterDao;
import com.dogsitter.model.Adozione;
import com.dogsitter.model.AnnunciCercaPadroneAdozione;
import com.dogsitter.model.AnnunciKijijiDogSitter;
import com.dogsitter.model.Associazione;
import com.dogsitter.model.Cane;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.DogSitter;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.W3CDateFormat;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import com.redfin.sitemapgenerator.W3CDateFormat.Pattern;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 * Questa classe passandogli un indirizzo crea una parte del link adatto agli standard seo, ad esempio:
 * http://www.servizidog.it/servizidog/adozione/la-cinquina-bufalotta/via-alberto-lollio/3
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CreaSitemap implements Job, Serializable {
	private static final long serialVersionUID = 7031958874268022841L;
	private static final Log log = LogFactory.getLog(CreaSitemap.class);
	public static DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss aa", Locale.ITALIAN);
	
	public static ApplicationContext contextDao = 
    		new ClassPathXmlApplicationContext("App-Database-Spring-Module.xml");
	
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		String data = df.format(new Date());
		System.out.println("#######################################################################################################");
		System.out.println("############# INIZIO PROCEDURA CreaSitemap "+data+" ####################################");
		System.out.println("#######################################################################################################");
		
		ServletContext servletContext = (ServletContext) context.getMergedJobDataMap().get("ServletContext");
		
		DogSitterDao daoDogSitter = (DogSitterDao) contextDao.getBean("DogSitterDao");
		DogHostDao daoDogHost = (DogHostDao) contextDao.getBean("DogHostDao");
		AdozioneDao daoAdozione = (AdozioneDao) contextDao.getBean("AdozioneDao");
		AssociazioneDao daoAssociazione = (AssociazioneDao) contextDao.getBean("AssociazioneDao");
		CaneDao daoCane = (CaneDao) contextDao.getBean("CaneDao");
		AnnunciCercaPadroneAdozioneDao daoAnnunciAdozione = (AnnunciCercaPadroneAdozioneDao) contextDao.getBean("AnnunciCercaPadroneAdozioneDao");
		AnnunciKijijiDogSitterDao daoAnnunciDogSitter = (AnnunciKijijiDogSitterDao) contextDao.getBean("AnnunciKijijiDogSitterDao");
		
		List<DogSitter> listDogSitter = daoDogSitter.getDogSitters();
		List<DogHost> listDogHost = daoDogHost.getDogHosts();
		List<Adozione> listAdozione = daoAdozione.getAdozioni();
		List<Associazione> listAssociazione = daoAssociazione.getAssociazioni();
		List<Cane> listCani = daoCane.getCanes();
		List<AnnunciCercaPadroneAdozione> listAdozioneAnnunci = daoAnnunciAdozione.getAnnunciCercaPadroneAdozione_SITEMAP();
		List<AnnunciKijijiDogSitter> listDogSitterAnnunciDogSitter = daoAnnunciDogSitter.getAnnunciKijijiDogSitter_SITEMAP();
		
		log.debug("QUARTZ CreaSitemap...");
		this.creaSitemapProUrlProfili(servletContext, listDogSitter, listDogHost, listAdozione, listAssociazione, listCani, listDogSitterAnnunciDogSitter, listAdozioneAnnunci);
	}
	
	
	public void creaSitemapProUrlProfili(ServletContext servletContext, List<DogSitter> listDogSitter, List<DogHost> 
		listDogHost, List<Adozione> listAdozione, List<Associazione> listAssociazione, List<Cane> listCani, 
		List<AnnunciKijijiDogSitter> listDogSitterAnnunci, List<AnnunciCercaPadroneAdozione> listAdozioneAnnunci) {
		
		String fileUploadDirectory = servletContext.getRealPath("/");
		
		//String contentP = servletContext.getContextPath();
		File myDir = new File(fileUploadDirectory);
		//File myFile = new File(myDir, nameFileSitemap);
		
		try {
			WebSitemapGenerator wsg = new WebSitemapGenerator(Constants.DOMAIN, myDir);
		
			W3CDateFormat dateFormat = new W3CDateFormat(Pattern.DAY);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			// asseggno il <lastmod> al sitemap
			wsg = WebSitemapGenerator.builder(Constants.DOMAIN, myDir).dateFormat(dateFormat).build();
			WebSitemapUrl url;
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.HOURLY).build();
			wsg.addUrl(url);
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/home").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.HOURLY ).build();
			wsg.addUrl(url);
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/login").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.MONTHLY).build();
			wsg.addUrl(url);
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/registrazione").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.MONTHLY).build();
			wsg.addUrl(url);
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/recoverpass").lastMod(new Date()).priority(0.5).changeFreq(ChangeFreq.MONTHLY).build();
			wsg.addUrl(url);
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/mappa").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.MONTHLY).build();
			wsg.addUrl(url);
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/ricerca").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.MONTHLY).build();
			wsg.addUrl(url);
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/contatti").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.MONTHLY).build();
			wsg.addUrl(url);
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/dogsitter").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.MONTHLY).build();
			wsg.addUrl(url);

			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/doghost").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.MONTHLY).build();
			wsg.addUrl(url);
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/adozione").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.MONTHLY).build();
			wsg.addUrl(url);
			
			url = new WebSitemapUrl.Options(Constants.DOMAIN+"/associazione").lastMod(new Date()).priority(0.10).changeFreq(ChangeFreq.MONTHLY).build();
			wsg.addUrl(url);
			
			for(DogSitter dogSitter : listDogSitter) {
				if(dogSitter.informazioniGeneraliInserite()){
					dogSitter.getUrlProfilo();
					url = new WebSitemapUrl.Options(Constants.DOMAIN+"/"+dogSitter.getUrlProfilo())
						.lastMod( dogSitter.getUltimaModifica() ).priority(0.8).changeFreq(ChangeFreq.WEEKLY ).build();
					wsg.addUrl(url);
				}
			}
			for(DogHost dogHost : listDogHost) {
				if(dogHost.informazioniGeneraliInserite()){
					dogHost.getUrlProfilo();
					url = new WebSitemapUrl.Options(Constants.DOMAIN+"/"+dogHost.getUrlProfilo())
						.lastMod( dogHost.getUltimaModifica() ).priority(0.8).changeFreq(ChangeFreq.WEEKLY ).build();
					wsg.addUrl(url);
				}
			}
			for(Adozione adozione : listAdozione) {
				Iterator<Cane> iteCane = listCani.iterator();
				Cane cane = new Cane();
				boolean caniPresenti = false;
				while(iteCane.hasNext()){
					cane = iteCane.next();
					if (cane.getAdozione() != null && cane.getAdozione().getId() == adozione.getId()){
						caniPresenti = true;
					}
				}
				if(adozione.informazioniGeneraliInserite()  &&  caniPresenti ){
						adozione.getUrlProfilo();
						url = new WebSitemapUrl.Options(Constants.DOMAIN+"/"+adozione.getUrlProfilo())
							.lastMod( adozione.getUltimaModifica() ).priority(0.8).changeFreq(ChangeFreq.WEEKLY ).build();
						wsg.addUrl(url);
				}
			}
			for(Associazione associazione : listAssociazione) {
				if(associazione.informazioniGeneraliInserite() /*&& daoCane.getCaneByAssociazione(associazione.getId()).size() > 0*/ ){
					associazione.getUrlProfilo();
						url = new WebSitemapUrl.Options(Constants.DOMAIN+"/"+associazione.getUrlProfilo())
							.lastMod( associazione.getUltimaModifica() ).priority(0.8).changeFreq(ChangeFreq.WEEKLY ).build();
						wsg.addUrl(url);
				}
			}
			for(AnnunciKijijiDogSitter annunciDogSitter : listDogSitterAnnunci) {
				if(annunciDogSitter.getUrlProfilo() != null && annunciDogSitter.getDataPubblicazione() != null){
					annunciDogSitter.getUrlProfilo();
						url = new WebSitemapUrl.Options(Constants.DOMAIN+"/"+annunciDogSitter.getUrlProfilo())
							.lastMod( annunciDogSitter.getDataPubblicazione() ).priority(0.8).changeFreq(ChangeFreq.WEEKLY ).build();
						wsg.addUrl(url);
				}
			}
			for(AnnunciCercaPadroneAdozione annunciAdozione : listAdozioneAnnunci) {
				if(annunciAdozione.getUrlProfilo() != null && annunciAdozione.getDataPubblicazione() != null){
					annunciAdozione.getUrlProfilo();
						url = new WebSitemapUrl.Options(Constants.DOMAIN+"/"+annunciAdozione.getUrlProfilo())
							.lastMod( annunciAdozione.getDataPubblicazione()).priority(0.8).changeFreq(ChangeFreq.WEEKLY ).build();
						wsg.addUrl(url);
				}
			}
			
			wsg.write();
			log.debug("Creazione Sitemap.xml complete [OK] "+fileUploadDirectory);
			
		} catch (MalformedURLException e) {
			log.debug("ERRORE MalformedURLException CreaSitemap.creaSitemapProUrlProfili");
			e.printStackTrace();
		}catch (Exception exc) {
			log.debug("ERRORE Exception CreaSitemap.creaSitemapProUrlProfili");
			exc.printStackTrace();
		}
	}


	
}
