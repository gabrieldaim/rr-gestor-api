package com.rr.gestor_api.service.Entrega;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.entrega.EntregaCriarDTO;
import com.rr.gestor_api.repositories.EntregaRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final TrabalhoRepository trabalhoRepository;

    public EntregaService(EntregaRepository entregaRepository, TrabalhoRepository trabalhoRepository) {
        this.entregaRepository = entregaRepository;
        this.trabalhoRepository = trabalhoRepository;
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

        return entregaRepository.save(entrega);
    }

//    // Atualizar Entrega
//    @Transactional
//    public Entrega atualizarEntrega(EntregaCriarDTO entregaCriarDTO) {
//        Entrega entrega = entregaRepository.findById(entregaCriarDTO.)
//                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
//
//        cliente.setNome(clienteInputDTO.nome());
//        cliente.setEmail(clienteInputDTO.email());
//        cliente.setTelefone(clienteInputDTO.telefone());
//        cliente.setTipoCliente(clienteInputDTO.tipoCliente());
//        cliente.setObservacao(clienteInputDTO.observacao());
//
//        return entregaRepository.save(cliente);
//    }
//
//    // Listar Todos os Clientes
//    @Transactional
//    public List<Cliente> listarTodosClientes() {
//        return entregaRepository.findAll();
//    }
//
//    // Deletar Cliente
//    @Transactional
//    public void deletarCliente(Long id) {
//        if (!entregaRepository.existsById(id)) {
//            throw new RuntimeException("Cliente não encontrado com o ID: " + id);
//        }
//        entregaRepository.deleteById(id);
//    }
}
