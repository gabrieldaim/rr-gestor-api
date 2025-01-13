package com.rr.gestor_api.domain.parcela;

import com.rr.gestor_api.domain.trabalho.Trabalho;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trabalho_id")
    private Trabalho trabalho;

    private String nome;

    private BigDecimal valor;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatusParcela status;

}

