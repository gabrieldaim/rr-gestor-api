package com.rr.gestor_api.service.cliente;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final TrabalhoRepository trabalhoRepository;

    public ClienteService(ClienteRepository clienteRepository, TrabalhoRepository trabalhoRepository) {
        this.clienteRepository = clienteRepository;
        this.trabalhoRepository = trabalhoRepository;
    }

    // Criar Cliente
    @Transactional
    public Cliente criarCliente(ClienteCriarDTO clienteInputDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteInputDTO.nome());
        cliente.setEmail(clienteInputDTO.email());
        cliente.setTelefone(clienteInputDTO.telefone());
        cliente.setTipoCliente(clienteInputDTO.tipoCliente());
        cliente.setObservacao(clienteInputDTO.observacao());

        return clienteRepository.save(cliente);
    }

    // Atualizar Cliente
    @Transactional
    public Cliente atualizarCliente(Long id, ClienteCriarDTO clienteInputDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));

        cliente.setNome(clienteInputDTO.nome());
        cliente.setEmail(clienteInputDTO.email());
        cliente.setTelefone(clienteInputDTO.telefone());
        cliente.setTipoCliente(clienteInputDTO.tipoCliente());
        cliente.setObservacao(clienteInputDTO.observacao());

        return clienteRepository.save(cliente);
    }

    // Buscar Cliente por ID
    @Transactional
    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
    }

    // Listar Todos os Clientes
    @Transactional
    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
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
