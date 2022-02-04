package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Clase exception not found de usuario
 * @author Usuario
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6734027569391630482L;
	
	public UsuarioNotFoundException(String user) {
		super("No se pudo encontrar el usuario con el nombre de usuario " + user);
	}
}
