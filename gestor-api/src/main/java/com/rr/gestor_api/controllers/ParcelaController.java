package com.rr.gestor_api.controllers;

import com.rr.gestor_api.dto.entrega.EntregaCriarDTO;
import com.rr.gestor_api.dto.erro.ErroDTO;
import com.rr.gestor_api.dto.parcela.ParcelaCriarDTO;
import com.rr.gestor_api.repositories.EntregaRepository;
import com.rr.gestor_api.repositories.ParcelaRepository;
import com.rr.gestor_api.service.Entrega.EntregaService;
import com.rr.gestor_api.service.erro.ErroException;
import com.rr.gestor_api.service.parcela.ParcelaService;
import com.rr.gestor_api.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/parcela")
@RequiredArgsConstructor
public class ParcelaController {
    private final ParcelaRepository repository;
    private final ParcelaService service;
    private final UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity criar(@RequestBody ParcelaCriarDTO body) {
        service.criarParcela(body);

        return ResponseEntity.ok("Parcela cadastrada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarParcela(@PathVariable("id") Long id){
        try {
            service.deletarParcela(id);
            return ResponseEntity.ok("Parcela deletada com sucesso!");
        }catch (ErroException e) {
            // Caso a parcela não seja encontrada, retorna erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(e.getCampo(),e.getMessage()));
        }

    }



}
