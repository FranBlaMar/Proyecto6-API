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
 * 
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
	 * 
	 * @param user
	 * @return
	 */
	@GetMapping("/usuario/{user}")
	public Usuario findByUser(@PathVariable String user) {
		Usuario resultado = this.servicioUsuario.obtenerUsuario(user);
		if (resultado == null) {
			throw new UsuarioNotFoundException(user);
		} else {
			return resultado;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/producto")
	public List<Producto> findAll(){
		return this.servicioProducto.findAll();
	}
	
	/**
	 * 
	 * @param ref
	 * @return
	 */
	@DeleteMapping("/pedido/{ref}")
	public Pedido delete(@PathVariable long ref){
		Pedido resultado = this.servicioPedido.borrarPedido(ref);
		if (resultado == null) {
			throw new PedidoNotFoundException(ref);
		} else {
			return resultado;
		}
	}
	
	/**
	 * 
	 * @param ped
	 * @param user
	 * @return
	 */
	@PostMapping("/pedido")
	public Pedido add(@RequestBody Pedido ped){
		return this.servicioPedido.add(ped);
	}
	
	/**
	 * 
	 * @param pedido
	 * @param ref
	 * @return
	 */
	@PutMapping("/pedido/{ref}")
	public Pedido edit(@RequestBody Pedido pedido, @PathVariable long ref){
		return this.servicioPedido.editarPedido(pedido,ref);
	}
	
	/**
	 * 
	 * @param ref
	 * @return
	 */
	@GetMapping("/pedido/{ref}")
	public Pedido get(@PathVariable long ref){
		Pedido resultado = this.servicioPedido.obtenerPedidoPorReferencia(ref);
		if (resultado == null) {
			throw new PedidoNotFoundException(ref);
		} else {
			return resultado;
		}
	}
	
	/**
	 * 
	 * @param ref
	 * @return
	 */
	@GetMapping("/pedido")
	public List<Pedido> getPedidos(){
		return this.servicioPedido.findAll();
	}
	
	/**
	 * 
	 * @param ref
	 * @return
	 */
	@GetMapping("/pedido/{ref}/lineaPedido")
	public List<ProductoPedido> getLineasPedido(@PathVariable long ref){
		List<ProductoPedido> resultado = this.servicioPedido.getLineasPedido(ref);
		if (resultado == null) {
			throw new PedidoNotFoundException(ref);
		} else {
			return resultado;
		}
	}
	
	/**
	 * 
	 * @param ref
	 * @param linea
	 * @return
	 */
	@PostMapping("/pedido/{ref}/lineaPedido")
	public ProductoPedido addLineaPedido(@PathVariable long ref, @RequestBody ProductoPedido linea){
		return this.servicioPedido.anadirLineaPedido(ref, linea);
	}
	
	/**
	 * 
	 * @param ref
	 * @param id
	 * @return
	 */
	@DeleteMapping("/pedido/{ref}/lineaPedido/{id}")
	public ProductoPedido deleteLineaPedido(@PathVariable long ref, @PathVariable long id) {
		return this.servicioPedido.borrarLineaPedido(ref,id);
	}
	
}
