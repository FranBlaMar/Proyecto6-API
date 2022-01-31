package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Pedido;

public interface PedidoRepository  extends JpaRepository<Pedido, Long>{
	
	@Query("SELECT p FROM Pedido p WHERE usuario_pedido =:us ORDER BY fecha_pedido DESC")
	public List<Pedido> findPedidoUser(String us);
	
}
