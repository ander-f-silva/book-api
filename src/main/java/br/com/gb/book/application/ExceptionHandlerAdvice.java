package br.com.gb.book.application;

import br.com.gb.book.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ExceptionHandlerAdvice {

	private ResponseEntity<Map> error(Exception exception, HttpStatus httpStatus) {
		String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());

		Map<String, String> error = new HashMap<>();
		error.put("message", message);

		return new ResponseEntity<>(error, httpStatus);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map> exception(NotFoundException exception) {
		return error(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Map> notFound(NotFoundException exception) {
		return error(exception, HttpStatus.NOT_FOUND);
	}

}
