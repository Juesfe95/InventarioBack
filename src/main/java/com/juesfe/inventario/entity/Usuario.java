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
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long id;
	
	@Column(name = "u_fecha_ingreso")
	private Date fechaIngreso;
	
	@NotBlank(message = "El nombre no puede estar vacio")
	@Column(name = "usuario_nombre")
	private String nombre;
	
	@NotBlank(message = "El email no puede estar vacio")
	@Column(name = "usuario_email",unique= true)
	private String email;
	
	@Column(name = "usuario_edad")
	private String edad;
	
	@NotNull(message = "el cargo no puede estar vacio")
	@ManyToOne
	@JoinColumn(name = "u_cargo_id")
	private Cargo cargoId;
	
}
