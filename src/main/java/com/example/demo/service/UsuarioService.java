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
	 * Metodo para obtener un usuario mediante su user
	 * @param us Usuario
	 * @return Devuelve el usuario que está almacenado en el servidor
	 */
	public Usuario obtenerUsuario(String us) {
		return repositorio.findById(us).orElse(null);
	}
	
}
