package com.building.cadastro_usuario.repository;

import com.building.cadastro_usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Usuario findByUsuario(String usuario);

	boolean existsByUsuario(String usuario);
}
