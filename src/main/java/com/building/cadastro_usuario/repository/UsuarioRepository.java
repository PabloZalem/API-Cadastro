package com.building.cadastro_usuario.repository;

import com.building.cadastro_usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Usuario findByUsuario(String usuario);
    @Transactional
	void deleteByUsuario(String usuario);
    boolean existsByUsuario(String usuario);
}
