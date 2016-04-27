package com.mhe.rest.datacash.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {

	public static final String PROPERTY_FILE_NAME = "DataCash_config.properties";
	public static final String PROPERTY_ERROR_FILE_NAME = "error_code.properties";
	static Logger logger = LoggerUtility.getLogger(PropertiesUtil.class
			.getName());

	public static void main(String[] args) throws IOException {
		Properties prop = PropertiesUtil.readProperty();
	}

	/**
	 * read properties file file from classpath
	 * 
	 * @param propertyFileName
	 * 
	 * @throws IOException
	 */

	public static Properties readProperty() {
		Properties prop = new Properties();

		// String propFilePath = System.getProperty("propFileLocation");

		// System.out.println("propFilePath is::"+propFilePath);
		Properties prop_env = new Properties();
		FileInputStream fileInputStream = null;

		// InputStream inputStream = null;

		try {
			fileInputStream = new FileInputStream(
					"/apps/applications/qa/DataCash/config/"
							+ PROPERTY_FILE_NAME);
			prop.load(fileInputStream);

			// inputStream = PropertiesUtil.class.getClassLoader()
			// .getResourceAsStream(propFilePath);
			prop.load(fileInputStream);

		} catch (IOException e) {

			logger.error("File Not found", e);
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {

				logger.error("IOException", e);

			}
		}

		return prop;

	}

	/**
	 * 
	 * @comment read error related properties file from class path
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */

	public static Properties readErrorProperty() {

		Properties prop_error = new Properties();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(
					"/apps/applications/qa/DataCash/config/"
							+ PROPERTY_ERROR_FILE_NAME);
			prop_error.load(fileInputStream);

		} catch (IOException e) {

			logger.debug("Error File Not found", e);
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {

				logger.error("IOException", e);

			}
		}

		return prop_error;

	}

}
