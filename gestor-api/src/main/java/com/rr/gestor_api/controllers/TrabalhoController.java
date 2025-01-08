package com.rr.gestor_api.controllers;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.cliente.ClienteRetornoDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoCriarDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoRetornoDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import com.rr.gestor_api.service.cliente.ClienteService;
import com.rr.gestor_api.service.trabalho.TrabalhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/trabalho")
@RequiredArgsConstructor
public class TrabalhoController {
    private final TrabalhoRepository repository;
    private final TrabalhoService service;

    @PostMapping("/criar")
    public ResponseEntity criar(@RequestBody TrabalhoCriarDTO body) {

        Trabalho trabalho = service.criarTrabalho(body);

        return ResponseEntity.ok().body(new TrabalhoRetornoDTO(trabalho));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabalhoRetornoDTO> buscarTrabalhoPorId(@PathVariable Long id) {
        Trabalho trabalho = service.buscarTrabalhoPorId(id);
        return ResponseEntity.ok(new TrabalhoRetornoDTO(trabalho));
    }
}
