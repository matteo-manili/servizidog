package com.dogsitter.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.dogsitter.model.Person;
import com.dogsitter.dao.PersonDao;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
 
@Repository("personDao")
public class PersonDaoHibernate extends GenericDaoHibernate<Person, Long> implements PersonDao {
 
    public PersonDaoHibernate() {
        super(Person.class);
    }

    
    public Person findByLastName(String lastName) {
    	List person = getSession().createCriteria(Person.class).add(Restrictions.eq("lastName", lastName)).list();
        return (Person) person.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public List<Person> findByLastNamePersons(String firstName) {
    	List<Person> listPeople = new ArrayList<Person>();
    	listPeople = getSession().createCriteria(Person.class).add(Restrictions.like("firstName", "%"+firstName+"%", MatchMode.END)).list(); 
        return listPeople;
    }
    
    
    public List personListDwr() {
    	List listPerson = getSession().createCriteria(Person.class).list();
        return listPerson;
    }
    
    
}
