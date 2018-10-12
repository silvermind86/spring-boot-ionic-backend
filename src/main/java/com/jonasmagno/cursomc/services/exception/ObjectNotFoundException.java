package com.jonasmagno.cursomc.services.exception;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String msm) {
		super(msm);
	};
	
	public ObjectNotFoundException(String msm, Throwable e) {
		super(msm, e);
	};

}
