package com.rr.gestor_api.dto.entrega;

import com.rr.gestor_api.domain.entrega.StatusEntrega;

import java.time.LocalDate;

public record EntregaCriarDTO(
        Long trabalhoId,
        String nome,
                              LocalDate data,
                              StatusEntrega status) {
}
