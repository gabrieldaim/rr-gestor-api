package com.rr.gestor_api.repositories;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);

}
