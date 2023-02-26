package base.web.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
	
	private Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Value("${app.name}")
	private String appName;
	
	@PostConstruct
	public void postConstruct() {
		logger.info("App Name: {}", appName);
		logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
	}
	
	@GetMapping({ "/", "" })
	@Secured("USER")
	public String requestIndex(Model model) {
		model.addAttribute("message", "Hello!");
		return "index";
	}

}
