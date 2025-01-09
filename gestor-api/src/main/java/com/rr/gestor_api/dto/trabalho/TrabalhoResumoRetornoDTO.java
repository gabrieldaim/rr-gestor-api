package com.rr.gestor_api.dto.trabalho;


import com.rr.gestor_api.domain.entrega.StatusEntrega;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TrabalhoResumoRetornoDTO {

    private Long id;
    private String nome;
    private String tema;
    private String tipoTrabalho;
    private LocalDate proxPrazo;
    private StatusEntrega statusEntrega;

    public TrabalhoResumoRetornoDTO(Long id, String nome, String tema, String tipoTrabalho, LocalDate proxPrazo, StatusEntrega statusEntrega) {
        this.id = id;
        this.nome = nome;
        this.tema = tema;
        this.tipoTrabalho = tipoTrabalho;
        this.proxPrazo = proxPrazo;
        this.statusEntrega = statusEntrega;
    }

    // Getters and setters
}

