package com.juesfe.inventario.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juesfe.inventario.constant.ApiConstant;
import com.juesfe.inventario.entity.Mercancia;
import com.juesfe.inventario.request.EliminarProductoAux;
import com.juesfe.inventario.service.MercanciaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(ApiConstant.MERCANCIA_CONTROLLER_API)
public class MercanciaController {
	
	@Autowired
	private MercanciaService mercanciaService;
	
	@PostMapping(value = ApiConstant.MERCANCIA_CONTROLLER_API_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> crearProducto(@Valid @RequestBody Mercancia nuevoProducto) {
		return this.mercanciaService.crearProducto(nuevoProducto);
	}
	
	@PutMapping(value = ApiConstant.MERCANCIA_CONTROLLER_API_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> actalizarProducto(@Valid @RequestBody Mercancia productoActualizar) {
		return this.mercanciaService.actalizarProducto(productoActualizar);
	}
	
	@GetMapping(value = ApiConstant.MERCANCIA_CONTROLLER_API_GET_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllProductos(){
	return this.mercanciaService.getAllProductos();
	}
	
	@DeleteMapping(value = ApiConstant.MERCANCIA_CONTROLLER_API_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> eliminarProducto(@RequestBody EliminarProductoAux eliminarProductoAux){
	return this.mercanciaService.eliminarProducto(eliminarProductoAux);
	}
}
