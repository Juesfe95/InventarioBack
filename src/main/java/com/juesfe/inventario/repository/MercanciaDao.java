package com.juesfe.inventario.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.juesfe.inventario.entity.Mercancia;


@Transactional
public interface MercanciaDao extends JpaRepository <Mercancia, Long> {
	
	@Query("SELECT DISTINCT p FROM Mercancia p " +
			"WHERE UPPER(p.nombre) = UPPER(:nombreProducto) ")
	Mercancia buscarNombreProducto(@Param("nombreProducto") String nombreProducto);
	
}
