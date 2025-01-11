package com.rr.gestor_api.dto.parcela;

import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.entrega.StatusEntrega;
import com.rr.gestor_api.domain.parcela.Parcela;
import com.rr.gestor_api.domain.parcela.StatusParcela;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParcelaRetornoPuraDTO(
        Long id,
        String nome,
        BigDecimal valor,
                              LocalDate data,
                              StatusParcela status) {
    public ParcelaRetornoPuraDTO(Parcela parcela){
        this(parcela.getId(), parcela.getNome(),parcela.getValor(), parcela.getData(), parcela.getStatus());
    }
}
