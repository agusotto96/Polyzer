package app.presentation;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import app.model.Polymer.InvalidSequenceException;
import app.service.interfaces.PolymerFactory.InvalidTypeException;

@ControllerAdvice
public class ControllerAdvicer extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ InvalidSequenceException.class, InvalidTypeException.class })
	protected ResponseEntity<Object> handleBadRequestExceptions(RuntimeException ex, ServletWebRequest request) {

		Map<String, Object> response = new LinkedHashMap<>();

		response.put("timestamp", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		response.put("status", 400);
		response.put("error", "Bad Request");
		response.put("message", ex.getMessage());
		response.put("path", request.getRequest().getRequestURI());

		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

	}

}
