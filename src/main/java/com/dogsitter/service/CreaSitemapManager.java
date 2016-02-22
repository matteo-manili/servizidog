package com.dogsitter.service;

import java.io.FileNotFoundException;

import javax.servlet.ServletContext;

/**
 * @author Matteo - matteo.manili@gmail.com
 *
 */
public interface CreaSitemapManager {
	
	void creaSitemapProUrlProfili(ServletContext servletContext) throws FileNotFoundException, NullPointerException;
	
}

