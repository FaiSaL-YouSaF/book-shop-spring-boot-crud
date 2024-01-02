package com.faisalyousaf777.BookShop.BookExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT)
public class BookRecordsNotFoundException extends RuntimeException {
	
	public BookRecordsNotFoundException(String message) {
		super(message);
	}
	
	public BookRecordsNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
