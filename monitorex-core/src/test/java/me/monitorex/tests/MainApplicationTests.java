package me.monitorex.tests;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.monitorex.MonitorCoreApplication;

@ExtendWith(MockitoExtension.class)
class MainApplicationTests {
	
	@Mock
	private File file;
	
	@Test
	void insstance() throws IOException {
		new MonitorCoreApplication();
	}

	@Test
	void validMain() throws IOException, URISyntaxException {
		MonitorCoreApplication.main(new String[]{});
	}
	
	@Test
	void mainFileDoesntExistTest() throws IOException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> MonitorCoreApplication.main(new String[]{"file-"+ new Random().nextLong() +".json"}));
	}
	
	@Test
	void tooManyArgsTest() throws IOException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> MonitorCoreApplication.main(new String[]{"param1", "param2"}));
	}
	
	@Test
	void nullArgsTest() throws IOException, URISyntaxException {
		MonitorCoreApplication.main(null);
	}
	
}