package com.juesfe.inventario.service;

import java.sql.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.juesfe.inventario.dto.ResponseDto;
import com.juesfe.inventario.entity.LogAuditoriasMercancia;
import com.juesfe.inventario.entity.Mercancia;
import com.juesfe.inventario.entity.Usuario;
import com.juesfe.inventario.repository.MercanciaDao;
import com.juesfe.inventario.repository.UsuarioDao;
import com.juesfe.inventario.request.EliminarProductoAux;

@Service
public class MercanciaService {
	
	@Autowired
	private MercanciaDao mercanciaDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	/**
	 * Esta funcion se encarga de la creacion de un producto
	 * @param nuevoProducto objeto que contiene los campos necesarios para crear el producto
    */
	public ResponseEntity<Object> crearProducto(@Valid @RequestBody Mercancia nuevoProducto) {
		ResponseEntity<Object> response = ResponseEntity.ok(HttpStatus.OK);
		ResponseDto responseDto = null;
		Usuario user = new Usuario();
		try {
			if(!this.existeNombreProducto(nuevoProducto.getNombre())) {
				user = this.usuarioDao.findById(nuevoProducto.getUsuarioId().getId()).get();
				nuevoProducto.setFechaIngreso(new Date(new java.util.Date().getTime()));
				this.mercanciaDao.save(nuevoProducto);
				nuevoProducto.setUsuarioId(user);
				responseDto = new ResponseDto(HttpStatus.OK, "Exito creando el producto");
				responseDto.setObjetoRespuesta(nuevoProducto);
				response = new ResponseEntity<>(responseDto, HttpStatus.OK);
			}else
			{
				responseDto = new ResponseDto(HttpStatus.BAD_REQUEST, "Error creando el producto, ya existe un producto registrado con el 'Nombre' ingresado");
				response = new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			responseDto = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear el producto " + e);
			response = new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	
	
	/**
	 * Esta funcion se encarga de la actualizacion de un producto 
	 * @param productoActualizar objeto que contiene los campos necesarios para actualizar el producto
    */
	public ResponseEntity<Object> actalizarProducto(@Valid @RequestBody Mercancia productoActualizar) {
		
		ResponseEntity<Object> response = ResponseEntity.ok(HttpStatus.OK);
		Mercancia producto = this.mercanciaDao.findById(productoActualizar.getId()).get();
		LogAuditoriasMercancia log = new LogAuditoriasMercancia();
		try 
		{
			if(!this.existeNombreProducto(productoActualizar.getNombre()) || producto.getNombre().equalsIgnoreCase(productoActualizar.getNombre()))
			{
				producto.setNombre(productoActualizar.getNombre());
				producto.setCantidad(productoActualizar.getCantidad());
				this.mercanciaDao.save(producto);
				
				//Registro de acciones en el log
				log.setAccion("Actualizacion");
				
				log.setFechaIngreso(new Date(new java.util.Date().getTime()));
				log.setProductoId(producto);
				log.setUsuarioAccion(productoActualizar.getUsuarioId());
				
				ResponseDto responseDto = new ResponseDto(HttpStatus.OK, "Exito actualizando el producto");
				responseDto.setObjetoRespuesta(producto);
				response = new ResponseEntity<>(responseDto, HttpStatus.OK);
			}
			else
			{
				ResponseDto responseDto = new ResponseDto(HttpStatus.BAD_REQUEST, "Error actualizando el producto ya existe un producto registrado con el 'Nombre' ingresado");
				response = new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
			}
		} 
		catch (Exception e) 
		{
			ResponseDto responseDto = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el producto" +e);
			response = new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	
	/**
	 * Esta funcion se encarga de listar todos los productos registrados en la base de datos
    */
	public ResponseEntity<Object> getAllProductos() {
		ResponseEntity<Object> response = ResponseEntity.ok(HttpStatus.OK);
		ResponseDto responseDto = null;
		try {
			responseDto = new ResponseDto(HttpStatus.OK, "Exito listando los productos");
			responseDto.setObjetoRespuesta(this.mercanciaDao.findAll());
			response = new ResponseEntity<>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			responseDto = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar los productos" + e);
			response = new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	/**
	 * Esta funcion se encarga de eliminar un producto de la base de datos
	 * @param EliminarProductoAux objeto con los datos necesarios para eliminar el producto
    */
	public ResponseEntity<Object> eliminarProducto(@RequestBody EliminarProductoAux eliminarProductoAux) {
		ResponseEntity<Object> response = ResponseEntity.ok(HttpStatus.OK);
		ResponseDto responseDto = null;
		Mercancia productoEliminar = new Mercancia();
		LogAuditoriasMercancia log = new LogAuditoriasMercancia();
		try {
			productoEliminar = this.mercanciaDao.findById(eliminarProductoAux.getProductoId()).get();
			Usuario user = this.usuarioDao.findById(eliminarProductoAux.getUsuarioId()).get();
			if(productoEliminar.getUsuarioId() == user) {
				this.mercanciaDao.deleteById(productoEliminar.getId());
				
				//Registro de acciones en el log
				log.setAccion("Eliminar");
				
				log.setFechaIngreso(new Date(new java.util.Date().getTime()));
				log.setProductoId(productoEliminar);
				log.setUsuarioAccion(user);
				responseDto = new ResponseDto(HttpStatus.OK, "Exito eliminando el producto");
				responseDto.setObjetoRespuesta("el producto " + productoEliminar.getNombre() + " fue eliminado");
				response = new ResponseEntity<>(responseDto, HttpStatus.OK);
			}else
			{
				responseDto = new ResponseDto(HttpStatus.BAD_REQUEST, "Error Solo el usuario que registro el producto puede eliminarlo");
				response = new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			responseDto = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error eliminando el producto " + e);
			response = new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	/**
	 * Esta funcion se encarga de verificar si el email de usuario ya existe en la BD
	 * @param email el email de usuario a validar
    */
	public boolean existeNombreProducto(String nombreProducto) {
		
		boolean bandera = false;
		
		if(this.mercanciaDao.buscarNombreProducto(nombreProducto) != null) {
			bandera = true;
		}
		return bandera;
	}
}
