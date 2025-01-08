package com.rr.gestor_api.service.trabalho;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoCriarDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrabalhoService {

    private final TrabalhoRepository trabalhoRepository;
    private final ClienteRepository clienteRepository;

    public TrabalhoService(TrabalhoRepository trabalhoRepository, ClienteRepository clienteRepository) {
        this.trabalhoRepository = trabalhoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Trabalho criarTrabalho(TrabalhoCriarDTO trabalhoInputDTO) {
        // Busca o cliente pelo ID
        Cliente cliente = clienteRepository.findById(trabalhoInputDTO.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + trabalhoInputDTO.clienteId()));

        // Cria o trabalho associado ao cliente
        Trabalho trabalho = new Trabalho();
        trabalho.setCliente(cliente);
        trabalho.setTipoTrabalho(trabalhoInputDTO.tipoTrabalho());
        trabalho.setFaculdade(trabalhoInputDTO.faculdade());
        trabalho.setCurso(trabalhoInputDTO.curso());
        trabalho.setTema(trabalhoInputDTO.tema());
        trabalho.setCaminhoPendrive(trabalhoInputDTO.caminhoPendrive());
        trabalho.setCaminhoDrive(trabalhoInputDTO.caminhoDrive());
        trabalho.setObservacao(trabalhoInputDTO.observacao());
        trabalho.setValorTotal(trabalhoInputDTO.valorTotal());

        return trabalhoRepository.save(trabalho);
    }

    // Atualizar Trabalho
    @Transactional
    public Trabalho atualizarTrabalho(Long id, TrabalhoCriarDTO trabalhoCriarDTO) {
        Trabalho trabalho = trabalhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado com o ID: " + id));

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));

        // Atualizando os dados do trabalho
        trabalho.setCliente(cliente);
        trabalho.setTipoTrabalho(trabalhoCriarDTO.tipoTrabalho());
        trabalho.setFaculdade(trabalhoCriarDTO.faculdade());
        trabalho.setCurso(trabalhoCriarDTO.curso());
        trabalho.setTema(trabalhoCriarDTO.tema());
        trabalho.setCaminhoPendrive(trabalhoCriarDTO.caminhoPendrive());
        trabalho.setCaminhoDrive(trabalhoCriarDTO.caminhoDrive());
        trabalho.setObservacao(trabalhoCriarDTO.observacao());
        trabalho.setValorTotal(trabalhoCriarDTO.valorTotal());

        return trabalhoRepository.save(trabalho);
    }

    // Buscar Cliente por ID
    @Transactional
    public Trabalho buscarTrabalhoPorId(Long id) {
        return trabalhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado com o ID: " + id));
    }

    // Listar Todos os Clientes
    @Transactional
    public List<Trabalho> listarTodosTrabalhos() {
        return trabalhoRepository.findAll();
    }

    // Deletar Cliente
    @Transactional
    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com o ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
