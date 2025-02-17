package com.rr.gestor_api.dto.cliente;

import com.rr.gestor_api.domain.cliente.TipoCliente;

public record ClienteCriarDTO(    String nome,
                                  String email,
                                  String telefone,
                                  TipoCliente tipoCliente,
                                  String observacao,
                                  Long indicadoPor) {
}
