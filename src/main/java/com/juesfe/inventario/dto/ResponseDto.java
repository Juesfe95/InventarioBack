package com.juesfe.inventario.dto;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@SuppressWarnings("deprecation")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class ResponseDto {
	
	private String categoria;
	private String codigo;
	private String descripcion;
	private int estado;
	private String tiempoRespuesta;
	
	private Object objetoRespuesta;
	
	public ResponseDto(HttpStatus status, String message) {
		super();
		this.tiempoRespuesta = OffsetDateTime.now().toString();
		this.estado = status.value();
		this.codigo = status.name();
		this.descripcion = message;
		this.categoria = "DEFAULT_CATEGORY";
	}
	
}
