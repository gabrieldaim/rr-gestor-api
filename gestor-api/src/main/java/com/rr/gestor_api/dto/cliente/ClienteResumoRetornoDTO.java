package com.rr.gestor_api.dto.cliente;

import com.rr.gestor_api.domain.cliente.TipoCliente;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClienteResumoRetornoDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String ultimoTrabalho;
    private TipoCliente tipoCliente;
}
