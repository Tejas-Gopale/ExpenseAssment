package com.alephys.expensetracker.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;

import lombok.Data;

@Data
public class ApiError {

	private LocalDateTime timeStamp;
	private String error;
	private HttpStatusCode statusCode;
	
	public ApiError() {
		this.timeStamp = LocalDateTime.now();
	}
	
	public ApiError(String error , HttpStatusCode statusCode) {
		this();
		this.error = error;
		this.statusCode = statusCode;
	}
}
