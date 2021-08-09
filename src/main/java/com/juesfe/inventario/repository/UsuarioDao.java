package com.juesfe.inventario.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.juesfe.inventario.entity.Usuario;

@Transactional
public interface UsuarioDao extends JpaRepository<Usuario, Long> {
	
	@Query("SELECT DISTINCT user FROM Usuario user " +
			"WHERE UPPER(user.email) = UPPER(:email) ")
	Usuario buscarEmail(@Param("email") String email);
}
