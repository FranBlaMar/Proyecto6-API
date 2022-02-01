package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.ProductoPedido;
import com.example.demo.model.Usuario;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoPedidoRepository;

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
	 * @return
	 */
	public Pedido save(Pedido pedido) {
		return repositorio.save(pedido);
	}
	
	/**
	 * 
	 * @param pedido
	 * @param user
	 * @return
	 */
	public Pedido add(Pedido pedido) {
		return this.save(pedido);
	}
	
	/**
	 * Metodo para obtener las lineas de pedido de un pedido en específico
	 * @param ref Numero de referencia del pedido
	 * @return Lista de lineas de pedido 
	 */
	public List<ProductoPedido> getLineasPedido (long ref) {
		return this.obtenerPedidoPorReferencia(ref).getProductos();
	}
	
	
	/**
	 * Metodo para añadir la linea de pedido a un pedido
	 * @param pedido Pedido al que vamos a añadirle las lineas
	 * @param productoPedido La linea que queremos añadir
	 * @return Linea de pedido añadida
	 */
	public ProductoPedido anadirLineaPedido(long ref,ProductoPedido productoPedido) {
		Pedido pedido = this.obtenerPedidoPorReferencia(ref);
		pedido.addProductos(productoPedido);
		this.save(pedido);
		return productoPedido;
	}

	/**
	 * Metodo para eliminar una linea de un pedido en la base de datos
	 * @param id Id de la linea de pedido
	 * @return La linea de pedido añadida
	 */
	public ProductoPedido eliminarLineaPedido(long ref, long id) {
		Pedido pedido = this.obtenerPedidoPorReferencia(ref);
		List <ProductoPedido> lineas = pedido.getProductos();
		ProductoPedido productoPedido = this.repositorioLinea.getById(id);
		lineas.remove(productoPedido);
		this.repositorioLinea.deleteById(id);
		this.save(pedido);
		return productoPedido;
	}

	/**
	 * Metodo para borrar pedidos de la base de datos
	 * @param refe
	 */
	 
	public Pedido borrarPedido(long refe) {
		Pedido p = this.repositorio.getById(refe);
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

}