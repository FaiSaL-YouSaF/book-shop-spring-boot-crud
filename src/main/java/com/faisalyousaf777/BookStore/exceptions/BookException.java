package com.faisalyousaf777.BookStore.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class BookException {
	
	private final String message;
	private final ZonedDateTime timeStamp;
	
}
