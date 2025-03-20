package com.rr.gestor_api.dto.trabalho;


import com.rr.gestor_api.domain.entrega.StatusEntrega;
import com.rr.gestor_api.domain.parcela.StatusParcela;
import com.rr.gestor_api.domain.trabalho.TipoTrabalho;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TrabalhoResumoParcelasRetornoDTO {

    private Long id;
    private String nome;
    private String responsavel;
    private String tema;
    private TipoTrabalho tipoTrabalho;
    private LocalDate proxPrazo;
    private StatusParcela statusParcela;

    public TrabalhoResumoParcelasRetornoDTO(Long id, String nome, String responsavel, String tema, TipoTrabalho tipoTrabalho, LocalDate proxPrazo, StatusParcela statusParcela) {
        this.id = id;
        this.nome = nome;
        this.responsavel = responsavel;
        this.tema = tema;
        this.tipoTrabalho = tipoTrabalho;
        this.proxPrazo = proxPrazo;
        this.statusParcela = statusParcela;
    }

}

