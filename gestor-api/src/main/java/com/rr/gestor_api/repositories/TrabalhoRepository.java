package com.rr.gestor_api.repositories;

import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoRetornoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrabalhoRepository extends JpaRepository<Trabalho, Long> {
    List<Trabalho> findByClienteId(Long clienteId);

    @Query("SELECT new com.rr.gestor_api.dto.trabalho.TrabalhoResumoRetornoDTO(" +
            "t.id, " +
            "t.cliente.nome, " +
            "t.tema, " +
            "t.tipoTrabalho, " +
            "MIN(e.data), " +
            "e.status) " +
            "FROM Trabalho t " +
            "LEFT JOIN t.entregas e " +
            "WHERE e.data = (SELECT MIN(e2.data) FROM t.entregas e2 WHERE e2.trabalho = t) OR e.data IS NULL " +
            "GROUP BY t.id, t.cliente.nome, t.tema, t.tipoTrabalho, e.status " +
            "ORDER BY MIN(e.data) ASC")
    List<TrabalhoResumoRetornoDTO> findTrabalhosWithMinEntregaDate();


}
