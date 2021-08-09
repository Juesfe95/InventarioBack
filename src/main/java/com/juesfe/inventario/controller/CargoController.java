package com.juesfe.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juesfe.inventario.constant.ApiConstant;
import com.juesfe.inventario.service.CargoService;

@RestController
@RequestMapping(ApiConstant.CARGO_CONTROLLER_API)
public class CargoController {
	
	@Autowired
	private CargoService cargoService;
	
	@GetMapping(value = ApiConstant.CARGO_CONTROLLER_GET_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllCargos(){
	return this.cargoService.getAllCargos();
	}
}
