package com.rr.gestor_api.repositories;

import com.rr.gestor_api.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,String> {

    Optional<Usuario> findByEmail(String email); 
}
