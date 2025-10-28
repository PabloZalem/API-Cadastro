package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.UsuarioService;
import infrastructure.entitys.Usuario;
import lombok.RequiredArgsConstructor;

@RequestMapping("/usuario")
@RestController
@RequiredArgsConstructor
public class UsuarioController {
	private final UsuarioService service;
	
	@PostMapping
	public ResponseEntity<Void> salvarUsuario(@RequestBody Usuario usario) {
		service.salvarUsuario(usario);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam String email) {
		return ResponseEntity.ok(service.buscarUsuarioPorEmail(email));
	}
}
