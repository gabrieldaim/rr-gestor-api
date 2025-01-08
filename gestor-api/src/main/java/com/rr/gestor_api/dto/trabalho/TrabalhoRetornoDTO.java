package com.rr.gestor_api.dto.trabalho;

import com.rr.gestor_api.domain.trabalho.Trabalho;

import java.math.BigDecimal;

public record TrabalhoRetornoDTO(Long clienteId,
                                 String tipoTrabalho,
                                 String faculdade,
                                 String curso,
                                 String tema,
                                 String caminhoPendrive,
                                 String caminhoDrive,
                                 String observacao,
                                 BigDecimal valorTotal) {

    public TrabalhoRetornoDTO(Trabalho trabalho){
        this(trabalho.getCliente().getId(),trabalho.getTipoTrabalho(),trabalho.getFaculdade(), trabalho.getCurso(), trabalho.getTema(), trabalho.getCaminhoPendrive(), trabalho.getCaminhoDrive(), trabalho.getObservacao(), trabalho.getValorTotal());
    }
}
