package com.rr.gestor_api.dto.trabalho;

import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.entrega.StatusEntrega;
import com.rr.gestor_api.domain.parcela.StatusParcela;
import com.rr.gestor_api.domain.trabalho.TipoPagamento;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.entrega.EntregaRetornoPuraDTO;
import com.rr.gestor_api.dto.parcela.ParcelaRetornoPuraDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record TrabalhoRetornoDTO(Long id,
                                 String responsavelEmail,
                                 String nomeResponsavel,
                                 Long clienteId,
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
                                 StatusEntrega statusEntregas,
                                 StatusParcela statusParcelas,
                                 TipoPagamento tipoPagamento,
                                 List<EntregaRetornoPuraDTO> entregas,
                                 List<ParcelaRetornoPuraDTO> parcelas) {

    public TrabalhoRetornoDTO(Trabalho trabalho){
        this(trabalho.getId(),trabalho.getResponsavel().getEmail(),trabalho.getResponsavel().getNome(),trabalho.getCliente().getId(),trabalho.getCliente().getNome(),trabalho.getCliente().getEmail(),trabalho.getCliente().getTelefone(),trabalho.getTipoTrabalho(),trabalho.getFaculdade(), trabalho.getCurso(), trabalho.getTema(), trabalho.getCaminhoPendrive(), trabalho.getCaminhoDrive(), trabalho.getObservacao(), trabalho.getValorTotal(),trabalho.getStatusEntregas(),trabalho.getStatusParcelas(),trabalho.getTipoPagamento(),trabalho.getEntregas().stream().map(EntregaRetornoPuraDTO::new).toList(),trabalho.getParcelas().stream().map(ParcelaRetornoPuraDTO::new).toList());
    }
}
