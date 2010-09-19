package com.exdriven.date;

public class NotAValidBusinessDayException extends RuntimeException {
	public NotAValidBusinessDayException(String message) {
		super(message);
	}
}
