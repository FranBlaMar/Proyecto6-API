package com.example.demo.controller;


import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.ProductoPedido;
import com.example.demo.model.Usuario;
import com.example.demo.utiles.Messages;

/**
 * Clase controlador
 * @author Usuario
 *
 */
@Controller
public class UsuarioController {
	
	@Autowired
	private HttpSession sesion;
	
	@Autowired 
	private UsuarioService servicioUsuario;
	@Autowired
	private ProductoService servicioProducto;
	@Autowired
	private PedidoService servicioPedido;

	
	/**
	 * Pantalla inicial de logeo del usuario
	 * @param model para pasar al formulario un objeto usuario
	 * @return String direccion html
	 */
	@GetMapping({"/", "/loginUsr"})
	public String loginUsuario (Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login";
	}
	
	
	/**
	 * Comprobacion del usuario
	 * @param newUser Nuevo usuario creado en el formulario
	 * @param errores errores producidos en el formulario
	 * @param model 
	 * @param redirectAttribute
	 * @return String direccion html
	 */
	@PostMapping("/loginUsr/submit")
	public String comprobarLogin(@Valid @ModelAttribute("usuario") Usuario newUser,
			BindingResult errores, Model model, RedirectAttributes redirectAttribute) {
		
		String resultado;
		//Si hay errores en el formulario o la persona intenta acceder sin iniciar sesion, le reenvia al login
		if (errores.hasErrors() || servicioUsuario.comprobarUser(newUser) == null) {
			this.sesion.setAttribute("errorLogin", true);
			redirectAttribute.addFlashAttribute("errorLogeo", Messages.getErrorLogin());
			model.addAttribute("errorLogin", Messages.getErrorLogin());
			resultado = "redirect:/";
		}
		else {
			//Si el usuario es correcto, almacenamos el id en sesion
			this.sesion.setAttribute("usuario", newUser.getUser());
			resultado = "menu";
		}
		return resultado;
	}
	
	
	/**
	 * Mostrar el lista de pedidos de un usuario
	 * @param model
	 * @return String direccion html
	 */
	@GetMapping("/listaPedidos")
	public String listarPedidos(Model model) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//Obtengo el usuario
			Usuario usuario = this.servicioUsuario.obtenerUsuario(this.sesion.getAttribute("usuario").toString());
			model.addAttribute("nombre", usuario.getNombre());
			//Obtengo los pedidos del usuario anterior
			List<Pedido> listaPedidos = servicioPedido.findPedidoUser(usuario.getUser()); 
			model.addAttribute("listaPedidos", listaPedidos);
			resultado = "lista";
		}
		return resultado;
	}
	
	/**
	 * Mostrar el catalogo de productos para realizar el pedido
	 * @param model
	 * @return String direccion html
	 */
	@GetMapping("/realizarPedido")
	public String mostrarCatalogo(Model model) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//Obtengo todos los productos del servidor para mostrarlos en el catalogo
			List<Producto> listaProductos = this.servicioProducto.findAll();
			model.addAttribute("listaProductos",listaProductos);
			resultado = "catalogo";
		}
		return resultado;
	}
	
	/**
	 * Recoger los productos comprados por el usuario y crear pedido
	 * @param cantidades lista con las cantidades introducidas por el usuario 
	 * @param model
	 * @param redirectAttributes
	 * @return String direccion html
	 */
	@PostMapping("/realizarPedido/añadirProductos")
	public String realizarPedido(@RequestParam("cantidad") int[] cantidades, Model model, RedirectAttributes redirectAttributes){
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			boolean comprobarCarrito = this.servicioPedido.comprobarCarrito(cantidades);
			//Si no ha añadido ningun producto, le redirecciona al catalogo y le muestra error
			if(!comprobarCarrito) {
				resultado ="redirect:/realizarPedido";
				redirectAttributes.addFlashAttribute("errorCatalogo", Messages.getErrorCatalogo());
			}
			else {
				//Si la compra es correcta, obtenemos el usuario que la ha realizado
				Usuario usuario = this.servicioUsuario.obtenerUsuario(this.sesion.getAttribute("usuario").toString());
				//Creamos el pedido y lo añadimos al model junto al usuario
				Pedido pedido = this.servicioPedido.crearPedido(cantidades, usuario);
				model.addAttribute("usuario",usuario);
				model.addAttribute("pedido",pedido);
				resultado = "resumen";
			}
		}
		
		return resultado;
	}
	
	/**
	 * Cerrar sesion del usuario
	 * @return String redireccion
	 */
	@GetMapping("/cerrarSesion")
	public String cerrarSesion(){
		this.sesion.invalidate();
		return "redirect:/";
	}

	/**
	 * Mostrar resumen del pedido realizado y añadir el tipo de envio y la direccion en caso de modificación de esta
	 * @param envio Tipo de envio seleccionado
	 * @param direccion Direccion de envio del usuario o una nueva direccion
	 * @param refe id de referencia del pedido 
	 * @return
	 */
	@PostMapping("/realizarPedido/resumen/finish/{refe}")
	public String finalizarPedido(@RequestParam("envio") String envio, @RequestParam("direccion") String direccion, @PathVariable("refe") int refe) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//añado al pedido el tipo de envio y la direccion
			this.servicioPedido.anadirTipoEnvioyDireccion(envio, direccion, refe);
			resultado="redirect:/listaPedidos";
		}
		return resultado;
	}
	
	/**
	 * Editar pedido
	 * @param model
	 * @param refe id de referencia del pedido que se desea editar
	 * @return
	 */
	@GetMapping("/editarPedido/{refe}")
	public String editarPedido(Model model, @PathVariable int refe) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//Obtengo el usuario
			Usuario us= this.servicioUsuario.obtenerUsuario(this.sesion.getAttribute("usuario").toString());
			model.addAttribute("usuario",us);
			//Obtengo el pedido mediante la referencia, para mostrar los datos en el formulario de edicion
			model.addAttribute("pedido", this.servicioPedido.obtenerPedidoPorReferencia(refe));
			
			resultado="editar";
		}
		return resultado;
	}
	
	
	/**
	 * Realizar cambios en el pedido 
	 * @param cantidades lista con las cantidades modificadas 
	 * @param refe id de referencia del pedido editado
	 * @param direccion antigua o nueva direccion, segun si se ha editado o no
	 * @param telefono antiguo o nuevo telefono, segun si se ha editado o no
	 * @param email antiguo o nuevo email , segun si se ha editado o no
	 * @param envio antiguo o nuevo tipo de envio, segun si se ha editado o no
	 * @param redirectAttribute
	 * @return String direccion html
	 */
	@PostMapping("/editarPedido/realizarCambios/{refe}")
	public String finalizarEdicionPedido( 
		@RequestParam int[] cantidades, @PathVariable int refe, 
		@RequestParam String direccion, @RequestParam String telefono, 
		@RequestParam String email, @RequestParam String envio, RedirectAttributes redirectAttribute) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//Obtenemos el pedido mediante la referencia
			Pedido pedido = servicioPedido.obtenerPedidoPorReferencia(refe);
			//Obtenemos la lista de lineas del pedido
			List<ProductoPedido> productos = pedido.getProductos();
			//Editamos las cantidades de los productos
			productos = this.servicioPedido.editarLineasPedido(cantidades, productos);
			//Calculamos el nuevo precio a pagar
			double precioTotal = this.servicioPedido.calcularPrecioTotal(productos);
			
			//Si el usuario ha eliminado todos los productos, le redirecciono a la edicion y le muestro error
			if (productos.isEmpty()) {
				servicioPedido.borrarPedido(refe);
				resultado ="redirect:/listaPedidos";
			}
			else {
				//Editamos el pedido
				this.servicioPedido.editarPedido(pedido, Math.round(precioTotal*100.0)/100.0,envio, direccion, telefono, email, productos );
				resultado ="redirect:/listaPedidos";
			}
		}
		return resultado;
	}
	
	/**
	 * Borrado de pedido del servidor
	 * @param refe id de refencia del pedido a borrar
	 * @return String direccion html
	 */
	@GetMapping ("/borrarPedido/{refe}")
	public String borrarPedido(@PathVariable long refe) {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			//Borro el pedido
			this.servicioPedido.borrarPedido(refe);
			resultado = "redirect:/listaPedidos";
		}
		return resultado;
	}
	
	/**
	 * Volver al menu de usuario
	 * @return String direccion html
	 */
	@GetMapping("/menu")
	public String volverAlMenu() {
		String resultado;
		if(this.sesion.getAttribute("usuario") == null) {
			resultado = "redirect:/";
		}
		else {
			resultado = "menu";
		}
		return resultado;
	}
}
