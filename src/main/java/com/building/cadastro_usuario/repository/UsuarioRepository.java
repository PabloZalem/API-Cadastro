package com.building.cadastro_usuario.repository;

import java.util.Optional;

import com.building.cadastro_usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario> findByEmail(String email);
	
	@Transactional
	void deleteByEmail(String email);
}
