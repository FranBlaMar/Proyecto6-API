package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * Clase usuario
 * @author Usuario
 *
 */
@Entity
@Table(name = "Usuario")
public class Usuario {
	
	@Id
	@NotEmpty
	private String user;
	@NotEmpty
	@Column(name = "contrasena", nullable = false)
	private String contrasena;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "telefono", nullable = false)
	private String telefono;
	@Column(name = "direccion", nullable = false)
	private String direccion;

	public Usuario() {}
	
	/**
	 * Constructor de la clase usuario
	 * @param user
	 * @param contrasena
	 * @param email
	 * @param nombre
	 * @param telefono
	 * @param direccion
	 */
	public Usuario( String user,String contrasena, String email, String nombre, String telefono, String direccion) {
		this.user = user;
		this.contrasena = contrasena;
		this.email = email;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	/**
	 * Constructor de la clase usuario con solo username y contraseña
	 * @param user
	 * @param contrasena
	 */
	public Usuario(String user, String contrasena) {
		this.user=user;
		this.contrasena = contrasena;
	}

	
	//geters y seters
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
	//hashcode y equals
	@Override
	public int hashCode() {
		return Objects.hash(contrasena, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(user, other.user);
	}


}
