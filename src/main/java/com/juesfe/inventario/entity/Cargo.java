package com.juesfe.inventario.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


import lombok.Data;

@Data
@Entity
@Table(name = "cargos")
public class Cargo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cargo_id")
	private Long id;
	
	@NotBlank(message = "El nombre del cargo no puede estar vacio")
	@Column(name = "cargo_nombre")
	private String cargoNombre;
}
