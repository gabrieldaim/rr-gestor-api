package com.rr.gestor_api.dto.cliente;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.cliente.TipoCliente;

public record ClienteRetornoDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        TipoCliente tipoCliente,
        String observacao,
        String indicadoPorEmail,
        String indicadoPorNome,
        Long indicadoPorId
) {
    // Construtor que converte diretamente de um Cliente para ClienteOutputDTO
    public ClienteRetornoDTO(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getTipoCliente(),
                cliente.getObservacao(),
                cliente.getIndicadoPor() != null ? cliente.getIndicadoPor().getEmail() : null,
                cliente.getIndicadoPor() != null ? cliente.getIndicadoPor().getNome() : null,
                cliente.getIndicadoPor() != null ? cliente.getIndicadoPor().getId() : null
        );
    }

}
