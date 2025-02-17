package com.rr.gestor_api.repositories;

import com.rr.gestor_api.domain.entrega.StatusEntrega;
import com.rr.gestor_api.domain.parcela.StatusParcela;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoParcelasRetornoDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoProxEntregasRetornoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrabalhoRepository extends JpaRepository<Trabalho, Long> {
    List<Trabalho> findByClienteId(Long clienteId);

    @Query("SELECT new com.rr.gestor_api.dto.trabalho.TrabalhoResumoProxEntregasRetornoDTO(" +
            "t.id, " +
            "t.cliente.nome, " +
            "t.tema, " +
            "t.tipoTrabalho, " +
            "e.data, " +
            "e.status) " +
            "FROM Trabalho t " +
            "JOIN t.entregas e " +
            "WHERE e.data = (SELECT MIN(e2.data) FROM t.entregas e2 WHERE e2.status <> 'CONCLUIDA' AND e2.trabalho = t) " +
            "AND t.statusEntregas <> 'CONCLUIDA' " +
            "AND e.status <> 'CONCLUIDA' " +
            "ORDER BY e.data ASC")
List<TrabalhoResumoProxEntregasRetornoDTO> findTrabalhosProxEntregas();


@Query("SELECT new com.rr.gestor_api.dto.trabalho.TrabalhoResumoParcelasRetornoDTO(" +
"t.id, " +
"t.cliente.nome, " +
"t.tema, " +
"t.tipoTrabalho, " +
"p.data, " +
"p.status) " +
"FROM Trabalho t " +
"JOIN t.parcelas p " +
"WHERE p.data = (SELECT MIN(p2.data) FROM t.parcelas p2 WHERE p2.status <> 'PAGA' AND p2.trabalho = t) " +
"AND t.statusParcelas <> 'PAGA' " +
"AND p.status <> 'PAGA' " +
"ORDER BY p.data ASC")
    List<TrabalhoResumoParcelasRetornoDTO> findTrabalhosProxParcelas();

    @Query("SELECT new com.rr.gestor_api.dto.trabalho.TrabalhoResumoProxEntregasRetornoDTO(" +
    "t.id, " +
    "t.cliente.nome, " +
    "t.tema, " +
    "t.tipoTrabalho, " +
    "e.data, " +
    "e.status) " +
    "FROM Trabalho t " +
    "JOIN t.entregas e " +
    "JOIN t.responsavel r " + // Adicionando o JOIN para a entidade Usuario
    "WHERE e.data = (SELECT MIN(e2.data) FROM t.entregas e2 WHERE e2.status <> 'CONCLUIDA' AND e2.trabalho = t) " +
    "AND t.statusEntregas <> 'CONCLUIDA' " +
    "AND e.status <> 'CONCLUIDA' " +
    "AND r.email = :responsavelEmail " + // Adicionando a condição para o email do responsável
    "ORDER BY e.data ASC")
List<TrabalhoResumoProxEntregasRetornoDTO> findMeusTrabalhos(@Param("responsavelEmail") String responsavelEmail);

@Query("SELECT new com.rr.gestor_api.dto.trabalho.TrabalhoResumoProxEntregasRetornoDTO(" +
        "t.id, " +
        "t.cliente.nome, " +
        "t.tema, " +
        "t.tipoTrabalho, " +
        "e.data, " +
        "e.status) " +
        "FROM Trabalho t " +
        "JOIN t.entregas e " +
        "WHERE e.data = (SELECT MAX(e2.data) FROM t.entregas e2) " + // Subconsulta para obter a data mais recente
        "ORDER BY e.data DESC") // Ordenando pela data mais recente
List<TrabalhoResumoProxEntregasRetornoDTO> findAllTrabalhos();

    @Query("SELECT new com.rr.gestor_api.dto.trabalho.TrabalhoResumoProxEntregasRetornoDTO(" +
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
    List<TrabalhoResumoProxEntregasRetornoDTO> findTrabalhosWithMinEntregaDateByClienteEmail(@Param("email") String email);

//     @Query("SELECT new com.rr.gestor_api.dto.trabalho.TrabalhoResumoProxEntregasRetornoDTO(" +
//        "t.id, " +
//        "t.cliente.nome, " +
//        "t.tema, " +
//        "t.tipoTrabalho, " +
//        "MAX(e.data), " +
//        "e.status) " +
//        "FROM Trabalho t " +
//        "LEFT JOIN t.entregas e " +
//        "WHERE e.data = (SELECT MAX(e2.data) FROM t.entregas e2 WHERE e2.trabalho = t) OR e.data IS NULL " +
//        "GROUP BY t.id, t.cliente.nome, t.tema, t.tipoTrabalho, e.status " +
//        "ORDER BY MAX(e.data) DESC")
// List<TrabalhoResumoProxEntregasRetornoDTO> findTrabalhosComEntregaMaisRecente();

    @Query("SELECT t FROM Trabalho t JOIN t.parcelas p WHERE p.status = :status")
    List<Trabalho> findTrabalhosWithParcelasStatus(@Param("status") StatusParcela status);

    @Query("SELECT t FROM Trabalho t JOIN t.entregas e WHERE e.status IN :statuses")
    List<Trabalho> findTrabalhosWithEntregasStatus(@Param("statuses") List<StatusEntrega> statuses);




}
