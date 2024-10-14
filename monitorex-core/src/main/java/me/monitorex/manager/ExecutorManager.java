package me.monitorex.manager;


import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.monitorex.util.JSONUtil;

public class ExecutorManager implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(ExecutorManager.class);

	private File jsonFile;
	
	public ExecutorManager(File file) {
		this.jsonFile = file;
	}

	@Override
	public void run() {
		logger.info("Starting {}", Thread.currentThread().getName());
		
		validJson();
		startMonitoring();
		
		logger.info("Finishing {}", Thread.currentThread().getName());
	}

	private void startMonitoring() {
		
	}

	/**
	 * Validate the JSON. 
	 */
	private void validJson() {
		boolean validJSONContent = JSONUtil.validateJSONFromFile(jsonFile);
		if (!validJSONContent) {
			logger.error("Not a valid JSON file: {}", jsonFile);
			throw new RuntimeException("Not a valid JSON file.");
		}
	}

}
