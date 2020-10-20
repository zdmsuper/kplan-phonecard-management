package com.kplan.phonecard.exceptions;

public class BusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BusinessException(String msg) {
		super(msg);
	}
	public BusinessException(Throwable t) {
		super(t);
	}
}
