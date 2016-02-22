package com.dogsitter.service.impl;

import com.dogsitter.dao.LookupDao;
import com.dogsitter.model.LabelValue;
import com.dogsitter.model.Role;
import com.dogsitter.model.TipoRuoliServiceDog;
import com.dogsitter.service.LookupManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
    @Autowired
    LookupDao dao;

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getName(), role1.getName()));
        }
        return list;
    }
    
    
    public List<LabelValue> getAllRuoliServiziDog() {
        List<TipoRuoliServiceDog> ruoliServiziDog = dao.getRuoliServiziDog();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (TipoRuoliServiceDog role1 : ruoliServiziDog) {
            list.add(new LabelValue(role1.getDescription(), role1.getName()));
        }

        return list;
    }

    
}
