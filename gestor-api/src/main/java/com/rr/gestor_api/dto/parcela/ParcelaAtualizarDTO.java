package com.rr.gestor_api.dto.parcela;

import com.rr.gestor_api.domain.entrega.StatusEntrega;
import com.rr.gestor_api.domain.parcela.StatusParcela;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParcelaAtualizarDTO(
                              Long id,
                              String nome,
                              BigDecimal valor,
                              LocalDate data,
                              StatusParcela status) {
}
