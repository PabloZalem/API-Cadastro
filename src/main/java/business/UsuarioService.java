package business;

import org.springframework.stereotype.Service;

import infrastructure.entitys.Usuario;
import infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioService {
	// Vamos fazer por injecao de dependencia
	private final UsuarioRepository repository;

	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}

	public void salvarUsuario(Usuario usuario) {
		repository.saveAndFlush(usuario);
	}

	public Usuario buscarUsuarioPorEmail(String email) {
		return repository.findByEmail(email).orElseThrow(
				() -> new RuntimeException("Email nao encontrado")
		);
	}
}
