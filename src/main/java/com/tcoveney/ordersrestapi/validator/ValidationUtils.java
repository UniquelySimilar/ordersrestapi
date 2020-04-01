package com.tcoveney.ordersrestapi.validator;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class ValidationUtils {
	private static final Logger logger = LoggerFactory.getLogger(ValidationUtils.class);

	@Autowired
	private MessageSource messageSource;
	
	public void createValidationErrorsResponse(BindingResult bindingResult, HttpServletResponse response) {
		response.setStatus(400);
		response.setContentType("application/json");
		//logger.debug(result.getAllErrors());
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

		for(FieldError fieldError : bindingResult.getFieldErrors()){
	        ObjectNode objectNode = mapper.createObjectNode();
			String message = messageSource.getMessage(fieldError.getCodes()[0], null, Locale.US);
	        objectNode.put("field", fieldError.getField());
	        objectNode.put("message", message);
	        arrayNode.add(objectNode);
			//logger.debug(fieldError.getField() + ": " + message);
		}
		// Add array of error message JSON objects to response
		try {
			response.getWriter().write(arrayNode.toString());
			response.getWriter().flush();
		}
		catch(IOException ioe) {
			logger.error("Error writing to response", ioe);
		}
	}

}
