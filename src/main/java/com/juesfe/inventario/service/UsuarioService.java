package com.juesfe.inventario.service;
import java.sql.Date;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.juesfe.inventario.dto.ResponseDto;
import com.juesfe.inventario.entity.Usuario;
import com.juesfe.inventario.repository.UsuarioDao;

@Service
public class UsuarioService {
	
	private static final Logger logger = Logger.getLogger(UsuarioService.class);
			
	@Autowired
	private UsuarioDao usuarioDao;
	
	
	/**
	 * Esta funcion se encarga de la creacion de un usuario
	 * @param nuevoUsuaurio objeto que contiene los campos necesarios para crear el usuario
    */
	public ResponseEntity<Object> crearUsuario(@Valid @RequestBody Usuario nuevoUsuaurio) {
		ResponseEntity<Object> response = ResponseEntity.ok(HttpStatus.OK);
		ResponseDto responseDto = null;
		try {
			if(!this.existeEmail(nuevoUsuaurio.getEmail()))
			{
				nuevoUsuaurio.setFechaIngreso(new Date(new java.util.Date().getTime()));
				this.usuarioDao.save(nuevoUsuaurio);
				responseDto = new ResponseDto(HttpStatus.OK, "Exito creando el usuario");
				responseDto.setObjetoRespuesta("Usuario creado con exito");
				response = new ResponseEntity<>(responseDto, HttpStatus.OK);
			}else
			{
				responseDto = new ResponseDto(HttpStatus.BAD_REQUEST, "Error creando el usuario, ya existe un usuario registrado con el 'Email' ingresado");
				response = new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			responseDto = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear el usuario " + e);
			response = new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	/**
	 * Esta funcion se encarga de la actualizacion de un usuario 
	 * @param usuarioActualizar objeto que contiene los campos necesarios para actualizar el usuario
    */
	public ResponseEntity<Object> actalizarUsuario(@Valid @RequestBody Usuario usuarioActualizar) {
		
		ResponseEntity<Object> response = ResponseEntity.ok(HttpStatus.OK);
		ResponseDto responseDto = null;
		Usuario usuario = this.usuarioDao.findById(usuarioActualizar.getId()).get();
		
		try 
		{
			if(!this.existeEmail(usuarioActualizar.getEmail()) || usuario.getEmail().equalsIgnoreCase(usuarioActualizar.getEmail()))
			{
				usuario.setNombre(usuarioActualizar.getNombre());
				usuario.setEdad(usuarioActualizar.getEdad());
				usuario.setEmail(usuarioActualizar.getEmail());
				this.usuarioDao.save(usuario);
				usuario = this.usuarioDao.findById(usuarioActualizar.getId()).get();
				responseDto = new ResponseDto(HttpStatus.OK, "Exito actualizando el usuario");
				responseDto.setObjetoRespuesta(usuario);
				response = new ResponseEntity<>(responseDto, HttpStatus.OK);
			}
			else
			{
				responseDto = new ResponseDto(HttpStatus.BAD_REQUEST, "Error actualizando el usuario, ya existe un usuario registrado con el 'Email' ingresado");
				response = new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
			}
		} 
		catch (Exception e) 
		{
			UsuarioService.logger.error("Error al actualizar el usuario " + e);
			responseDto = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el usuario " +e);
			response = new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	
	/**
	 * Esta funcion se encarga de listar todos los usuarios registrados en la base de datos
    */
	public ResponseEntity<Object> getAllUsuarios() {
		ResponseEntity<Object> response = ResponseEntity.ok(HttpStatus.OK);
		ResponseDto responseDto = null;
		try {
			responseDto = new ResponseDto(HttpStatus.OK, "Exito listando los usuarios");
			responseDto.setObjetoRespuesta(this.usuarioDao.findAll());
			response = new ResponseEntity<>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			responseDto = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar los usuarios " + e);
			response = new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	/**
	 * Esta funcion se encarga de eliminar un usuario de la base de datos
	 * @param idUsuario id del usuario a eliminar
    */
	public ResponseEntity<Object> eliminerUsuario(@RequestParam Long idUsuario) {
		ResponseEntity<Object> response = ResponseEntity.ok(HttpStatus.OK);
		ResponseDto responseDto = null;
		Usuario usuarioEliminar = new Usuario();
		try {
			usuarioEliminar = this.usuarioDao.findById(idUsuario).get();
			this.usuarioDao.deleteById(idUsuario);
			responseDto = new ResponseDto(HttpStatus.OK, "Exito eliminando el usuario");
			responseDto.setObjetoRespuesta(usuarioEliminar);
			response = new ResponseEntity<>(responseDto, HttpStatus.OK);
		} catch (Exception e) {
			responseDto = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Error eliminando el usuario " + e);
			response = new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	/**
	 * Esta funcion se encarga de verificar si el email de usuario ya existe en la BD
	 * @param email el email de usuario a validar
    */
	public boolean existeEmail(String email) {
		
		boolean bandera = false;
		
		if(this.usuarioDao.buscarEmail(email) != null) {
			bandera = true;
		}
		return bandera;
	}
	
	
	
}
