package com.rr.gestor_api.repositories;

import com.rr.gestor_api.domain.parcela.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
    List<Parcela> findByTrabalhoId(Long trabalhoId);
}
