package com.rr.gestor_api.dto.trabalho;

import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.dto.entrega.EntregaCriarDTO;

import java.math.BigDecimal;
import java.util.List;

public record TrabalhoCriarDTO(Long clienteId,
                               String tipoTrabalho,
                               String faculdade,
                               String curso,
                               String tema,
                               String caminhoPendrive,
                               String caminhoDrive,
                               String observacao,
                               BigDecimal valorTotal,
                               List<EntregaCriarDTO>entregas) {
}
