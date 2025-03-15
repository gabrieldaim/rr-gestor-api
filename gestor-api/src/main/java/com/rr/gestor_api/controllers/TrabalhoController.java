package com.rr.gestor_api.controllers;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.cliente.ClienteRetornoDTO;
import com.rr.gestor_api.dto.erro.ErroDTO;
import com.rr.gestor_api.dto.trabalho.*;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import com.rr.gestor_api.service.cliente.ClienteService;
import com.rr.gestor_api.service.erro.ErroException;
import com.rr.gestor_api.service.trabalho.TrabalhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@RestController
@RequestMapping("/trabalho")
@RequiredArgsConstructor
public class TrabalhoController {
    private static final AtomicReference<LocalDate> ultimaExecucao = new AtomicReference<>(LocalDate.MIN);
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

    @GetMapping("/todosResumo")
    public ResponseEntity<List<TrabalhoResumoProxEntregasRetornoDTO>> buscarTrabalhos() {
        List<TrabalhoResumoProxEntregasRetornoDTO> trabalhos = service.listarTodosTrabalhos();
        return ResponseEntity.ok(trabalhos);
    }

    @GetMapping("/todosResumoEmail/{email}")
    public ResponseEntity<List<TrabalhoResumoProxEntregasRetornoDTO>> buscarTrabalhosEmail(@PathVariable String email) {
        List<TrabalhoResumoProxEntregasRetornoDTO> trabalhos = service.listarTodosTrabalhosEmail(email);
        return ResponseEntity.ok(trabalhos);
    }

    // @GetMapping("/todosResumoParcelas")
    // public ResponseEntity<List<TrabalhoResumoParcelasRetornoDTO>> buscarTrabalhoParcela() {
    //     List<TrabalhoResumoParcelasRetornoDTO> trabalhos = service.listarTodosTrabalhosParcela();
    //     return ResponseEntity.ok(trabalhos);
    // }

    @GetMapping("/todosResumoProxParcelas")
    public ResponseEntity<List<TrabalhoResumoParcelasRetornoDTO>> buscarTrabalhoProxParcela() {
        List<TrabalhoResumoParcelasRetornoDTO> trabalhos = service.listarTodosTrabalhosProxParcela();
        return ResponseEntity.ok(trabalhos);
    }

    @GetMapping("/todosResumoProxEntregas")
    public ResponseEntity<List<TrabalhoResumoProxEntregasRetornoDTO>> buscarTrabalhoProxEntrega() {
        List<TrabalhoResumoProxEntregasRetornoDTO> trabalhos = service.listarTodosTrabalhosProxEntrega();
        return ResponseEntity.ok(trabalhos);
    }

    @GetMapping("/todosResumoMeusTrabalhos")
    public ResponseEntity<List<TrabalhoResumoProxEntregasRetornoDTO>> buscarMeusTrabalho() {
        LocalDate hoje = LocalDate.now();
        
        // Se for a primeira execução do dia
        if (!hoje.equals(ultimaExecucao.get())) {
            ultimaExecucao.set(hoje); // Atualiza a última data
            service.atualizarStatusTrabalhos(); // Executa a função adicional
        }
        List<TrabalhoResumoProxEntregasRetornoDTO> trabalhos = service.listarMeusTrabalhos();
        return ResponseEntity.ok(trabalhos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTrabalho(
            @PathVariable("id") Long id,
            @RequestBody TrabalhoAtualizarDTO trabalhoAtualizarDTO) {

        try {
            // Chama o serviço para atualizar o trabalho
            Trabalho trabalhoAtualizado = service.atualizarTrabalho(id, trabalhoAtualizarDTO);

            // Retorna o trabalho atualizado como DTO
            TrabalhoRetornoDTO trabalhoRetornoDTO = new TrabalhoRetornoDTO(trabalhoAtualizado);

            return ResponseEntity.ok(trabalhoRetornoDTO);
        } catch (ErroException e) {
            // Caso o trabalho não seja encontrado, retorna erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(e.getCampo(),e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarTrabalho(@PathVariable("id") Long id){
        try {
            service.deletarTrabalho(id);
            return ResponseEntity.ok("Trabalho deletado com sucesso!");
        }catch (ErroException e) {
            // Caso o trabalho não seja encontrado, retorna erro
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(e.getCampo(),e.getMessage()));
        }

    }
}
