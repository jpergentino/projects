package base.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {
	
	private Logger logger = LoggerFactory.getLogger(CustomErrorController.class);
	
    @GetMapping({"", "/"})
    public String handleError(Model model, HttpServletRequest request) throws Exception {
    	
    	Object errorStatus = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    	Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
    	Object errorException = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

    	String message = "Error "+ errorStatus +" "+ errorMessage;
    	
    	if (errorStatus != null) {
            Integer statusCode = Integer.valueOf(errorStatus.toString());
        
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
            	Object errorRequest = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
            	message = "Sorry, request not found: "+ errorRequest;
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            	message = "Unexpected error.";
            }
        }
    	
    	logger.error("Error {}: {} - {}", errorStatus, errorMessage, errorException);
    	
    	model.addAttribute("errorStatus", errorStatus);
    	model.addAttribute("errorMessage", message);

        return "error";
    }

}