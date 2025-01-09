package com.rr.gestor_api.dto.entrega;

import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.entrega.StatusEntrega;

import java.time.LocalDate;

public record EntregaRetornoPuraDTO(
        Long id,
        String nome,
                              LocalDate data,
                              StatusEntrega status) {
    public EntregaRetornoPuraDTO(Entrega entrega){
        this(entrega.getId(), entrega.getNome(), entrega.getData(),entrega.getStatus());
    }
}
