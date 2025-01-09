package com.rr.gestor_api.dto.trabalho;

import com.rr.gestor_api.dto.entrega.EntregaAtualizarDTO;
import com.rr.gestor_api.dto.entrega.EntregaCriarDTO;

import java.math.BigDecimal;
import java.util.List;

public record TrabalhoAtualizarDTO(Long clienteId,
                                   String tipoTrabalho,
                                   String faculdade,
                                   String curso,
                                   String tema,
                                   String caminhoPendrive,
                                   String caminhoDrive,
                                   String observacao,
                                   BigDecimal valorTotal,
                                   List<EntregaAtualizarDTO>entregas) {
}
