package com.rr.gestor_api.domain.trabalho;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.entrega.StatusEntrega;
import com.rr.gestor_api.domain.parcela.Parcela;
import com.rr.gestor_api.domain.parcela.StatusParcela;
import com.rr.gestor_api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private TipoTrabalho tipoTrabalho;

    private String faculdade;

    private String curso;

    private String tema;

    private String caminhoPendrive;

    private String caminhoDrive;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private StatusEntrega statusEntregas;

    @Enumerated(EnumType.STRING)
    private StatusParcela statusParcelas;

    private TipoPagamento tipoPagamento;

    private BigDecimal valorTotal;

    private BigDecimal valorPago;

    @OneToMany(mappedBy = "trabalho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entrega> entregas = new ArrayList<>();

    @OneToMany(mappedBy = "trabalho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parcela> parcelas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "responsavel_id", referencedColumnName = "id")
    private Usuario responsavel;
}

