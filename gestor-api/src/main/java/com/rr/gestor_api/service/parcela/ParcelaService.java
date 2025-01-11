package com.rr.gestor_api.service.parcela;

import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.parcela.Parcela;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.parcela.ParcelaCriarDTO;
import com.rr.gestor_api.repositories.ParcelaRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import com.rr.gestor_api.service.erro.ErroException;
import com.rr.gestor_api.service.usuario.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ParcelaService {

    private final ParcelaRepository parcelaRepository;
    private final TrabalhoRepository trabalhoRepository;
    private final UsuarioService usuarioService;

    public ParcelaService(ParcelaRepository parcelaRepository, TrabalhoRepository trabalhoRepository, UsuarioService usuarioService) {
        this.parcelaRepository = parcelaRepository;
        this.trabalhoRepository = trabalhoRepository;
        this.usuarioService = usuarioService;
    }

    // Criar Parcela
    @Transactional
    public Parcela criarParcela(ParcelaCriarDTO parcelaCriarDTO) {
        Trabalho trabalho = trabalhoRepository.findById(parcelaCriarDTO.trabalhoId())
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado com o ID: " + parcelaCriarDTO.trabalhoId()));

        Parcela parcela = new Parcela();
        parcela.setTrabalho(trabalho);
        parcela.setNome(parcelaCriarDTO.nome());
        parcela.setData(parcelaCriarDTO.data());
        parcela.setStatus(parcelaCriarDTO.status());
        parcela.setValor(parcelaCriarDTO.valor());

        return parcelaRepository.save(parcela);
    }

    // Deletar Parcela
    @Transactional
    public void deletarParcela(Long id) {
        Parcela parcela = parcelaRepository.findById(id)
                .orElseThrow(() -> new ErroException("id","Parcela não encontrada com o ID: " + id));
        usuarioService.usuarioIsResponsavel(parcela.getTrabalho().getResponsavel().getId());

        parcelaRepository.deleteById(id);
    }
}
