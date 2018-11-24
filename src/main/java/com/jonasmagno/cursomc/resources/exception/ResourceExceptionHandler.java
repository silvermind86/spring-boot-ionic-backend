package com.jonasmagno.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jonasmagno.cursomc.services.exception.DataIntegrityException;
import com.jonasmagno.cursomc.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request){
		
		 StandardError se = new StandardError(
			 HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(se);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrit(ObjectNotFoundException exception, HttpServletRequest request){
		
		 StandardError se = new StandardError(
			 HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(se);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException exception, HttpServletRequest request){
		
		 ValidationError ve = new ValidationError(
			 HttpStatus.NOT_FOUND.value(), "Erro de validação", System.currentTimeMillis());
		 
		 for(FieldError fe: exception.getBindingResult().getFieldErrors()) {
			 ve.addError(fe.getField(), fe.getDefaultMessage());
		 }
		 
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ve);
	}
}
