package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PedidoNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6734027569391630482L;
	
	public PedidoNotFoundException(Long ref) {
		super("No se pudo encontrar el pedido con la referencia " + ref);
	}

}