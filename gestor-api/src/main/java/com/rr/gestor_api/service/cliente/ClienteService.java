package com.rr.gestor_api.service.cliente;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.cliente.ClienteResumoRetornoDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoRetornoDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import com.rr.gestor_api.service.erro.ErroException;
import com.rr.gestor_api.service.usuario.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final TrabalhoRepository trabalhoRepository;
    private final UsuarioService usuarioService;

    public ClienteService(ClienteRepository clienteRepository, TrabalhoRepository trabalhoRepository, UsuarioService usuarioService) {
        this.clienteRepository = clienteRepository;
        this.trabalhoRepository = trabalhoRepository;
        this.usuarioService = usuarioService;
    }

    // Criar Cliente
    @Transactional
    public Cliente criarCliente(ClienteCriarDTO clienteInputDTO) {
        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(clienteInputDTO.email());
        Optional<Cliente> clienteIndicacao = Optional.empty();
        if(clienteInputDTO.indicadoPor() != null){
            clienteIndicacao = clienteRepository.findById(clienteInputDTO.indicadoPor());
        }

        if(clienteExistente.isEmpty()){
            Cliente cliente = new Cliente();
            cliente.setNome(clienteInputDTO.nome());
            cliente.setEmail(clienteInputDTO.email());
            cliente.setTelefone(clienteInputDTO.telefone());
            cliente.setTipoCliente(clienteInputDTO.tipoCliente());
            cliente.setObservacao(clienteInputDTO.observacao());
            clienteIndicacao.ifPresent(cliente::setIndicadoPor);

            return clienteRepository.save(cliente);
        }else{
            throw  new ErroException("email", "Usuário já cadastrado no sistema");
        }
        
    }

    // Atualizar Cliente
    @Transactional
    public Cliente atualizarCliente(Long id, ClienteCriarDTO clienteInputDTO) {
        usuarioService.userIsAuthorized();
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));

                Optional<Cliente> clienteIndicacao = Optional.empty();
        if(clienteInputDTO.indicadoPor() != null){
            clienteIndicacao = clienteRepository.findById(clienteInputDTO.indicadoPor());
        }

        cliente.setNome(clienteInputDTO.nome());
        cliente.setEmail(clienteInputDTO.email());
        cliente.setTelefone(clienteInputDTO.telefone());
        cliente.setTipoCliente(clienteInputDTO.tipoCliente());
        cliente.setObservacao(clienteInputDTO.observacao());
        clienteIndicacao.ifPresent(cliente::setIndicadoPor);

        return clienteRepository.save(cliente);
    }

    // Buscar Cliente por ID
    @Transactional
    public Cliente buscarClientePorId(Long id) {
        usuarioService.userIsAuthorized();
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
    }

    // Listar Todos os Clientes
    @Transactional
    public List<ClienteResumoRetornoDTO> listarTodosClientes() {
        return clienteRepository.findClientesComUltimoTrabalho();
    }

    // Deletar Cliente
    @Transactional
    public void deletarCliente(Long id) {
        usuarioService.userIsAuthorized();
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ErroException("id","Cliente não encontrado com o ID: " + id));
        
        clienteRepository.deleteById(id);
    }
}
