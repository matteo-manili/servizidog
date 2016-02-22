package com.dogsitter.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.dogsitter.model.Person;

@WebService
@Path("/dogsitter")
public interface PersonService {
	
    /**
     * Finds a user by their username.
     *
     * @param username the user's username used to login
     * @return User a populated user object
     */
    Person getPersonByUsername(@PathParam("username") String username);
    
    List<Person> getTuttiPerson() throws Exception;

	
    

}
