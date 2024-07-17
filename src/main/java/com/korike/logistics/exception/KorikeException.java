package com.korike.logistics.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class KorikeException extends RuntimeException{
	private ApiErrorCode errorCode;
	
	public KorikeException(String message) {
		super(message);
	}
	
	public KorikeException(ApiErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ApiErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ApiErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "KorikeException [errorCode=" + errorCode + ", getMessage()=" + getMessage() + "]";
	}
}
