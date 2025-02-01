package com.rr.gestor_api.dto.trabalho;

import com.rr.gestor_api.domain.trabalho.TipoTrabalho;
import com.rr.gestor_api.dto.entrega.EntregaAtualizarDTO;
import com.rr.gestor_api.dto.entrega.EntregaCriarDTO;
import com.rr.gestor_api.dto.parcela.ParcelaAtualizarDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record TrabalhoAtualizarDTO(Long clienteId,
                                   String responsavelEmail,
                                   TipoTrabalho tipoTrabalho,
                                   String faculdade,
                                   String curso,
                                   String tema,
                                   String caminhoPendrive,
                                   String caminhoDrive,
                                   String observacao,
                                   BigDecimal valorTotal,
                                   List<EntregaAtualizarDTO>entregas,
                                   List<ParcelaAtualizarDTO>parcelas) {
}
