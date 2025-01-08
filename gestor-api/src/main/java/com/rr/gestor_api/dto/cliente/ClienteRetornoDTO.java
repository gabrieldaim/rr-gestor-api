package com.rr.gestor_api.dto.cliente;

import com.rr.gestor_api.domain.cliente.Cliente;

public record ClienteRetornoDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String tipoCliente,
        String observacao
) {
    // Construtor que converte diretamente de um Cliente para ClienteOutputDTO
    public ClienteRetornoDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getTipoCliente(), cliente.getObservacao());
    }
}
