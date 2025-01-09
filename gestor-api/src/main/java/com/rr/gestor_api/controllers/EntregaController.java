package com.rr.gestor_api.controllers;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.cliente.ClienteRetornoDTO;
import com.rr.gestor_api.dto.entrega.EntregaCriarDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.repositories.EntregaRepository;
import com.rr.gestor_api.service.Entrega.EntregaService;
import com.rr.gestor_api.service.cliente.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/entrega")
@RequiredArgsConstructor
public class EntregaController {
    private final EntregaRepository repository;
    private final EntregaService service;

    @PostMapping("/criar")
    public ResponseEntity criar(@RequestBody EntregaCriarDTO body) {
        service.criarEntrega(body);

        return ResponseEntity.ok("Entrega cadastrada com sucesso!");
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ClienteRetornoDTO> buscarClientePorId(@PathVariable Long id) {
//        Cliente cliente = service.buscarClientePorId(id);
//        return ResponseEntity.ok(new ClienteRetornoDTO(cliente));
//    }
}
