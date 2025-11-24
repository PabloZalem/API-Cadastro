package com.building.cadastro_usuario.service;

import com.building.cadastro_usuario.entity.Usuario;
import com.building.cadastro_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

	// Vamos fazer por injecao de dependencia
    @Autowired
    private final UsuarioRepository repository;

	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}

    public List<Usuario> buscarTodosUsuarios() {
        return repository.findAll();
    }

	public void salvarUsuario(Usuario usuario) {
        if (repository.existsByUsuario(usuario.getUsuario())) {
            throw new IllegalArgumentException("Usuário já existe");
        }
		repository.saveAndFlush(usuario);
	}

	public Usuario buscarUsuario(String user) {
        Usuario usuario = repository.findByUsuario(user);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario nao encontrado");
        }

		return usuario;
	}

	public void deletarUsuario(String usuario) {
        if (!repository.existsByUsuario(usuario)) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + usuario);
        }
		repository.deleteByUsuario(usuario);
	}
	
	public void atualizarUsuarioPorId(Integer id, Usuario usuario) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
		
		Usuario usuarioAtualizado = Usuario.builder()
        .usuario(usuario.getUsuario() != null ? usuario.getUsuario() : usuarioEntity.getUsuario())
        .senha(usuario.getSenha() != null ? usuario.getSenha() : usuarioEntity.getSenha())
        .id(usuarioEntity.getId())
        .build();
		
		repository.saveAndFlush(usuarioAtualizado);
	}

    /**
     *
     * @param username
     * @return integra a autenticacao com Spring Security
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Usuario usuario = repository.findByUsuario(username);

       if (usuario == null) {
           throw new UsernameNotFoundException("usuario nao encontrado: " + username);
       }

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsuario(),
                usuario.getSenha(),
                Collections.emptyList()
        );
    }
}
