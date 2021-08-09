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
@Table(name = "log_auditorias")
public class LogAuditoriasMercancia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
	private Long id;
	
	@NotBlank(message = "la accion no puede estar vacia")
	@Column(name = "log_accion",length = 40)
	private String accion;
	
	@NotBlank(message = "La fecha de ingreso no puede estar vacia")
	@Column(name = "log_fecha_ingreso")
	private Date fechaIngreso;
	
	@NotNull(message = "el usuario que realiza la accion no puede ser nulo")
	@ManyToOne
	@JoinColumn(name = "log_usuario_accion")
	private Usuario usuarioAccion;
	
	@NotNull(message = "el id del producto no puede ser nulo")
	@ManyToOne
	@JoinColumn(name = "log_producto_id")
	private Mercancia productoId;
}
