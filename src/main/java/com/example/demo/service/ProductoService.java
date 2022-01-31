package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;

/**
 * Clase servicio de productos
 * @author Usuario
 *
 */
@Service
public class ProductoService {

	@Autowired
	private ProductoRepository repositorio;
	

	/**
	 * Metodo para obtener todos los productos almacenados en el servidor
	 * @return Lista de productos
	 */
	public List<Producto> findAll() {
		return repositorio.findAll();
	}
	
	/**
	 * Metodo para obtener un producto mediante su id
	 * @param id Id del producto que deseamos obtener
	 * @return Producto deseado
	 */
	public Producto obtenerProductoPorId(long id){
		return repositorio.findById(id).orElse(null);
	}
	
	
}