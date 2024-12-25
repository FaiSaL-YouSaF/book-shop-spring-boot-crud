package com.faisalyousaf777.BookStore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalBookExceptionsHandler {
	
	@ExceptionHandler(value = {BookNotFoundException.class})
	public ResponseEntity<Object> bookNotFoundExceptionHandler(BookNotFoundException ex) {
		
		HttpStatus notFound = HttpStatus.NOT_FOUND;
		
		return new ResponseEntity<>(
				new BookException(
						ex.getMessage(),
						ZonedDateTime.now(Clock.systemDefaultZone())
				),
				notFound
		);
	}
	
	@ExceptionHandler(value = {BookRecordsNotFoundException.class})
	public ResponseEntity<Object> bookRecordsNotFoundExceptionHandler(BookRecordsNotFoundException ex) {

		HttpStatus noContent = HttpStatus.NO_CONTENT;

		return new ResponseEntity<>(
				new BookException(
						ex.getMessage(),
						ZonedDateTime.now(Clock.systemDefaultZone())
				),
				noContent
		);
	}

	@ExceptionHandler(value = {BookAlreadyExistsException.class})
	public ResponseEntity<Object> bookAlreadyExistsExceptionHandler(BookAlreadyExistsException ex) {

		HttpStatus badRequest = HttpStatus.CONFLICT;

		return new ResponseEntity<>(
				new BookException(
						ex.getMessage(),
						ZonedDateTime.now(Clock.systemDefaultZone())
				),
				badRequest
		);
	}
}
