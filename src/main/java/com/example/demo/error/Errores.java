package com.example.demo.error;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.springframework.http.HttpStatus;


/**
 * Clase que controla las excepciones
 * @author Usuario
 *
 */
public class Errores {
	
	private HttpStatus estadoPeticion;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fecha;
	private String mensajeDeError;
	
	public HttpStatus getEstadoPeticion() {
		return estadoPeticion;
	}
	
	public void setEstadoPeticion(HttpStatus estado) {
		this.estadoPeticion = estado;
	}
	
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	public String getMensajeDeError() {
		return mensajeDeError;
	}
	
	public void setMensajeDeError(String mensaje) {
		this.mensajeDeError = mensaje;
	}
}
