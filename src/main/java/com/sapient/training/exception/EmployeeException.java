package com.sapient.training.exception;

public class EmployeeException extends Exception{	
	private static final long serialVersionUID = 1L;
	
	public EmployeeException() {
		super();
	}
	
	public EmployeeException(String message) {
		super(message);
	}
	
	public EmployeeException(String message,Throwable e) {
		super(message);
	}

	@Override
	public String getMessage() {		
		return super.getMessage();
	}
	
	

}
