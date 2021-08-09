package com.juesfe.inventario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juesfe.inventario.dto.ResponseDto;
import com.juesfe.inventario.repository.CargoDao;

@Service
public class CargoService {
	
	@Autowired
	private CargoDao cargoDao;
	
	
	/**
	 * Esta funcion se encarga de listar todos los cargosregistrados en la base de datos
    */
	public ResponseEntity<Object> getAllCargos() {
		ResponseEntity<Object> response = ResponseEntity.ok(HttpStatus.OK);
		ResponseDto responseDto = null;
		try {
			responseDto = new ResponseDto(HttpStatus.OK, "Exito listando los cargos");
			responseDto.setObjetoRespuesta(this.cargoDao.findAll());
			response = new ResponseEntity<>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			responseDto = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar los cargos" + e);
			response = new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
