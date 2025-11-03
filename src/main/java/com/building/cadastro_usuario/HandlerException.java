package com.building.cadastro_usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.building.cadastro_usuario.exception.DuplicateEmailException;
import com.building.cadastro_usuario.exception.InvalidEmailFormatException;
import com.building.cadastro_usuario.exception.MissingFieldException;

import jakarta.persistence.PersistenceException;

@RestControllerAdvice
public class HandlerException {
	
	@ExceptionHandler(MissingFieldException.class)
	public ResponseEntity<String> handleMissingField(MissingFieldException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage()); // 400
	}
	
	@ExceptionHandler(InvalidEmailFormatException.class)
    public ResponseEntity<String> handleInvalidEmail(InvalidEmailFormatException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage()); // 400
    }
	
	@ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmail(DuplicateEmailException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()); // 409
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<String> handlePersistence(PersistenceException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage()); // 500
    }

    // fallback genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Erro inesperado: " + ex.getMessage());
    }
}
