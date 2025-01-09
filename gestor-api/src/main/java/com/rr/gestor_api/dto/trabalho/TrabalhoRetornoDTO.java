package com.rr.gestor_api.dto.trabalho;

import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.entrega.EntregaRetornoPuraDTO;

import java.math.BigDecimal;
import java.util.List;

public record TrabalhoRetornoDTO(Long id,
                                 Long ClienteId,
                                 String nome,
                                 String email,
                                 String telefone,
                                 String tipoTrabalho,
                                 String faculdade,
                                 String curso,
                                 String tema,
                                 String caminhoPendrive,
                                 String caminhoDrive,
                                 String observacao,
                                 BigDecimal valorTotal,
                                 List<EntregaRetornoPuraDTO> entregas) {

    public TrabalhoRetornoDTO(Trabalho trabalho){
        this(trabalho.getId(),trabalho.getCliente().getId(),trabalho.getCliente().getNome(),trabalho.getCliente().getEmail(),trabalho.getCliente().getTelefone(),trabalho.getTipoTrabalho(),trabalho.getFaculdade(), trabalho.getCurso(), trabalho.getTema(), trabalho.getCaminhoPendrive(), trabalho.getCaminhoDrive(), trabalho.getObservacao(), trabalho.getValorTotal(),trabalho.getEntregas().stream().map(EntregaRetornoPuraDTO::new).toList());
    }
}
