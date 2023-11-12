package com.booking.api.exception;

import java.io.Serial;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Not Found")
public class NotAcceptableException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public NotAcceptableException(String message) {
		super(message);
	}

}
