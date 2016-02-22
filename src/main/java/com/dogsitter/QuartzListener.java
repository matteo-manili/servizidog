package com.dogsitter;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import com.dogsitter.webapp.util.Avvio_CaricaImmagini;
import com.dogsitter.webapp.util.CreaSitemap;
import com.dogsitter.webapp.util.SalvaImmaginiUtentiBackUp;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 * QUARTS PER FAR PARTIRE PROCEDURE IN AUTOMATICO. VEDERE: http://stackoverflow.com/questions/19573457/simple-example-for-quartz-2-2-and-tomcat-7 
	VEDERE ANCHE: https://quartz-scheduler.org/documentation/quartz-2.2.x/examples/Example1 
	VEDERE ANCHE: http://www.cronmaker.com/ (per impostare il cronometro)
	CE BISOGNO DEL LISTENER NEL WEB.XML
 *
 *
 *
 *		<!-- OGNI GIORNO ALLE 03:05 : VEDERE: http://www.cronmaker.com/ -->
        <cron-expression>0 5 3 1/1 * ? *</cron-expression>
        <!-- OGNI MINUTO -->
        <!-- <cron-expression>0 0/1 * 1/1 * ? *</cron-expression>  -->
 */

@WebListener
public class QuartzListener extends QuartzInitializerListener {
	private static final Log log = LogFactory.getLog(QuartzListener.class);
	
    
	@Override
	public void contextInitialized(ServletContextEvent sce) {
        try {
        	super.contextInitialized(sce);
	        ServletContext ctx = sce.getServletContext();
	        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);
            Scheduler scheduler = factory.getScheduler();

            //(per non farle partire contemporaneamente altrimenti causa il java.lang.OutOfMemoryError: Java heap space)
            Date Ore00 = new Date(TimeUnit.MINUTES.toMillis(0)); //questa parte alle 00 di ogni due ore
            Date ore15 = new Date(TimeUnit.MINUTES.toMillis(15)); //e questa parte a 30 min di ognu due ore 
            Date ore30 = new Date(TimeUnit.MINUTES.toMillis(30)); //e questa parte a 30 min di ognu due ore 
            
            //--------------------------- 1 TRIGGER --------------------------------------------------
            //----------------------------------------------------------------------------------------
            // define the job and tie it to our HelloJob class
            JobDetail job1 = JobBuilder.newJob(CatturaAnnuncioKijiji.class)
        	      .withIdentity("myJob", "group1") // name "myJob", group "group1"
        	      .build();
            
            job1.getJobDataMap().put("ServletContext", ctx);

        	  // Trigger the job to run now, and then every 40 seconds
            Trigger trigger1 = TriggerBuilder.newTrigger()
        	      .withIdentity("myTrigger", "group1")

        	      /*
        	       .withSchedule(SimpleScheduleBuilder.simpleSchedule()
        	          .withIntervalInMinutes(10)
        	          .repeatForever()).build();
        	      */
               //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(3).repeatForever())
        	     // .build() ; 
            /* //QUESTA E' QUELLA BUONA
        	      .withSchedule(
                    CronScheduleBuilder.cronSchedule("0 00 02 ? * *")).startNow().build(); */
        	       
        	  		//0 03 04 ? * *	inizio alle 04:03am every day (parte all 4 e 3 minuti di ogni giorno)
        	     
        	  // per impostare il cronometro vedere: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger
        	  // e anche: http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-05
            
            
            
            .startAt(Ore00) // get the next even-hour (minutes and seconds zero ("00:00"))
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(4)
                .repeatForever())
            // note that in this example, 'forJob(..)' is not called 
            //  - which is valid if the trigger is passed to the scheduler along with the job  
            .build();
            // note that in this example, 'forJob(..)' is not called 
            //  - which is valid if the trigger is passed to the scheduler along with the job  
            
            
            //--------------------------- 2 TRIGGER --------------------------------------------------
            //----------------------------------------------------------------------------------------
            JobDetail job2 = JobBuilder.newJob(CatturaAnnuncioCercapadrone.class)
        	      .withIdentity("myJob", "group2") // name "myJob", group "group1"
        	      .build();
            
            job2.getJobDataMap().put("ServletContext", ctx);

            Trigger trigger2 = TriggerBuilder.newTrigger()
        	      .withIdentity("myTrigger", "group2")

            .startAt(ore30) // get the next even-hour (minutes and seconds zero ("00:00"))
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(4)
                .repeatForever())
            .build();

				//--------------------------- 3 TRIGGER --------------------------------------------------
				//----------------------------------------------------------------------------------------
        	  JobDetail job3 = JobBuilder.newJob(Avvio_CaricaImmagini.class)
              	      .withIdentity("myJob", "group3") // name "myJob", group "group1"
              	      .build();
        	  job3.getJobDataMap().put("ServletContext", ctx);
        	  
        	  Trigger trigger3 = TriggerBuilder.newTrigger()
              	      .withIdentity("myTrigger", "group3").startNow().build();
        	  
				//--------------------------- 4 TRIGGER --------------------------------------------------
				//----------------------------------------------------------------------------------------
        	  JobDetail job4 = JobBuilder.newJob(SalvaImmaginiUtentiBackUp.class)
            	      .withIdentity("myJob", "group4") // name "myJob", group "group1"
            	      .build();
                
        	  job4.getJobDataMap().put("ServletContext", ctx);

            	  // Trigger the job to run now, and then every 40 seconds
                Trigger trigger4 = TriggerBuilder.newTrigger()
            	      .withIdentity("myTrigger", "group4")
            	      
            	      //.startNow().build();
        	  .withSchedule(
                    CronScheduleBuilder.cronSchedule("0 45 03 ? * *")).startNow().build(); //questo quello buono
              //--------------------------- 5 TRIGGER --------------------------------------------------
              //----------------------------------------------------------------------------------------
                JobDetail job5 = JobBuilder.newJob(CreaSitemap.class)
            	      .withIdentity("myJob", "group5") // name "myJob", group "group1"
            	      .build();
                
                job5.getJobDataMap().put("ServletContext", ctx);

                Trigger trigger5 = TriggerBuilder.newTrigger()
            	      .withIdentity("myTrigger", "group5")

                .startAt(ore15) // get the next even-hour (minutes and seconds zero ("00:00"))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInHours(12) //ogni 12 ore e 15
                    .repeatForever())
                .build();
                
                
              //----------------------------------------------------------------------------------------
              //----------------------------------------------------------------------------------------

                //-XX:PermSize=9999m -XX:MaxPermSize=9999m
                //-Xms256M -Xmx256M 
                
                scheduler.scheduleJob(job1, trigger1); //parte ogni 4 ore al minuto 0:00 di ogni giorno
                scheduler.scheduleJob(job2, trigger2); //parte ogni 4 ore al minuto 0:30 di ogni giorno
                scheduler.scheduleJob(job3, trigger3); //parte all'avvio dell'applicazione
                scheduler.scheduleJob(job4, trigger4); //parte ogni giorno alle 3:45
                scheduler.scheduleJob(job5, trigger5); //parte ogni 12 ore al minuto 0:15 di ogni giorno
                
                
        } catch (Exception e) {
        	System.out.println("Exception QuartzListener.contextInitialized");
        	e.printStackTrace();
        }
    }



    
    
	 
}
