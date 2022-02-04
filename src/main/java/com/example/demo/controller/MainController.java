package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.error.LineaPedidoNotFoundException;
import com.example.demo.error.PedidoNotFoundException;
import com.example.demo.error.UsuarioNotFoundException;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.ProductoPedido;
import com.example.demo.model.Usuario;
import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;

/**
 * Clase controlador de la API
 * @author usuario
 *
 */
@RestController
public class MainController {

	@Autowired 
	private UsuarioService servicioUsuario;
	@Autowired
	private ProductoService servicioProducto;
	@Autowired
	private PedidoService servicioPedido;
	
	
	
	
	/**
	 * Metodo que devuelve un usuario pasandole un nombre de usuario
	 * @param user Nombre de usuario
	 * @return Usuario con dicho nombre de usuario
	 */
	@GetMapping("/usuario/{user}")
	public Usuario findByUser(@PathVariable String user) {
		Usuario resultado = this.servicioUsuario.obtenerUsuario(user);
		if (resultado == null) {
			throw new UsuarioNotFoundException(user);
		}
		return resultado;
	}
	
	/**
	 * Metodo que devuelve todos los productos
	 * @return Lista de productos
	 */
	@GetMapping("/producto")
	public List<Producto> findAll(){
		return this.servicioProducto.findAll();
	}
	
	/**
	 * Metodo que borra un pedido mediante su numero de referencia
	 * @param ref Numero de referencia del pedido
	 * @return El pedido borrado
	 */
	@DeleteMapping("/pedido/{ref}")
	public Pedido delete(@PathVariable long ref){
		Pedido p = this.servicioPedido.obtenerPedidoPorReferencia(ref);
		if (p == null) {
			throw new PedidoNotFoundException(ref);
		}
		Pedido resultado = this.servicioPedido.borrarPedido(ref);
		return resultado;
	}
	
	/**
	 * Metodo para añadir un pedido
	 * @param ped Pedido que se desea añadir
	 * @return El pedido añadido
	 */
	@PostMapping("/pedido")
	public Pedido add(@RequestBody Pedido ped){
		return this.servicioPedido.add(ped);
	}
	
	/**
	 * Metodo para modificar un pedido mediante su numero de referencia
	 * @param pedido El nuevo pedido editado
	 * @param ref El numero de referencia del pedido qeu se desea modificar
	 * @return El pedido modificado
	 */
	@PutMapping("/pedido/{ref}")
	public Pedido edit(@RequestBody Pedido pedido, @PathVariable long ref){
		return this.servicioPedido.editarPedido(pedido,ref);
	}
	
	/**
	 * Metodo para obtener un pedido mediante su numero de referencia
	 * @param ref El numero de referencia del pedido a obtener
	 * @return EL pedido que se ha obtenido desde el numero de referencia
	 */
	@GetMapping("/pedido/{ref}")
	public Pedido get(@PathVariable long ref){
		Pedido resultado = this.servicioPedido.obtenerPedidoPorReferencia(ref);
		if (resultado == null) {
			throw new PedidoNotFoundException(ref);
		}
		return resultado;
	}
	
	/**
	 * Metodo que devuelve una lista de pedidos
	 * @return Lista de pedidos
	 */
	@GetMapping("/pedido")
	public List<Pedido> getPedidos(){
		return this.servicioPedido.findAll();
	}
	
	/**
	 * Metodo para obtener todas las lineas de un pedido 
	 * @param ref Numero de referencia del pedido
	 * @return Lista de lineas de pedido
	 */
	@GetMapping("/pedido/{ref}/lineaPedido")
	public List<ProductoPedido> getLineasPedido(@PathVariable long ref){
		List<ProductoPedido> resultado = this.servicioPedido.getLineasPedido(ref);
		if (resultado == null) {
			throw new PedidoNotFoundException(ref);
		}
		return resultado;
	}
	
	/**
	 * Metodo para obtener una linea de pedido mediante su id
	 * @param ref Numero de referencia del pedido
	 * @param id Id de la linea de pedido
	 * @return La linea de pedido obtenida
	 */
	@GetMapping("/pedido/{ref}/lineaPedido/{id}")
	public ProductoPedido getLineaPedido(@PathVariable long ref, @PathVariable long id){
		Pedido pedido = this.servicioPedido.obtenerPedidoPorReferencia(ref);
		if (pedido == null) {
			throw new PedidoNotFoundException(ref);
		}
		ProductoPedido resultado = this.servicioPedido.getLineaPedido(ref,id);
		if (resultado == null) {
			throw new LineaPedidoNotFoundException(id);
		}
		return resultado;
	}
	
	/**
	 * Metodo para añadir una linea de pedido
	 * @param ref Numero de referencia del pedido
	 * @param linea Linea de pedido que se va a añadir
	 * @return La linea de pedido añadida
	 */
	@PostMapping("/pedido/{ref}/lineaPedido")
	public ProductoPedido addLineaPedido(@PathVariable long ref, @RequestBody ProductoPedido linea){
		return this.servicioPedido.anadirLineaPedido(ref, linea);
	}
	
	/**
	 * Metodo para borrar una linea de pedido
	 * @param ref Numero de referencia del pedido
	 * @param id id de la linea de pedido que se va a borrar
	 * @return La linea borrada
	 */
	@DeleteMapping("/pedido/{ref}/lineaPedido/{id}")
	public ProductoPedido deleteLineaPedido(@PathVariable long ref, @PathVariable long id) {
		Pedido pedido = this.servicioPedido.obtenerPedidoPorReferencia(ref);
		if (pedido == null) {
			throw new PedidoNotFoundException(ref);

		}
		ProductoPedido resultado = this.servicioPedido.getLineaPedido(ref, id);
		if (resultado == null) {
			throw new LineaPedidoNotFoundException(id);
		}
		return this.servicioPedido.borrarLineaPedido(ref, id);
	}
	
	/**
	 * Metodo para modificar una linea de pedido
	 * @param ref Numero de referencia del pedido
	 * @param linea La nueva linea modificada
	 * @param id El id de la linea que se va a modificar
	 * @return La linea modificada
	 */
	@PutMapping("/pedido/{ref}/lineaPedido/{id}")
	public ProductoPedido editLineaPedido(@PathVariable long ref, @RequestBody ProductoPedido linea, @PathVariable long id) {
		Pedido pedido = this.servicioPedido.obtenerPedidoPorReferencia(ref);
		if (pedido == null) {
			throw new PedidoNotFoundException(ref);
		}
		return this.servicioPedido.editarLineaPedido(ref, linea, id);
	}
	
}
