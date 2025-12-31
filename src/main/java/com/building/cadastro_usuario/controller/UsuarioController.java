package com.building.cadastro_usuario.controller;

import com.building.cadastro_usuario.entity.Usuario;
import com.building.cadastro_usuario.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.building.cadastro_usuario.exception.MissingFieldException;

import lombok.RequiredArgsConstructor;

import java.util.List;
@CrossOrigin(origins = {
        "http://localhost:5500",
        "http://127.0.0.1:5500"
}, maxAge = 3600)
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

		service.salvarUsuario(usuario); // já criptografa dentro do service
		return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro criado com sucesso");
	}

	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> buscarTodosUsuarios() {
		return ResponseEntity.ok(service.buscarTodosUsuarios());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Integer id) {
		Usuario usuario = service.buscarUsuarioPorId(id);
		return ResponseEntity.ok(usuario);
	}

	@DeleteMapping
	public ResponseEntity<Void> deletarUsuarioPorEmail(@RequestParam String email) {
		service.deletarUsuario(email);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	public ResponseEntity<Void> atualizarUsuarioPorId(@RequestParam Integer id, @RequestBody Usuario usuario) {
		service.atualizarUsuarioPorId(id, usuario);
		return ResponseEntity.ok().build();
	}
}
