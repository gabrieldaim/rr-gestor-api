package com.rr.gestor_api.dto.trabalho;

import java.math.BigDecimal;

public record TrabalhoCriarDTO(Long clienteId,
                               String tipoTrabalho,
                               String faculdade,
                               String curso,
                               String tema,
                               String caminhoPendrive,
                               String caminhoDrive,
                               String observacao,
                               BigDecimal valorTotal) {
}
