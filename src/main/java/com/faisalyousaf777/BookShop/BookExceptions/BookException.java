package com.faisalyousaf777.BookShop.BookExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class BookException {
	
	private final String message;
	private final ZonedDateTime timeStamp;
	
}
