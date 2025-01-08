package com.rr.gestor_api.repositories;

import com.rr.gestor_api.domain.trabalho.Trabalho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrabalhoRepository extends JpaRepository<Trabalho, Long> {
    List<Trabalho> findByClienteId(Long clienteId);
}
