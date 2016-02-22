package com.dogsitter.service;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public class UserSignupTokenNotFoundException extends Exception {
	private static final long serialVersionUID = 8126430410869355672L;

	public UserSignupTokenNotFoundException(final String message) {
        super(message);
    }

	public UserSignupTokenNotFoundException() {

	}
	
	

}
