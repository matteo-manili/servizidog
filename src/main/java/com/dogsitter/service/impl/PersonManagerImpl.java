package com.dogsitter.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogsitter.dao.PersonDao;
import com.dogsitter.model.Person;
import com.dogsitter.service.PersonManager;
import com.dogsitter.service.PersonService;

@Service("personManager")
@WebService(serviceName = "PersonService", endpointInterface = "com.dogsitter.service.PersonService")
public class PersonManagerImpl extends GenericManagerImpl<Person, Long> implements PersonManager, PersonService  {
	
    private PersonDao personDao;
	

    @Override
    @Autowired
    public void setPersonDao(final PersonDao personDao) {
        this.dao = personDao;
        this.personDao = personDao;
    }


	@Override
	public Person getPersonByUsername(String username) {
		return (Person) personDao.findByLastName(username);
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getTuttiPerson() throws Exception {
		List<Person> listPeople = new ArrayList<Person>();	
		try{
			listPeople = personDao.personListDwr();
    		
    		if(listPeople.size() > 0){
	    		Iterator<Person> iterator = listPeople.iterator();
		        while(iterator.hasNext()){
		        	String s = iterator.next().getFirstName();
				}
    		}
    		
    		
		return listPeople;
		
		}catch(Exception ee){
			return null;
		}
	
	}
	
	@Override
	public List<Person> getPersonByNameJson(String term) throws Exception {
		List<Person> listPeople = new ArrayList<Person>();	
		
		listPeople = personDao.findByLastNamePersons(term);

		return listPeople;

	}
	
	
}
