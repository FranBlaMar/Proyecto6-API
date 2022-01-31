package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase Producto
 * @author Usuario
 *
 */
@Entity
@Table(name = "Producto")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "imagen", nullable = false)
	private String imagen;
	
	@Column(name = "precio", nullable = false)
	private double precio;
	
	
	public Producto() {	
	}
	
	/**
	 * Constructor de la clase productos
	 * @param nombre del producto
	 * @param imagen ruta de la imagen del producto
	 * @param precio del producto
	 */
	public Producto(String nombre, String imagen, double precio) {
		super();
		this.nombre = nombre;
		this.imagen = imagen;
		this.precio = precio;
	}

	
	//geters y seters
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	
	//Hashcode y equals
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return id == other.id;
	}

	
}
