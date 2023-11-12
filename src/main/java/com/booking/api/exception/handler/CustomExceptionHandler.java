package com.booking.api.exception.handler;

import java.util.List;
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

import com.booking.api.exception.ConflictException;
import com.booking.api.exception.NotAcceptableException;
import com.booking.api.exception.NotFoundException;
import com.booking.api.exception.StandardError;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(final Exception exception, final WebRequest request) {
		return buildExceptionResponse(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpClientErrorException.BadRequest.class)
	public final ResponseEntity<Object> handleBadRequestException(final HttpClientErrorException.BadRequest exception,
			final WebRequest request) {
		return buildExceptionResponse(exception, request, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Object> handleNotFoundException(final NotFoundException exception,
			final WebRequest request) {
		return buildExceptionResponse(exception, request, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotAcceptableException.class)
	public final ResponseEntity<Object> handleNotAcceptableException(final NotAcceptableException exception,
			final WebRequest request) {
		return buildExceptionResponse(exception, request, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(ConflictException.class)
	public final ResponseEntity<Object> handleNotAcceptableException(final ConflictException exception,
			final WebRequest request) {
		return buildExceptionResponse(exception, request, HttpStatus.CONFLICT);
	}

	private ResponseEntity<Object> buildExceptionResponse(final Exception exception, WebRequest request,
			HttpStatus httpStatus) {

		return buildExceptionResponse(exception, request, httpStatus, Optional.empty());
	}

	private ResponseEntity<Object> buildExceptionResponse(final Throwable exception, WebRequest request,
			HttpStatus httpStatus, Optional<List<String>> details) {

		final String errorMessage = Optional.ofNullable(exception.getMessage()).orElse("Unexpected error");

		return new ResponseEntity<>(StandardError.builder().code(httpStatus.value()).message(errorMessage).build(),
				httpStatus);

	}
}
