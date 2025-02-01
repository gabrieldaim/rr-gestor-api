package com.rr.gestor_api.service.Entrega;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.entrega.EntregaCriarDTO;
import com.rr.gestor_api.repositories.EntregaRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import com.rr.gestor_api.service.erro.ErroException;
import com.rr.gestor_api.service.usuario.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final TrabalhoRepository trabalhoRepository;
    private final UsuarioService usuarioService;

    public EntregaService(EntregaRepository entregaRepository, TrabalhoRepository trabalhoRepository, UsuarioService usuarioService) {
        this.entregaRepository = entregaRepository;
        this.trabalhoRepository = trabalhoRepository;
        this.usuarioService = usuarioService;
    }

    // Criar Entrega
    @Transactional
    public Entrega criarEntrega(EntregaCriarDTO entregaCriarDTO) {
        Trabalho trabalho = trabalhoRepository.findById(entregaCriarDTO.trabalhoId())
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado com o ID: " + entregaCriarDTO.trabalhoId()));

        Entrega entrega = new Entrega();
        entrega.setTrabalho(trabalho);
        entrega.setNome(entregaCriarDTO.nome());
        entrega.setData(entregaCriarDTO.data());
        entrega.setStatus(entregaCriarDTO.status());

        usuarioService.usuarioIsResponsavel(entrega.getTrabalho().getResponsavel().getId());
        return entregaRepository.save(entrega);
    }

    // Deletar Entrega
    @Transactional
    public void deletarEntrega(Long id) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new ErroException("id","Entrega não encontrada com o ID: " + id));
        usuarioService.usuarioIsResponsavel(entrega.getTrabalho().getResponsavel().getId());

        entregaRepository.deleteById(id);
    }
}
