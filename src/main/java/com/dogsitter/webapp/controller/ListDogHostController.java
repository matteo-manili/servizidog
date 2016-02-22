package com.dogsitter.webapp.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.dogsitter.Constants;
import com.dogsitter.model.DogHost;
import com.dogsitter.model.Image;
import com.dogsitter.webapp.util.BuildRicercaJSONObject;

/**
 * @author Matteo Manili - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping(value = "/doghost*", method = RequestMethod.GET)
public class ListDogHostController extends BaseFormController {
	
	
	@RequestMapping(method = RequestMethod.GET)
    protected ModelAndView dogHostGet(final HttpServletRequest request, final HttpServletResponse response) {
		try{
	        return new ModelAndView("listDogHost");
		}catch(Exception exc){
			return new ModelAndView("redirect:/");
		}
    }
	
	
	@ModelAttribute("DogHostJson")
	protected List<JSONObject> ricercaDogHostJson(final HttpServletRequest request) throws Exception { 
	
		String context = request.getContextPath();
		List<JSONObject> list = new LinkedList<JSONObject>();
		final Locale locale = request.getLocale();
		final String servizioDogHost = getText("doghost.title", locale);
		BuildRicercaJSONObject brJSONOBJECT = new BuildRicercaJSONObject();
		
		//DOG HOST
    	Iterator<DogHost> dog_host_ite = getDogHostManager().getDogHosts().iterator();
    	DogHost doghost = null;
		while (dog_host_ite.hasNext()) {
			doghost = new DogHost();
			doghost = dog_host_ite.next();
			if(doghost.informazioniGeneraliInserite() ){
				
				Image image = getImageManager().getImmaginePrefeita( doghost.getUser().getId(), 
						getTipoRuoliManager().getTipoRuoliServiceDogDaoByName(Constants.DOGHOST_ROLE).getId());
				
				list.add( brJSONOBJECT.DogHostBuildJSONObject(doghost, image, servizioDogHost, context) );
			}
		}

		return list;

	}
	
	
	@ModelAttribute("DogHostTot")
    protected String loadDogHostTot(final HttpServletRequest request) {
        	int totale = getDogHostManager().getDogHostConInfoInserite();
            return String.valueOf(totale);
        }
	
	
}
