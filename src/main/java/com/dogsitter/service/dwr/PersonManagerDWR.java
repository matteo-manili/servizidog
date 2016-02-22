package com.dogsitter.service.dwr;

import java.util.List;

import com.dogsitter.dao.PersonDao;
import com.dogsitter.model.Person;
import com.dogsitter.service.GenericManager;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
public interface PersonManagerDWR extends GenericManager<Person, Long> {
	
	void setPersonDao(PersonDao personDao);
	
	String sayHello(String name) throws Exception;
	
	List<String> allUsersStrings(String firstNameDwr) throws Exception;
	
	List<Person> allUsersPerson(String firstNameDwr) throws Exception;
	
	Person getpersonListDwr(String nome) throws Exception;

	Person[] getAllPersonListDwr() throws Exception;
}
