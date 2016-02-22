package com.dogsitter;


/**
 * Constant values used throughout the application.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */


public final class Constants {

    private Constants() {
        // hide me
    }
    //~ Static fields/initializers =============================================
    
    public static final String DOMAIN = "http://www.servizidog.it";
    
    public static final String DOGSITTER_ROLE = "ROLE_DOGSITTER";
    
    public static final String DOGHOST_ROLE = "ROLE_DOGHOST";
    
    public static final String ADOZIONE_ROLE = "ROLE_ADOZIONE";
    
    public static final String ASSOCIAZIONE_ROLE = "ROLE_ASSOCIAZIONE";
    
    public static final String AVAILABLE_ROLES_SERVIZIDOG = "availableRuoliServiziDog";
    
    public static final String RUOLI_SERVIZI_UTENTE = "ruoliServiziUtente";
    
    public static final String PREZZO_MEDIO_DOGHOST = "15,00";
    
    public static final String PREZZO_MEDIO_DOGSITTER = "8,00";
    
    public static final Integer MAX_DESCRIZIONE_TAG_DESCRIPTION = 170;
    
    public static final String STORAGE_DIRECTORY_IMMAGINI = "/tmpImage";
    
    public static final String STORAGE_DIRECTORY_IMMAGINI_ANNUNCI = "/tmpImageAnnunci";
    
    //public static final String IMAGES_DIRECTORY = "/images"; //(QUESTA CARTELLA E' PROTETTA QUANDO SI STA NELLA ROOT DI TOMCAT di javahost.it !!!!!)
    
    public static final String IMAGES_DIRECTORY = "/img";
    
    public static final String IMMAGINE_SOSTITUTIVA = "/img/logo-thumbnail.png";
    
    public static final String FACEBOOK_APP_ID = "547748282047154";
    
	public static final String FACEBOOK_APP_SECRET = "cc3c3e81cc9dacd2e689de910e778433";
	
	public static final String FACEBOOK_REDIRECT_URI = "http://localhost:8080/servizidog/facebookReturn";
	
	public static final String FACEBOOK_PASSWRD = "";
	
	public static final String DOG_SITTER = "dog-sitter";
	
	public static final String DOGSITTER = "dogsitter";
	
	public static final String PET_SITTER = "pet-sitter";
	
	public static final String PETSITTER = "petsitter";
	
	public static final String DOG = "dog";
	
	public static final String PET = "pet";
	
	public static final String SITTER = "sitter";
	
	public static final String PATH_THUMBNAIL_ANN_DOGSITTER = "/thumbnailAnnuncioDogSitter";
	
	public static final String PATH_THUMBNAIL_ANN_ADOZIONE = "/thumbnailAnnuncioAdozione";
	
	public static final long MAX_SIZE_FILE_IMAGE = 614400; //600 kb
	
	public static final long MAX_IMMAGINI_X_QUERY = 100; //600 kb
	
	public static final int MAX_IMMAGINI_X_QUERY_PICCOLA = 5; //600 kb
    
    /**
     * Assets Version constant
     */
    public static final String ASSETS_VERSION = "assetsVersion";
    /**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses, we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_KEY = "userForm";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";

    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    /**
     * The name of the User role, as specified in web.xml
     */
    public static final String USER_ROLE = "ROLE_USER";

    /**
     * The name of the user's role list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * The name of the CSS Theme setting.
     * @deprecated No longer used to set themes.
     */
    public static final String CSS_THEME = "csstheme";


}
