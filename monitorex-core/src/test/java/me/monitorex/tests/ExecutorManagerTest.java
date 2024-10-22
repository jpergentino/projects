package me.monitorex.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import me.monitorex.manager.ExecutorManager;
import me.monitorex.util.JSONUtil;

class ExecutorManagerTest {

	String VALID_JSON_FILE = "services.json";

	String VALID_JSON_STRING = "[ " + "	{ " + "		\"name\": \"Google\", "
			+ "		\"url\" : \"http://www.google.com\", " + "		\"port\": 80, " + "	} " + "]";

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
		Assertions.assertThrows(IOException.class,
				() -> new ExecutorManager(Paths.get(new Random().nextLong() + VALID_JSON_FILE).toFile()).run());
	}

	@Test
	void testRunThrowsIOException() throws StreamReadException, DatabindException, IOException {
		
		File jsonFile =  mock(File.class);
		executorManager = new ExecutorManager(jsonFile);
		
		when(JSONUtil.validateJSONFromFile(jsonFile)).thenThrow(IOException.class);

		// Act & Assert
		assertThrows(IOException.class, () -> executorManager.run());
	}

}
