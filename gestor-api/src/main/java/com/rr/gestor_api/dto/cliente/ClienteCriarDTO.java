package com.rr.gestor_api.dto.cliente;

public record ClienteCriarDTO(    String nome,
                                  String email,
                                  String telefone,
                                  String tipoCliente,
                                  String observacao,
                                  Long indicadoPor) {
}
