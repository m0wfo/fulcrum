package com.mowforth.fulcrum.http;

/**
 * Exception representing some bad input.
 */
public class RequestException extends Exception {

	public RequestException(String message) {
		super(message);
	}
}
