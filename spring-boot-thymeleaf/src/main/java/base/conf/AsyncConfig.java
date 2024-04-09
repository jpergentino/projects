package base.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
	
	private Logger logger = LoggerFactory.getLogger(AsyncConfig.class);
    
	@Bean
	public TaskExecutor taskExecutor() {
		
		logger.info("Initializing ThreadPool...");
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(5);
		executor.setThreadNamePrefix("Bot Process-");
		executor.initialize();
		return executor;
	}
	
}