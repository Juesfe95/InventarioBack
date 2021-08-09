package com.juesfe.inventario.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juesfe.inventario.constant.ApiConstant;
import com.juesfe.inventario.entity.Usuario;
import com.juesfe.inventario.service.UsuarioService;

@RestController
@RequestMapping(ApiConstant.USUARIO_CONTROLLER_API)
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping(value = ApiConstant.USUARIO_CONTROLLER_API_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> crearUsuario(@Valid @RequestBody Usuario nuevoUsuaurio) {
		return this.usuarioService.crearUsuario(nuevoUsuaurio);
	}
	
	@PutMapping(value = ApiConstant.USUARIO_CONTROLLER_API_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> actalizarUsuario(@Valid @RequestBody Usuario usuarioActualizar) {
		return this.usuarioService.actalizarUsuario(usuarioActualizar);
	}
	
	@GetMapping(value = ApiConstant.USUARIO_CONTROLLER_API_GET_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllUsuarios(){
	return this.usuarioService.getAllUsuarios();
	}
	
	@DeleteMapping(value = ApiConstant.USUARIO_CONTROLLER_API_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> eliminerUsuario(@RequestParam Long idUsuario){
	return this.usuarioService.eliminerUsuario(idUsuario);
	}
}
