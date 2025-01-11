package com.rr.gestor_api.controllers;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.cliente.ClienteRetornoDTO;
import com.rr.gestor_api.dto.entrega.EntregaCriarDTO;
import com.rr.gestor_api.dto.erro.ErroDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.repositories.EntregaRepository;
import com.rr.gestor_api.service.Entrega.EntregaService;
import com.rr.gestor_api.service.cliente.ClienteService;
import com.rr.gestor_api.service.erro.ErroException;
import com.rr.gestor_api.service.usuario.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/entrega")
@RequiredArgsConstructor
public class EntregaController {
    private final EntregaRepository repository;
    private final EntregaService service;
    private final UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity criar(@RequestBody EntregaCriarDTO body) {
        service.criarEntrega(body);

        return ResponseEntity.ok("Entrega cadastrada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEntrega(@PathVariable("id") Long id){
        try {
            service.deletarEntrega(id);
            return ResponseEntity.ok("Entrega deletada com sucesso!");
        }catch (ErroException e) {
            // Caso a entrega não seja encontrada, retorna erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(e.getCampo(),e.getMessage()));
        }

    }



}
