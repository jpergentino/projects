package me.monitorex;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.monitorex.manager.ExecutorManager;

public class MonitorCoreApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(MonitorCoreApplication.class);

	private static String SERVICES_JSON_FILE = "services.json";

	public static void main(String[] args) throws IOException, URISyntaxException {

		logger.info("Starting application...");

		// Checking if there is argument
		if (args != null && args.length != 0) {

			if (args.length > 1) {
				String msg = "Please provide only one argument: the service.json file.";
				logger.error(msg);
				throw new IllegalArgumentException(msg);
			}

			SERVICES_JSON_FILE = args[0];
		}
		
		File file = Paths.get(SERVICES_JSON_FILE).toFile();

		if (!file.exists() || !file.isFile() || !SERVICES_JSON_FILE.endsWith(".json")) {
			String msg = "The file doesn't exist or is not a valid file: "+ file.getAbsolutePath();
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
		
		logger.info("Using services file: {}", file.getAbsolutePath());
		
		new Thread(new ExecutorManager(file), "Thread-"+ file.getName()).start();
		
	}

}