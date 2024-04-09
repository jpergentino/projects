package base;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.StringUtils;

import base.web.controller.DelayService;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = StartApplication.class)

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-junit.properties")
public class ApplicationTests {
	
	private static String baseUrl;
	
	@Value(value="${local.server.port}")
	private int port;
	
	@Value(value="${server.tomcat.threads.max}")
	private int maxThreads;
	
	private static Logger logger = LoggerFactory.getLogger(ApplicationTests.class);
	
	@BeforeEach
	public void init() {
		baseUrl = "http://localhost:" + port;
	}
	
	@Autowired
	private DelayService delayService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testAutowired() {
		logger.debug("Port: {}.", port);
		logger.debug("Max Threads: {}.", maxThreads);
		assertTrue(port != 0);
		assertTrue(maxThreads != 0);
		assertNotNull(delayService);
	}
	
	@Test
	void testintWebThreads() throws URISyntaxException, IOException, InterruptedException {

		String url = baseUrl + "/api/delay/3";
		
		ExecutorService threadPool = Executors.newFixedThreadPool(1000);
		
		for (int i = 0; i < 1000; i++) {
			
			threadPool.submit(() -> {
//				logger.debug("Requesting {}.", url);
				
				String body = null;
				
				try {
					HttpRequest request = HttpRequest.newBuilder()
							.uri(new URI(url))
							.GET()
							.timeout(Duration.ofSeconds(60))
							.build();
					
					HttpClient client = HttpClient.newHttpClient();
					
					HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
					body = response.body();
					
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				
				
				
//				String response = restTemplate.getForObject(url, String.class);
				assertTrue(StringUtils.hasText(body));
				assertEquals(body, "Done");
				logger.debug("Response: {}.", body);
			});
			
		}
		
		
		threadPool.shutdown();
		
		while(!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
			logger.info("Waiting...");
			Thread.sleep(1 * 1000);
		}
		

		logger.debug("End of test method.");
		
	}

}
