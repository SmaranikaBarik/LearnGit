package com.mhe.rest.datacash.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



/**
 * 
 * This class is used to create logger type object. This class contains a static
 * method getLogger() which is used to return logger type object
 * 
 * @author TCS
 * 
 */

public class LoggerUtility {

	/**
	 * This method is used to accept class names as parameter and returns log
	 * object
	 * 
	 * @param className
	 * @return log
	 */
	private static Logger logger;
	public static final String PROPERTY_LOG_FILE_NAME = "log4j.properties";

	public static Logger getLogger(String className) {
		Properties prop = new Properties();
		try {
			PropertyConfigurator.configure("/apps/applications/DataCash/config/" + PROPERTY_LOG_FILE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger = Logger.getLogger(className);
		return logger;
	}
	
	
	 

}
