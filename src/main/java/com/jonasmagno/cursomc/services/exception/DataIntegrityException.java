package com.jonasmagno.cursomc.services.exception;

public class DataIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msm) {
		super(msm);
	};
	
	public DataIntegrityException(String msm, Throwable e) {
		super(msm, e);
	};

}
