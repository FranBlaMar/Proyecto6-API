package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase pedido
 * @author Usuario
 *
 */
@Entity
@Table(name = "Pedido")
public class Pedido {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long referencia;
	
	@ManyToOne
	@JoinColumn(name="usuario_pedido")
	private Usuario usuarioPedido;
	
	@OneToMany (cascade=CascadeType.ALL, orphanRemoval = true)
	private List<ProductoPedido> productos =new ArrayList<> ();
	
	@Column(name = "fechaPedido", nullable = false)
	private LocalDate fechaPedido;
	@Column(name = "direccion", nullable = false)
	private String direccion;
	@Column(name = "telefono", nullable = false)
	private String telefono;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "tipoEnvio")
	private String tipoEnvio;
	@Column(name = "precioTotal", nullable = false)
	private double precioTotal;
	
	/**
	 * Constructor de la clase Pedido
	 * @param usuarioPedido del pedido
	 * @param direccion del pedido
	 * @param telefono del usuario del pedido
	 * @param email del usuario del pedido
	 */
	public Pedido(Usuario usuarioPedido, String direccion, String telefono, String email) {
		super();
		this.usuarioPedido = usuarioPedido;
		this.fechaPedido = LocalDate.now();
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
	}

	public Pedido() {
		this.fechaPedido = LocalDate.now();
	}
	
	
	//Geters y seters
	public long getReferencia() {
		return referencia;
	}


	public List<ProductoPedido> getProductos() {
		return productos;
	}
	
	public void setProductos (List<ProductoPedido> productos) {
		this.productos= productos;
	}
	public void addProductos(ProductoPedido pp) {
		this.productos.add(pp);
	}
	
	public Usuario getUsuarioPedido() {
		return usuarioPedido;
	}

	public void setUsuarioPedido(Usuario usuarioPedido) {
		this.usuarioPedido = usuarioPedido;
	}
	
	
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	public LocalDate getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDate fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	
	public String getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	
	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	
	//Hashcode y equals
	@Override
	public int hashCode() {
		return Objects.hash(referencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return referencia == other.referencia;
	}
	
}
