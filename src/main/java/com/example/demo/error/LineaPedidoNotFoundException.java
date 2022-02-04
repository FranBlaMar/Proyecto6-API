package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Clase exception not found de linea pedido
 * @author Usuario
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class LineaPedidoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6734027569391630482L;
	
	public LineaPedidoNotFoundException(Long id) {
		super("No se pudo encontrar la linea de pedido con el id " + id);
	}
}
