package me.monitorex.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utilities for handling JSON files and contents. 
 */
public class JSONUtil {

	private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

	/**
	 * Validate if the JSON content is a valid JSON file.
	 * 
	 * @param jsonFile the JSON file.
	 * 
	 * @return true if it is a valid JSON file, false otherwise.
	 * @throws IOException if an IO error occur. 
	 */
	public static boolean validateJSONFromFile(File jsonFile) throws IOException {
		
		try {
			new ObjectMapper().readTree(readJsonFile(jsonFile));
		} catch (JsonMappingException e) {
			return false;
		} catch (JsonProcessingException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Read a JSON file.
	 * 
	 * @param jsonFile the JSON file.
	 * 
	 * @return JSON content.
	 * @throws IOException if an I/O error occur.
	 */
	public static String readJsonFile(File jsonFile) throws IOException {
		
		StringBuilder resultStringBuilder = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            resultStringBuilder.append(line).append(System.lineSeparator());
	        }
	    }
	    
	  return resultStringBuilder.toString();
	}


}