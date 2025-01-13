package com.rr.gestor_api.repositories;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.dto.cliente.ClienteResumoRetornoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);

    @Query("SELECT new com.rr.gestor_api.dto.cliente.ClienteResumoRetornoDTO(" +
            "c.id, " +
            "c.nome, " +
            "c.email, " +
            "c.telefone, " +
            "(SELECT t.tema FROM Trabalho t WHERE t.cliente = c ORDER BY t.id DESC LIMIT 1), " +
            "c.tipoCliente) " +
            "FROM Cliente c")
    List<ClienteResumoRetornoDTO> findClientesComUltimoTrabalho();


}
