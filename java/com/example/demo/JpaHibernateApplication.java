package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.UsuarioRepository;

/**
 * Clase Application
 * @author Usuario
 *
 */
@SpringBootApplication
public class JpaHibernateApplication extends SpringBootServletInitializer{


	public static void main(String[] args) {
		SpringApplication.run(JpaHibernateApplication.class, args);
	}
	
	/**
	 * Metodo para almacenar en la base de datos los usuarios y pedidos
	 * @param repositorioUs Repositorio donde almacenar los usuarios
	 * @param repositorioPro Repositorio donde alamcenar los productos
	 * @return
	 */
	@Bean
	CommandLineRunner initData(UsuarioRepository repositorioUs,ProductoRepository repositorioPro) {
		return args -> {
			repositorioUs.saveAll( Arrays.asList(new Usuario("J123", "123", "jorge@dominio.com","Jorge", "911111111", "C/Rosales del campo Nº3"), new Usuario("FRAN", "BLANCO", "fran@dominio.com","Francisco", "922222222", "C/Puente romero Nº23"),new Usuario("F123", "111", "ff123@dominio.com","Maria", "933333333", "C/Perez de la luna Nº41")));
			repositorioPro.saveAll(Arrays.asList (new Producto("Camiseta","camiseta.jpg", 6.99),new Producto("Sudadera","sudadera.jpg", 26.50),new Producto("Botines","botines.jpg", 20.25),new Producto("Gorro","gorro.jpg", 5.99), new Producto("Pendiente","pendiente.jpg", 2.50),new Producto("Pantalon","pantalon.jpg", 19.99)));
		};
	}
}
