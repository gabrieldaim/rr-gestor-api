package com.rr.gestor_api.controllers;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.cliente.ClienteRetornoDTO;
import com.rr.gestor_api.dto.usuario.LoginResponseDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.service.cliente.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;



@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteRepository repository;
    private final ClienteService service;

    @PostMapping("/criar")
    public ResponseEntity criar(@RequestBody ClienteCriarDTO body) {
        Optional<Cliente> cliente = repository.findByEmail(body.email());
        if (cliente.isEmpty()) {
            service.criarCliente(body);
        }
        return ResponseEntity.badRequest().body("Usuário já cadastrado no sistema");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteRetornoDTO> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = service.buscarClientePorId(id);
        return ResponseEntity.ok(new ClienteRetornoDTO(cliente));
    }
}
