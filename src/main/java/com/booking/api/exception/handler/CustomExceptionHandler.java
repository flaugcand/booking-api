package com.booking.api.exception.handler;

import java.util.Optional;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.booking.api.exception.NotAcceptableException;
import com.booking.api.exception.NotFoundException;
import com.booking.api.exception.StandardError;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(final Exception exception, final WebRequest request) {
		final String errorMessage = Optional.ofNullable(exception.getMessage()).orElse("Unexpected error");
		return new ResponseEntity<>(
				StandardError.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(errorMessage).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpClientErrorException.BadRequest.class)
	public final ResponseEntity<Object> handleBadRequestException(final HttpClientErrorException.BadRequest exception,
			final WebRequest request) {
		final String errorMessage = Optional.ofNullable(exception.getMessage()).orElse("Unexpected error");
		return new ResponseEntity<>(
				StandardError.builder().code(HttpStatus.BAD_REQUEST.value()).message(errorMessage).build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Object> handleNotFoundException(final NotFoundException exception,
			final WebRequest request) {
		final String errorMessage = Optional.ofNullable(exception.getMessage()).orElse("Unexpected error");
		return new ResponseEntity<>(
				StandardError.builder().code(HttpStatus.NOT_FOUND.value()).message(errorMessage).build(),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotAcceptableException.class)
	public final ResponseEntity<Object> handleNotAcceptableException(final NotAcceptableException exception,
			final WebRequest request) {
		final String errorMessage = Optional.ofNullable(exception.getMessage()).orElse("Unexpected error");
		return new ResponseEntity<>(
				StandardError.builder().code(HttpStatus.NOT_ACCEPTABLE.value()).message(errorMessage).build(),
				HttpStatus.NOT_ACCEPTABLE);
	}

}
