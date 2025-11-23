package com.building.cadastro_usuario.controller;

import com.building.cadastro_usuario.entity.Usuario;
import com.building.cadastro_usuario.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.building.cadastro_usuario.exception.InvalidEmailFormatException;
import com.building.cadastro_usuario.exception.MissingFieldException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	private final UsuarioService service;
	
	@PostMapping("/create")
	public ResponseEntity<String> salvarUsuario(@RequestBody Usuario usuario) {
		if (usuario.getUsuario() == null || usuario.getUsuario().isBlank()) {
			throw new MissingFieldException("Usuario obrigatorio");
		}
	    if (!usuario.getSenha().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
	        throw new InvalidEmailFormatException("Formato de email inválido");
	    }
		
		service.salvarUsuario(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro criado com sucesso");
	}
	
	@GetMapping
	public ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam String email) {
		return ResponseEntity.ok(service.buscarUsuarioPorEmail(email));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarUsuarioPorEmail(@RequestParam String email) {
		service.deletarUsuarioPorEmail(email);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> atualizarUsuarioPorId(@RequestParam Integer id, @RequestBody Usuario usuario) {
		service.atualizarUsuarioPorId(id, usuario);
		return ResponseEntity.ok().build();
	}
}
