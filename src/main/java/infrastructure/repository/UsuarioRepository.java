package infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import infrastructure.entitys.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
}
