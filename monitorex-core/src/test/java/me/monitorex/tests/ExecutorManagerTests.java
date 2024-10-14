package me.monitorex.tests;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import me.monitorex.manager.ExecutorManager;
import me.monitorex.util.JSONUtil;

class ExecutorManagerTests {
	
	String VALID_JSON_FILE = "services.json";
			
	String VALID_JSON_STRING = "[ "
			+ "	{ "
			+ "		\"name\": \"Google\", "
			+ "		\"url\" : \"http://www.google.com\", "
			+ "		\"port\": 80, "
			+ "	} "
			+ "]";
	
	@Mock
	private ExecutorManager executorManager;

	@Test
	void instanceNull() throws IOException {
		new ExecutorManager(null);
		new JSONUtil();
	}

	@Test
	void instanceValidJSON() {
		new ExecutorManager(Paths.get(VALID_JSON_FILE).toFile());
	}
	
	@Test
	void managerRun() {
		new ExecutorManager(Paths.get(VALID_JSON_FILE).toFile()).run();
	}
	
	@Test
	void invalidJson() {
		Assertions.assertThrows(RuntimeException.class,
				() -> new ExecutorManager(Paths.get(new Random().nextLong() + VALID_JSON_FILE).toFile()).run());
	}
	
}
