package base.web.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import base.web.controller.rest.APIController;

@Service
public class DelayService {

	private Logger logger = LoggerFactory.getLogger(APIController.class);

	public String processDelay(Long time) {
		
		logger.info("Server waiting {} seconds...", time);
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("Server done waiting {} seconds...", time);
		
		return Calendar.getInstance().getTime().toString();
	}


}
