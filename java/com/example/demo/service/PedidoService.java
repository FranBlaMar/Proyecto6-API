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
	@Autowired
	private ProductoService servicioProducto;
	
	
	/**
	 * Metodo para obtener todos los pedidos
	 * @return La lista con todos los pedidos almacenados en el servidor
	 */
	public List<Pedido> findAll() {
		return repositorio.findAll();
	}
	
	
	/**
	 * Metodo para almacenar pedido en la base de datos
	 * @param pedido EL pedido que se desea almacenar en la base de datos
	 * @return Pedido pedido que se ha alamcenado
	 */
	public Pedido add(Pedido pedido) {
		return repositorio.save(pedido);
	}
	
	
	/**
	 * Metodo para crear un pedido
	 * @param cantidadesProductosComprados Cantidades de los productos que ha comprado
	 * @param user Usuario que ha realizado el pedido
	 * @return El pedido creado
	 */
	public Pedido crearPedido( int[] cantidadesProductosComprados, Usuario user) {
		//Creo el pedido
		Pedido pedido = new Pedido();
		//Creo las lineas de pedido
		List<Producto> productos = this.servicioProducto.findAll();
		double precioTotal = 0;
		for (int i = 0; i < cantidadesProductosComprados.length; i++) {
			//Obtengo el precio del producto y lo multiplico por la cantidad comprada. Se suman todos los precios
			if (cantidadesProductosComprados[i] > 0) {
				precioTotal += productos.get(i).getPrecio()*cantidadesProductosComprados[i];
				//Alamcenamos las lineas de pedido
				anadirLineaPedido(pedido,new ProductoPedido(),productos.get(i),cantidadesProductosComprados[i]);
			}
		}
		//añadimos los datos necesarios del usuario al pedido
		pedido = anadirDatosUserPedido(pedido, user,  Math.round(precioTotal*100.0)/100.0);
		return this.add(pedido);
	}
	
	
	/**
	 * Metodo para añadir los datos del usuario a un pedido
	 * @param pedido Pedido al que deseamos añadir los datos del usuarios
	 * @return El pedido con los datos necesarios del pedido
	 */
	public Pedido anadirDatosUserPedido(Pedido pedido, Usuario user, double precio) {
		pedido.setDireccion(user.getDireccion());
		pedido.setEmail(user.getEmail());
		pedido.setPrecioTotal(precio);
		pedido.setTelefono(user.getTelefono());
		pedido.setUsuarioPedido(user);
		return pedido;
	}

	
	/**
	 * Metodo para añadir la linea de pedido a un pedido
	 * @param pedido Pedido al que vamos a añadirle las lineas
	 * @param productoPedido La linea que queremos añadir
	 * @param producto El producto que se ha comprado
	 * @param cantidad La cantidad comprada de dicho prducto
	 */
	public void anadirLineaPedido(Pedido pedido,ProductoPedido productoPedido, Producto producto, int cantidad) {
		productoPedido.setCantidad(cantidad);
		productoPedido.setProducto(producto);
		pedido.addProductos(productoPedido);
	}


	/**
	 * Metodo para añadir nueva direccion y tipo de envio a un pedido
	 * @param envio Tipo de envio
	 * @param direccion Nueva o antigua direcion, depende de si se ha modificado o no
	 * @param pedido id de referencia del pedido
	 */
	public void anadirTipoEnvioyDireccion(String envio, String direccion,long pedido) {
		Pedido p = obtenerPedidoPorReferencia(pedido);
		p.setTipoEnvio(envio);
		p.setDireccion(direccion);
		add(p);	
	}
	
	
	/**
	 * Metodo para eliminar una linea de un pedido en la base de datos
	 * @param id Id de la linea de pedido
	 */
	public void eliminarLineaPedido(long id) {
		this.repositorioLinea.deleteById(id);
	}
	
	
	/**
	 * Metodo para editar un pedido y modifcarlo en la base de datos
	 * @param pedido El pedido que deseamos editar
	 * @param precioTotal El nuevo precio total del pedido
	 * @param envio antiguo o nuevo tipo de envio, segun si se ha editado o no
	 * @param direccion antigua o nueva direccion, segun si se ha editado o no
	 * @param telefono antiguo o nuevo telefono, segun si se ha editado o no
	 * @param email antiguo o nuevo email , segun si se ha editado o no
	 * @param productos Lista con las lineas de producto
	 */
	public void editarPedido(Pedido pedido, double precioTotal, String envio, String direccion, String telefono, String email, List<ProductoPedido> productos) {
		pedido.setDireccion(direccion);
		pedido.setTelefono(telefono);
		pedido.setEmail(email);
		pedido.setPrecioTotal(precioTotal);
		pedido.setTipoEnvio(envio);
		pedido.setProductos(productos);
		this.add(pedido);
	}
	
	
	/**
	 * Metodo para modificar las lineas de producto
	 * @param cantidades Lista de cantidades modificadas
	 * @param productos Lista de lineas de pedido que vamos a modificar
	 * @return La lista de lineas de pedido modificada
	 */
	public List<ProductoPedido> editarLineasPedido(int[] cantidades, List<ProductoPedido> productos){
		//Recorro la lista de productos del pedido, para cambiarle las cantidades
		for(int i = 0; i < cantidades.length; i++) {
			int cantidad = cantidades[i];
			productos.get(i).setCantidad(cantidad);
		}
		//Si una linea tiene cantidad 0, la eliminamos de la base de datos y de la lista
		int i = 0;
		while( i < productos.size()){
			if (productos.get(i).getCantidad() <= 0) {
				eliminarLineaPedido(productos.get(i).getIdLinea());
				productos.remove(productos.get(i));
			}
			else {
				i++;
			}
		}
		
		return productos;
	}
	
	
	/**
	 * Metodo para calcular el nuevo precio total 
	 * @param productos Lista de lineas de productos
	 * @return El precio total
	 */
	public double calcularPrecioTotal(List<ProductoPedido> productos) {
		double precioTotal = 0;
		for(ProductoPedido producto : productos) {
			precioTotal += producto.getProducto().getPrecio() * producto.getCantidad();
		}
		return precioTotal;
	}
	
	
	/**
	 * Metodo para borrar pedidos de la base de datos
	 * @param refe
	 */
	 
	public void borrarPedido(long refe) {
		this.repositorio.deleteById(refe);
	}
	
	
	/**
	 * Metodo para comprobar si se han añadido productos al carrito
	 * @param cantidades Lista de cantidades compradas
	 * @return true si se se han añadido productos o false si no se han añadido
	 */
	public boolean comprobarCarrito(int[] cantidades) {
		boolean comprobarCarrito = false;
		//recorro el array con las cantidades indicadas en el formulario, para ver que ha añadido minimo 1 producto
		for (int i = 0; i < cantidades.length && !comprobarCarrito; i++) {
			if (cantidades[i] > 0) {
				comprobarCarrito = true;
			}
		}
		return comprobarCarrito;
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
	 * Metodo para obtener todos los pedidos que pertenecen a un usuario
	 * @param us id usuario
	 * @return Todos los pedidos de un usuario
	 */
	public List<Pedido> findPedidoUser(String us){
		return repositorio.findPedidoUser(us);
	}

}