package com.dogsitter.webapp.controller;


import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dogsitter.Constants;
import com.dogsitter.model.FacebookUser;
import com.dogsitter.model.User;
import com.dogsitter.service.RoleManager;
import com.dogsitter.service.UserExistsException;
import com.dogsitter.webapp.util.GeneraUsernameUnivocoFacebook;



/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
@Controller
@RequestMapping(value = "/facebookReturn")
public class FacebookController extends BaseFormController {
	
	private RoleManager roleManager;
    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }
	

    
	public FacebookController() {
		setCancelView("redirect:/");
        setSuccessView("redirect:/login"); 
	}


	@RequestMapping(method = RequestMethod.GET)
    public String facebookGet(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws IOException{
    	System.out.println("sono in FacebookRetunDataController.facebookGet GET");
    	
    	return getSuccessView();
    }
	
	

	
	
	

	@RequestMapping(method = RequestMethod.POST)
    public String facebookPost(final HttpServletRequest request, final HttpServletResponse response, ModelMap modelMap) throws IOException{
    	System.out.println("sono in FacebookRetunDataController.facebookGet POST");
        final Locale locale = request.getLocale();
        FacebookUser facebookUser = new FacebookUser();
    	try{
    		
    		/*
    		String code = request.getParameter("code");
    		if (code == null || code.equals("")) {
    			throw new RuntimeException(
    					"ERROR: Didn't get code parameter in callback.");
    		}
    		
    		System.out.println("code_Facebook: "+code);
    		
    		FBConnection fbConnection = new FBConnection();
    		String accessToken = fbConnection.getAccessToken(code);

    		
    		FBGraph fbGraph = new FBGraph(accessToken);
    		String graph = fbGraph.getFBGraph();
    		JSONObject jsObj = fbGraph.getGraphData(graph);
    		
    		
    		
    		facebookUser.setId( jsObj.getString("id"));
    		facebookUser.setNomeCompleto(jsObj.getString("name"));
    		facebookUser.setFirstName(jsObj.getString("first_name"));
    		facebookUser.setLastName(jsObj.getString("last_name"));
    		facebookUser.setEmail(jsObj.getString("email"));
    		facebookUser.setGender(jsObj.getString("gender"));
    		facebookUser.setLocale(jsObj.getString("locale"));
    		facebookUser.setPicture(jsObj.get("picture"));
    		facebookUser.setCover(jsObj.get("cover"));
    		*/
    		
    		facebookUser.setId(request.getParameter("idFB"));
    		facebookUser.setNomeCompleto(request.getParameter("nameFB"));
    		facebookUser.setFirstName(request.getParameter("firstNameFB"));
    		
    		if(request.getParameter("middleNameFB") != null && !request.getParameter("middleNameFB").equals("")){
    			facebookUser.setLastName(request.getParameter("middleNameFB")+" "+request.getParameter("lastNameFB"));
    		}else{
    			facebookUser.setLastName(request.getParameter("lastNameFB"));
    		}

    		facebookUser.setEmail(request.getParameter("emailFB"));
    		facebookUser.setGender(request.getParameter("genderFB"));
    		facebookUser.setLocale(request.getParameter("localeFB"));
    		facebookUser.setPicture(request.getParameter("pictureFB"));
    		facebookUser.setCover(request.getParameter("coverFB"));
    		
    		/*
    		System.out.println("id= "+jsObj.getString("id"));
    		System.out.println("name= "+jsObj.getString("name"));
    		System.out.println("first_name= "+jsObj.getString("first_name"));
    		System.out.println("last_name= "+jsObj.getString("last_name"));
    		System.out.println("email= "+jsObj.getString("email"));
    		System.out.println("gender= "+jsObj.getString("gender"));
    		System.out.println("locale= "+jsObj.getString("locale"));
    		System.out.println("cover= "+jsObj.get("cover"));
    		System.out.println("picture= "+jsObj.get("picture"));
    		*/
    		
    		
    		
    	/*
	    }catch (final InvocationTargetException ite) {
	    	saveError(request, getText("errore di connessione a facebook ", locale));
	    	ite.printStackTrace();
	    	return "login";
	    }catch (final IOException ioE) {
	    	saveError(request, getText("errore di connessione a facebook ", locale));
	    	ioE.printStackTrace();
	    	return "login";    	*/
	    }catch (final Exception exc) {
	    	saveError(request, getText("errore di connessione a facebook ", locale));
	    	exc.printStackTrace();
	    	return "login";
	    }

    	
    	try{

    		if (facebookUser.informazioniGeneraliRicevute() && this.getUserManager().getUserByEmail(facebookUser.getEmail()) != null ){
    			
        		User user = this.getUserManager(). getUserByEmail(facebookUser.getEmail());
        		
        		if (user.getFacebookId() == null || user.getFacebookId().equals("")){
	        		user.setFacebookId(facebookUser.getId());
	        		this.getUserManager().save(user);
        		}
        		
            	// log user in automatically
        		this.loginAutomatic(user, request, locale);
    	        
    	        //mi serve per il menu

        		//getServletContext().setAttribute(Constants.RUOLI_SERVIZI_UTENTE, user.getTipoRuoliServiceDogListPerMenu());
    	        request.getSession().setAttribute(Constants.RUOLI_SERVIZI_UTENTE, user.getTipoRuoliServiceDogListPerMenu());
    	        
        		return "redirect:/home-utente";

    		}else{
    			
    			return "redirect:/login";
    			
    		}
		    
	        }catch(UsernameNotFoundException unfe){
	    		System.out.println("username not fount REGISTRO L'UTENTE FACEBOOK");
	    		// REGISTRO L'UTENTE FACEBOOK....
    			User user = new User();


    			String usernameGenerata = GeneraUsernameUnivocoFacebook.GeneraUsername(facebookUser.getFirstName(), facebookUser.getLastName(), false);

    			while( this.getUserManager().userUsernameExist(usernameGenerata) == true ){
    				usernameGenerata = GeneraUsernameUnivocoFacebook.GeneraUsername(facebookUser.getFirstName(), facebookUser.getLastName(), true);
    			}

    			System.out.println("username generata= "+usernameGenerata);
    			user.setUsername( usernameGenerata );
    			
    			user.setFacebookId(facebookUser.getId());
    			user.setEmail(facebookUser.getEmail());
    			user.setFirstName(facebookUser.getFirstName());
    			user.setLastName(facebookUser.getLastName());
    			user.setPassword(Constants.FACEBOOK_PASSWRD);
    			
    			user.addRole(roleManager.getRole(Constants.USER_ROLE));
    			
    			//inserisco la data di adesso 
    	        user.setSignupDate(new Date());
    	    	//disabilito l'utente
    	        user.setEnabled(true);
    	        user.setConfirmDate(new Date());
    		
	        	//salvo l'utente

				try {
					user = this.getUserManager().saveUser(user);
				} catch (UserExistsException e) {
					// TODO Auto-generated catch block
					System.out.println("FacebookController UserExistsException REGISTRAZIONE UTENTE FACEBOOK "+facebookUser.getNomeCompleto() );
					e.printStackTrace();
				}

		        // log user in automatically
				saveMessage(request, getText("user.registered", user.getUsername(), locale));
		        request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);
		        
		        this.loginAutomatic(user, request, locale);

		        return "redirect:/home-utente";
		        
	        }catch (final AccessDeniedException ade) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
	            return null;
	        }catch (final Exception exc) {
		    	exc.printStackTrace();
		    	return "login";
		    }
    	
    }

	
    private void loginAutomatic(User user, HttpServletRequest request, Locale locale){
    	
    	final String password = user.getPassword();

        // log user in automatically
        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(), password, user.getAuthorities());
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }


}
