package base.web.controller.rest;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import base.web.controller.DelayService;
import base.web.dto.RequestDTO;

@RestController
@RequestMapping("/api")
public class APIController {
	
	private Logger logger = LoggerFactory.getLogger(APIController.class);
	
	@Autowired
	private DelayService delayService;
	
    @GetMapping
	@RequestMapping("/delay/{time}")
    @ResponseBody
	public String processDelay(@PathVariable Long time) {
    	
    	logger.info("Request received {}.", time);
		String delayProcessed = delayService.processDelay(time);
		logger.info("Processed delay {}: {}.", time, delayProcessed);
    	
		return "Done";
	}

    @GetMapping("/binding")
    @ResponseBody
	public ResponseEntity<?> processBinder(@RequestBody RequestDTO dto) {
    	
    	Assert.notNull(dto, "DTO object is required.");
    	Assert.hasText(dto.getText(), "Text is required.");
    	
    	logger.info("Request received for {}.", dto);
    	
		return new ResponseEntity<>("Text: "+ dto.getText(), HttpStatus.OK);
	}
    
    @InitBinder
    public void initBind(WebDataBinder binder) {
    	
    	logger.info("Binding on Controller: {}", binder.getTarget());
    	binder.registerCustomEditor(String.class, new PropertyEditorSupport() {

			@Override
			public Object getValue() {
				logger.info("getValue(): {}", getValue());
				return super.getValue();
			}

			@Override
			public String getAsText() {
				logger.info("getAsText(): {}", getAsText());
				return super.getAsText();
			}

			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				super.setAsText(text);
				logger.info("setAsText(): {}", text);
			}
    		
    	});
    }

}