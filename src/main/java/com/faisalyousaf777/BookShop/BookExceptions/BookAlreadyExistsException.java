package com.faisalyousaf777.BookShop.BookExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BookAlreadyExistsException extends RuntimeException{
	public BookAlreadyExistsException(String message) {
		super(message);
	}
	
	public BookAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
