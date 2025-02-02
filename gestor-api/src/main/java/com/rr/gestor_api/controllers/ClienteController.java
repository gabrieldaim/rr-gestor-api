package com.rr.gestor_api.controllers;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.cliente.ClienteResumoRetornoDTO;
import com.rr.gestor_api.dto.cliente.ClienteRetornoDTO;
import com.rr.gestor_api.dto.erro.ErroDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoAtualizarDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoRetornoDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoRetornoDTO;
import com.rr.gestor_api.dto.usuario.LoginResponseDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.service.cliente.ClienteService;
import com.rr.gestor_api.service.erro.ErroException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteRepository repository;
    private final ClienteService service;

    @PostMapping("/criar")
    public ResponseEntity criar(@RequestBody ClienteCriarDTO body) {

        try{
            Cliente cliente = service.criarCliente(body);
            return ResponseEntity.ok(new ClienteRetornoDTO(cliente));
        }catch (ErroException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(e.getCampo(),e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteRetornoDTO> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = service.buscarClientePorId(id);
        return ResponseEntity.ok(new ClienteRetornoDTO(cliente));
    }

    @GetMapping("/todosResumo")
    public ResponseEntity<List<ClienteResumoRetornoDTO>> buscarTrabalhoPorId() {
        List<ClienteResumoRetornoDTO> clientes = service.listarTodosClientes();
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable("id") Long id) {
        try {
            service.deletarCliente(id);
            return ResponseEntity.ok("Cliente deletado com sucesso!");
        } catch (ErroException e) {
            // Caso o Cliente não seja encontrado, retorna erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(e.getCampo(), e.getMessage()));
        }
    }

@PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable("id") Long id,
            @RequestBody ClienteCriarDTO clienteAtualizar) {

        try {
            // Chama o serviço para atualizar o trabalho
            Cliente clienteAtualizado = service.atualizarCliente(id, clienteAtualizar);

            // Retorna o trabalho atualizado como DTO
            ClienteRetornoDTO clienteRetornoDTO = new ClienteRetornoDTO(clienteAtualizado);

            return ResponseEntity.ok(clienteRetornoDTO);
        } catch (ErroException e) {
            // Caso o trabalho não seja encontrado, retorna erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(e.getCampo(),e.getMessage()));
        }
    }
}
