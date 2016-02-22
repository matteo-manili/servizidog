package com.dogsitter.util;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.dogsitter.model.Cane;


/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class CaneListContainer implements Serializable {
	private static final long serialVersionUID = 8581430704215197353L;

	//Important. Set this to a default List in order to avoid null pointer exceptions when the list is empty
	
	

	public CaneListContainer() {
    }
	
    private List<Cane> caneList = new LinkedList<Cane>();
 
    public CaneListContainer(List<Cane> caneList) {
        this.caneList = caneList;
    }
 
    
    
    public List<Cane> getCaneList() {
        return caneList;
    }
 
    public void setCaneList(List<Cane> caneList) {
        this.caneList = caneList;
    }

}
