package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.model.Pedido;
import com.example.demo.model.ProductoPedido;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoPedidoRepository;
import com.example.demo.repository.ProductoRepository;

/**
 * Clase servicio de pedidos
 * @author Usuario
 *
 */
@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repositorio;
	@Autowired
	private ProductoRepository repositorioProducto;
	@Autowired
	private ProductoPedidoRepository repositorioLinea;	
	
	/**
	 * Metodo para obtener todos los pedidos
	 * @return La lista con todos los pedidos almacenados en el servidor
	 */
	public List<Pedido> findAll() {
		return repositorio.findAll();
	}
	
	/**
	 * 
	 * @param pedido
	 * @param user
	 * @return
	 */
	public Pedido add(Pedido pedido) {
		this.repositorio.save(pedido);
		return pedido;
	}
	
	/**
	 * Metodo para obtener las lineas de pedido de un pedido en específico
	 * @param ref Numero de referencia del pedido
	 * @return Lista de lineas de pedido 
	 */
	public List<ProductoPedido> getLineasPedido (long ref) {
		List<ProductoPedido> resultado;
		if(this.obtenerPedidoPorReferencia(ref) == null) {
			resultado = null;
		}
		else {
			resultado = this.obtenerPedidoPorReferencia(ref).getProductos();
		}
		return resultado;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ProductoPedido getLineaPedido (long ref, long id) {
		Pedido pedido = obtenerPedidoPorReferencia(ref);
		ProductoPedido resultado = null;
		for(ProductoPedido linea : pedido.getProductos()) {
			if (linea.getIdLinea().equals(id)) {
				resultado = linea;
			}
		}
		return resultado;
	}

	/**
	 * Metodo para borrar pedidos de la base de datos
	 * @param refe
	 */
	 
	public Pedido borrarPedido(long refe) {
		Pedido p = this.repositorio.findById(refe).get();
		this.repositorio.deleteById(refe);
		return p;
	}
	
	/**
	 * Metodo para obtener un pedido de la lista de pedidos mediante un numero de referencia
	 * @param long El id de referencia del pedido que deseamos obtener
	 * @return Pedido que estamos buscando
	 */
	public Pedido obtenerPedidoPorReferencia(long referencia){
		return repositorio.findById(referencia).orElse(null);
	}
	
	/**
	 * 
	 * @param pedido
	 * @param referencia
	 * @return
	 */
	public Pedido editarPedido(Pedido pedido, Long referencia) {
		if (this.repositorio.findById(referencia).orElse(null) == null) {
			this.add(pedido);
		}
		else {
			pedido.setReferencia(referencia);
			this.add(pedido);
		}
		return pedido;
	}
	
	/**
	 * 
	 * @param ref
	 * @param linea
	 * @return
	 */
	public ProductoPedido anadirLineaPedido(long ref, ProductoPedido linea) {
		ProductoPedido resultado = linea;
		if(this.obtenerPedidoPorReferencia(ref) == null) {
			resultado = null;
		}
		else {
			Pedido pedido = obtenerPedidoPorReferencia(ref);
			long idProducto = linea.getProducto().getId();
			linea.setProducto(this.repositorioProducto.findById(idProducto).get());
			pedido.addProductos(linea);
			pedido.setPrecioTotal(this.calcularPrecioTotal(pedido));
			this.add(pedido);
		}
		return resultado;
	}
	
	/**
	 * 
	 * @param linea
	 * @param ref
	 * @return
	 */
	public double calcularPrecioTotal(Pedido pedido) {
		double precioTotal = pedido.getPrecioTotal();
		for(ProductoPedido linea: pedido.getProductos()) {
			precioTotal += linea.getCantidad() * linea.getProducto().getPrecio();
		}
		return precioTotal;
	}
	
	
	/**
	 * 
	 * @param ref
	 * @param id
	 * @return
	 */
	public ProductoPedido borrarLineaPedido(Long ref, Long id) {
		ProductoPedido linea = getLineaPedido(ref,id);
		Pedido pedido = obtenerPedidoPorReferencia(ref);
		List<ProductoPedido> lineasPedido = pedido.getProductos();
		for (ProductoPedido item : lineasPedido) {
			if (item.getIdLinea().equals(id)) {
				double cantidadARestar = item.getProducto().getPrecio() * item.getCantidad();
				pedido.setPrecioTotal(pedido.getPrecioTotal() - cantidadARestar);
			}
		}
		lineasPedido.remove(linea);
		pedido.setProductos(lineasPedido);
		add(pedido);
		this.repositorioLinea.delete(linea);
		return linea;
	}
	
	/**
	 * 
	 * @param ref
	 * @param linea
	 * @param id
	 * @return
	 */
	public ProductoPedido editarLineaPedido (long ref, ProductoPedido linea, long id) {
		long idProducto = linea.getProducto().getId();
		linea.setProducto(this.repositorioProducto.findById(idProducto).get());
		linea.setIdLinea(id);
		
		Pedido pedido = obtenerPedidoPorReferencia(ref);
		pedido.getProductos().remove(linea);
		pedido.getProductos().add(linea);
		pedido.setPrecioTotal(calcularPrecioTotal(pedido));
		add(pedido);
		this.repositorioLinea.save(linea);
		return linea;
	}

}