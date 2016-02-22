package com.dogsitter.dao;

import org.appfuse.dao.BaseDaoTestCase;

import com.dogsitter.model.Cane;
import com.dogsitter.model.Comuni;
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
 
public class PersonDaoTest extends BaseDaoTestCase {
    @Autowired
    private PersonDao personDao;
    
    @Autowired
    private ZonaDao zonaDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private CaneDao caneDao;

    @Test
    public void testFindPersonByLastName() {
    	

        try{
        	
        	
        	
        	
        	
        	
        	
        	
        	Iterator<Cane> iteratorcANE = caneDao.getCaneByAdozione(1l).iterator();
    		Cane cane = null;
    		while ( iteratorcANE.hasNext() ) {
    			cane = iteratorcANE.next();
    			System.out.println("nome cane "+cane.getNomeCane());
    		}
        	
        	
        	
        	
        	
        	
        	
        	
        		System.out.println("------------------ALTRA ROBA---------------");
        		String term = "Rov";
				List<Object> listComuni;
				listComuni = zonaDao.findByZonaRicercaGoogleMapComuneProvinciaRegione(term); 
		    	
		    	
		    	Iterator<Object> iterator = listComuni.iterator();
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
			        	String s = (String)iterator2.next();
						System.out.println("listResult sss="+s);
					}
	    		}
	    		System.out.println(".............................................");
	    		

	    		User userr = userDao.get(-1L);
	    		
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


