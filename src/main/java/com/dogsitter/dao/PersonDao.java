package com.dogsitter.dao;

import java.util.List;
import com.dogsitter.model.Person;

public interface PersonDao extends GenericDao<Person, Long> {
	
	public Person findByLastName(String lastName);
    
	public List personListDwr();
	
	public List<Person> findByLastNamePersons(String firstName);
	
}
