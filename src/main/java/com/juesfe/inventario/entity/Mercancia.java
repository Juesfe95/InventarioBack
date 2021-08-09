package com.juesfe.inventario.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "mercancias")
public class Mercancia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "producto_id")
	private Long id;
	
	@NotBlank(message = "El nombre del producto no puede estar vacio")
	@Column(name = "producto_nombre", unique=true, length = 200)
	private String nombre;
	
	@NotNull(message = "La cantidad no puede ser nula")
	@Column(name = "producto_cantidad")
	private Integer cantidad;
	
	@Column(name = "p_fecha_ingreso")
	private Date fechaIngreso;
	
	@NotNull(message = "el usuario que realiza la accion no puede ser nulo")
	@ManyToOne
	@JoinColumn(name = "p_usuario_id")
	private Usuario usuarioId;
	
	
	
	
	
	
	
	
}
