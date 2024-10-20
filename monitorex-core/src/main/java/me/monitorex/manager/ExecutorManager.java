package me.monitorex.manager;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.monitorex.monitor.Service;
import me.monitorex.monitor.ServicesConfiguration;
import me.monitorex.util.JSONUtil;

public class ExecutorManager implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(ExecutorManager.class);

	private File jsonFile;

	private ServicesConfiguration servicesConfiguration;
	
	public ExecutorManager(File file) {
		this.jsonFile = file;
	}

	@Override
	public void run() {
		logger.info("Starting {}", Thread.currentThread().getName());
		
		try {
			validJson();
			prepareData();
			startMonitoring();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("Finishing {}", Thread.currentThread().getName());
	}

	private void prepareData() throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		servicesConfiguration = mapper.readValue(jsonFile, ServicesConfiguration.class);
	}

	private void startMonitoring() {
		
		Objects.requireNonNull(servicesConfiguration);
		
		for (Service service : servicesConfiguration.getServices()) {
			logger.info("Monitoring {}: {}", service.getName(), service.getUrl());
		}
		
		
	}

	/**
	 * Validate the JSON (syntax only).
	 */
	private void validJson() {
		boolean validJSONContent = JSONUtil.validateJSONFromFile(jsonFile);
		if (!validJSONContent) {
			logger.error("Not a valid JSON file: {}", jsonFile);
			throw new RuntimeException("Not a valid JSON file.");
		}
	}

}
