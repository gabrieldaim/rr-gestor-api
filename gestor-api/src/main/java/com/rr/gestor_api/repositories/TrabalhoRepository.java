package com.rr.gestor_api.repositories;

import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoParcelasRetornoDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoRetornoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT new com.rr.gestor_api.dto.trabalho.TrabalhoResumoParcelasRetornoDTO(" +
            "t.id, " +
            "t.cliente.nome, " +
            "t.tema, " +
            "t.tipoTrabalho, " +
            "MIN(p.data), " +
            "p.status) " +
            "FROM Trabalho t " +
            "LEFT JOIN t.parcelas p " +
            "WHERE p.data = (SELECT MIN(p2.data) FROM t.parcelas p2 WHERE p2.trabalho = t) OR p.data IS NULL " +
            "GROUP BY t.id, t.cliente.nome, t.tema, t.tipoTrabalho, p.status " +
            "ORDER BY MIN(p.data) ASC")
    List<TrabalhoResumoParcelasRetornoDTO> findTrabalhosWithMinParcelaDate();

    @Query("SELECT new com.rr.gestor_api.dto.trabalho.TrabalhoResumoRetornoDTO(" +
            "t.id, " +
            "t.cliente.nome, " +
            "t.tema, " +
            "t.tipoTrabalho, " +
            "MIN(e.data), " +
            "e.status) " +
            "FROM Trabalho t " +
            "LEFT JOIN t.entregas e " +
            "WHERE (e.data = (SELECT MIN(e2.data) FROM t.entregas e2 WHERE e2.trabalho = t) OR e.data IS NULL) " +
            "AND t.cliente.email = :email " +
            "GROUP BY t.id, t.cliente.nome, t.tema, t.tipoTrabalho, e.status " +
            "ORDER BY MIN(e.data) ASC")
    List<TrabalhoResumoRetornoDTO> findTrabalhosWithMinEntregaDateByClienteEmail(@Param("email") String email);



}
