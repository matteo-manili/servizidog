package com.dogsitter.service;

import com.dogsitter.model.DogSitter;
import com.dogsitter.model.LabelValue;

import java.util.List;

/**
 * Business Service Interface to talk to persistence layer and
 * retrieve values for drop-down choice lists.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupManager {
    /**
     * Retrieves all possible roles from persistence layer
     * @return List of LabelValue objects
     */
    List<LabelValue> getAllRoles();
    
    List<LabelValue> getAllRuoliServiziDog();
    
}
