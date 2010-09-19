package com.exdriven.shopping;

public class ShopperException extends RuntimeException {
	public ShopperException(String message, Exception cause) {
		super(message, cause);
	}
}