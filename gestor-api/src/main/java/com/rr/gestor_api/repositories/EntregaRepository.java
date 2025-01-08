package com.rr.gestor_api.repositories;

import com.rr.gestor_api.domain.entrega.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByTrabalhoId(Long trabalhoId);
}
