package com.dogsitter.dao;

import org.appfuse.dao.BaseDaoTestCase;

import com.dogsitter.model.Comuni;
import com.dogsitter.model.Image;
import com.dogsitter.model.Province;
import com.dogsitter.model.Regioni;
import com.dogsitter.model.TipoRuoliServiceDog;
import com.dogsitter.model.User;

import org.hibernate.exception.SQLGrammarException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
public class ImageDaoTest extends BaseDaoTestCase {
    @Autowired
    private PersonDao personDao;
    
    @Autowired
    private ZonaDao zonaDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private ImageDao imageDao;

    @Test
    public void testFindPersonByLastName() {
    	

        try{
        		//String term = "Rov";
				List<Image> listComuni;
				listComuni = null;
						//imageDao.cancelaImmaginiPreferite(1l, 1l); 
				
				System.out.println(listComuni.size());
				
				if(listComuni.size() >= 1){
					System.out.println("vero");
				}else{
					System.out.println("falso");
				}
				
				
		    	System.out.println("Funziona !!!! listComuni.size="+listComuni.size() );
		    	
		    	
		    	Iterator iterator = listComuni.iterator();
		    	ArrayList<String> listComuniString = new ArrayList<String>();
		    	while ( iterator.hasNext() ) {
		    	    Object[] tuple = (Object[]) iterator.next();
		    	    Comuni com =  (Comuni) tuple[0];
		    	    Province prov = (Province) tuple[1];
		    	    Regioni reg = (Regioni) tuple[2];
		    	    //System.out.println(com.getNomeComune()+" "+prov.getNomeProvincia()+" "+reg.getNomeRegione());
		    	    String appo = com.getNomeComune()+", "+prov.getNomeProvincia()+", "+reg.getNomeRegione();
		    	    
		    	    if (listComuniString.size() < 10)
		    	    	listComuniString.add(appo);
		    	}
		    	
		    	System.out.println("listComuniString.size()="+listComuniString.size());
		    	
	    		if(listComuniString.size() > 0){
		    		Iterator<String> iterator2 = listComuniString.iterator();
			        while(iterator2.hasNext()){
			        	String s = iterator2.next();
						System.out.println("listResult sss="+s);
					}
	    		}
	    		System.out.println(".............................................");
	    		

	    		User userr = userDao.get(1L);
	    		
	    		String ee = userr.toString();
	    		System.out.println("ee="+ee);
	    		
	    		Iterator<TipoRuoliServiceDog> we =  userr.getTipoRuoli().iterator();
	    		System.out.println("..........");
	    		while ( we.hasNext() ) {
	    			String aa = we.next().getDescription();
	    			System.out.println("aa="+aa);
	    		}
	    		

	    		//aggiungere add ......
	    		
	    		
	    		
        	}
        
	    	
        
        catch(SQLGrammarException sqlExc){
	    		System.out.println("errore SQLGrammarException ");
	    		System.out.println(sqlExc.getCause());
	    }
	    catch(Exception ee){
        		System.out.println("errore Exception ");
	    		System.out.println(ee.getCause());
	    }
        
        
        
        
        
        
    }
    /*
    @Test(expected = DataAccessException.class)
    public void testAddAndRemovePerson() throws Exception {
        Person person = new Person();
        person.setFirstName("Country");
        person.setLastName("Bry");
     
        person = personDao.save(person);
        flush();
     
        person = personDao.get(person.getId());
     
        assertEquals("Country", person.getFirstName());
        assertNotNull(person.getId());
     
        log.debug("removing person...");
     
        personDao.remove(person.getId());
        flush();
     
        // should throw DataAccessException
        personDao.get(person.getId());
    }
    */
    
}


