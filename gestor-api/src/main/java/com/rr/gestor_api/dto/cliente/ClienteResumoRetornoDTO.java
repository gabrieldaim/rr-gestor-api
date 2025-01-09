package com.rr.gestor_api.dto.cliente;

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
    private String tipoCliente;
}
