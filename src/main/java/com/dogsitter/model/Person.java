package com.dogsitter.model;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "person")
public class Person extends BaseObject implements Serializable {
    private static final long serialVersionUID = -4313357867937454334L;
    
    
	private Long id;
    private String firstName;
    private String lastName;
    
    /*

    private Set<String> numTelefono;
    
    private void setNumTelefono(Set<String> numTelefono) {
    	this.numTelefono = numTelefono;
    }
    	 

    
    @ElementCollection
    @CollectionTable(name = "num_telefono", joinColumns = @JoinColumn(name = "id") )
    @Column(name = "numero")
    public Set<String> getNumTelefono() {
    	return (Set<String>) this.numTelefono;
    }
    */
    
    


    @Id @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="first_name", length=50)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name="last_name", length=50)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}



}
