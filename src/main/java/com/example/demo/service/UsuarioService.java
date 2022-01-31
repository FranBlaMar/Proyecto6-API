package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

/**
 * Clase servicio de usuarios
 * @author Usuario
 *
 */
@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repositorio;
	
	/**
	 * Metodo para obtener todos los usuarios
	 * @return Lista de los usuarios existentes en la web
	 */
	public List<Usuario> findAll() {
		return repositorio.findAll();
	}
	
	/**
	 * Metodo para comprobar si un usuario existe en nuestro servidor
	 * @param us Usuario creado en el login, que usaremos para comparar con usuarios existentes
	 * @return Devuelve el usuario que hayamos encontrado, en caso de no existir devuelve null
	 */
	public Usuario comprobarUser(Usuario us) {
		Usuario resultado = null;
		//Compruebo si existe un usuario con ese user
		if (repositorio.findById(us.getUser()).orElse(null) != null) {
			//Si existe, compruebo si la contraseña es correcta
			if(repositorio.findById(us.getUser()).orElse(null).getContrasena().equals(us.getContrasena())) {
				resultado = repositorio.findById(us.getUser()).orElse(null);
			}
		}
		//Si no ha entrado en el primer if, es que no existe un usuario y devuelve resultado. Resultado está inicializado como null
		return resultado;
	}
	
	/**
	 * Metodo para obtener un usuario mediante su user
	 * @param us Usuario
	 * @return Devuelve el usuario que está almacenado en el servidor
	 */
	public Usuario obtenerUsuario(String us) {
		return repositorio.findById(us).orElse(null);
	}
	
}
