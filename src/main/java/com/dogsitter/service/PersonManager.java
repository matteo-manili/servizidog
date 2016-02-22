package com.dogsitter.service;


import java.util.List;

import com.dogsitter.dao.PersonDao;
import com.dogsitter.model.Person;

public interface PersonManager extends GenericManager<Person, Long> {
	
	/**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setPersonDao(PersonDao personDao);

	Person getPersonByUsername(String username);
	
	List<Person> getTuttiPerson() throws Exception;
	
	List<Person> getPersonByNameJson(String term) throws Exception;
	
	
}
