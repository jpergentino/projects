package me.monitorex.monitor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(MonitorThread.class);

	public static final String MSG_SERVICE_IS_REQUIRED = "A service is required to run a Monitor process.";
	public static final String MSG_SERVICE_NAME_IS_REQUIRED = "A service name is required to run a Monitor process.";
	public static final String MSG_SERVICE_URL_IS_REQUIRED = "An url is required to run a Monitor process.";
	public static final String MSG_EXPECTED_RESULT_CODE_IS_REQUIRED = "An expected result code is required to run a Monitor process.";
	public static final String MSG_INTERVAL_IS_REQUIRED = "An interval is required to run a Monitor process.";
	
	private Service service;
	
	private boolean canMonitor = true;
	
	public MonitorThread(Service service) {
		this.service = service;
	}

	@Override
	public void run() {
		
		logger.debug("Running {} {}", service.getName(), service.getUrl());
		
		validateRequiredFields();
		
		HttpClient client = HttpClient.newHttpClient();
		
		URI url = URI.create(service.getUrl());
		String methodToUse = service.getMethod() != null ? service.getMethod().toUpperCase() : "GET";
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(url)
				.GET()
				.timeout(service.getTimeout() != null && service.getTimeout() > 0 
						? Duration.ofSeconds(service.getTimeout()) 
						: Duration.ofSeconds(60))
				.method(methodToUse, methodToUse.equals("POST") || methodToUse.equals("PUT") 
						? BodyPublishers.ofString(service.getRequestBody()) 
						: BodyPublishers.noBody())
				.build();
		
		while (canMonitor) {

			logger.debug("Verifying {} {}", service.getName(), service.getUrl());
			
			try {
				long startTime = System.nanoTime();
				HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
				long endTime = System.nanoTime();
				
				double durationInMillis = (endTime - startTime) / 1_000_000_000.0;
				
				logger.debug("{} {} result code {} in {} sec.", service.getName(), service.getUrl(), response.statusCode(),  Math.round(durationInMillis * 1000.0) / 1000.0);
				
				if (response.statusCode() != service.getExpectedResultCode()) {
					logger.error("Unexpected result code for {}: {}", service.getName(), response.statusCode());
				}
				
			} catch (IOException | InterruptedException e) {
				logger.error("Error when requesting {}: {}", url, e.getMessage());
			}
			
			try {
				Thread.sleep(service.getInterval() * 1000);
			} catch (InterruptedException e) {
				logger.error("Error when Thread.sleep() on {}: {}", url, e.getMessage());
			}
			
		}
		
	}

	private void validateRequiredFields() {
		Objects.requireNonNull(service, MSG_SERVICE_IS_REQUIRED);
		Objects.requireNonNull(service.getName(), MSG_SERVICE_NAME_IS_REQUIRED);
		Objects.requireNonNull(service.getUrl(), MSG_SERVICE_URL_IS_REQUIRED);
		Objects.requireNonNull(service.getExpectedResultCode(), MSG_EXPECTED_RESULT_CODE_IS_REQUIRED);
		Objects.requireNonNull(service.getInterval(), MSG_INTERVAL_IS_REQUIRED);
	}

}
