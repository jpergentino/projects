package base.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class ConfigurationGuard implements InitializingBean {
	
	private Logger logger = LoggerFactory.getLogger(ConfigurationGuard.class);

	@Value("${spring.profiles.active}")
	private String profileActive;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		if (!StringUtils.hasText(profileActive)) {
			String message = "Required param is missing: ${spring.profiles.active}. Add the param like -Dspring.profiles.active=dev";
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
	}
    
}