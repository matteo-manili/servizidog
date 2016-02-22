package com.dogsitter.service.dwr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dogsitter.dao.PersonDao;
import com.dogsitter.model.Person;
import com.dogsitter.service.dwr.PersonManagerDWR;
import com.dogsitter.service.impl.GenericManagerImpl;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Service("PersonManagerDWR")
public class PersonManagerImplDWR extends GenericManagerImpl<Person, Long> implements PersonManagerDWR {
	
	
	private PersonDao personDao;
	

    @Override
    @Autowired
    public void setPersonDao(final PersonDao personDao) {
        this.dao = personDao;
        this.personDao = personDao;
    }
	
    
    
    @Override
    public String sayHello(String name) throws Exception {
    	try{
            System.out.println("sono in PersonManagerImplDWR sayHello name="+name);
            
            Person person = new Person();
        	person = personDao.findByLastName(name);
            
            return person.getFirstName();
            
    	}catch(Exception ee){
    		System.out.println("sono in exception");
			System.out.println("getCause:"+ ee.getCause());
			return "errore";
    	}
    }

	
	@Override
	public List<String> allUsersStrings(String firstNameDwr) throws Exception {
		
		System.out.println("sono in PersonManagerImplDWR.allUsersStrings(String="+firstNameDwr+")");
		List<String> listPeopleString = new ArrayList<String>();	
		List<Person> listPerson;
		try{
    		listPerson = personDao.findByLastNamePersons(firstNameDwr);
    		System.out.println("Funziona !!!!");
    		if(listPerson.size() > 0){
	    		Iterator<Person> iterator = listPerson.iterator();
		        while(iterator.hasNext()){
		        	String s = iterator.next().getFirstName();
		        	listPeopleString.add(s);
					System.out.println("allUsersStrings s="+s);
				}
    		}
		return listPeopleString;
		
		}catch(Exception ee){
			System.out.println("sono in exception");
			System.out.println("getCause:"+ ee.getCause());
			return listPeopleString;
		}
	
	}
	
	@Override
	public List<Person> allUsersPerson(String firstNameDwr) throws Exception {
		
		System.out.println("sono in PersonManagerImplDWR.allUsersPerson(String firstNameDwr)");
		List<Person> listPeople = new ArrayList<Person>();	
		try{
			listPeople = personDao.findByLastNamePersons(firstNameDwr);
    		System.out.println("Funziona !!!!");
    		
    		if(listPeople.size() > 0){
	    		Iterator<Person> iterator = listPeople.iterator();
		        while(iterator.hasNext()){
		        	String s = iterator.next().getFirstName();
					System.out.println("allUsersPerson s="+s);
				}
    		}
    		
    		
		return listPeople;
		
		}catch(Exception ee){
			System.out.println("sono in exception");
			System.out.println("getCause:"+ ee.getCause());
			return null;
		}
	
	}
	
	
	//----------------------------------------
	@Override
	public Person getpersonListDwr(String nome) throws Exception {
		System.out.println("sono in PersonManagerImplDWR.getpersonListDwr()");
    	Person person = new Person();
    	//person.setId(1L);
    	//person.setFirstName("dai cazzo");
    	//person.setLastName("da per dio");
    	person = personDao.findByLastName(nome);
    	return (Person)person;
    }

	
	
	@Override
	public Person[] getAllPersonListDwr() throws Exception {

		System.out.println("sono in PersonManagerImplDWR.getAllPersonListDwr()");
		
		/*
		Person[] stockArr = new Person[personDao.personListDwr().size()];
		stockArr = (Person[]) personDao.personListDwr().toArray(stockArr);

		for(Person s : stockArr)
		    System.out.println("allora="+s.getFirstName());
		    
		return stockArr;
		*/

		Person[] list = new Person[2];


		Person info = null;
		info = new Person();
		info.setId(1L);
		info.setFirstName("PORCO DIO");
		info.setLastName("PORCO DIO");
		list[0] = info;

		
		
		for(Person s : list)
		    System.out.println("allora="+s.getFirstName());
		

		return list;
		
	}

    @Override
	public Person get(Long id) {
		// TODO Auto-generated method stub
		return super.get(id);
	}


	@Override
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return super.exists(id);
	}


	@Override
	public Person save(Person object) {
		// TODO Auto-generated method stub
		return super.save(object);
	}


	@Override
	public void remove(Person object) {
		// TODO Auto-generated method stub
		super.remove(object);
	}


	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		super.remove(id);
	}

}
