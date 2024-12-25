package com.faisalyousaf777.BookStore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException{

	public BookNotFoundException(String message) {
		super(message);
	}
	
	public BookNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
