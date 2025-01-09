package com.rr.gestor_api.service.trabalho;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.dto.cliente.ClienteCriarDTO;
import com.rr.gestor_api.dto.entrega.EntregaAtualizarDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoAtualizarDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoCriarDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoRetornoDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import com.rr.gestor_api.service.erro.ErroException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Entrega>entregas = trabalhoInputDTO.entregas().stream().map(entregaCriarDTO -> {
            Entrega entrega = new Entrega();
            entrega.setTrabalho(trabalho);
            entrega.setNome(entregaCriarDTO.nome());
            entrega.setData(entregaCriarDTO.data());
            entrega.setStatus(entregaCriarDTO.status());
            return entrega;
        }).toList();
        trabalho.setEntregas(entregas);

        return trabalhoRepository.save(trabalho);
    }

    // Atualizar Trabalho
    @Transactional
    public Trabalho atualizarTrabalho(Long id, TrabalhoAtualizarDTO trabalhoInputDTO) {
        // Busca o trabalho pelo ID
        Trabalho trabalho = trabalhoRepository.findById(id)
                .orElseThrow(() -> new ErroException("id","Trabalho não encontrado com o ID: " + id));

        Cliente cliente = clienteRepository.findById(trabalhoInputDTO.clienteId())
                .orElseThrow(() -> new ErroException("clienteId","Cliente não encontrado com o ID: " + trabalhoInputDTO.clienteId()));

        // Atualiza os campos do trabalho
        trabalho.setCliente(cliente);
        trabalho.setTipoTrabalho(trabalhoInputDTO.tipoTrabalho());
        trabalho.setFaculdade(trabalhoInputDTO.faculdade());
        trabalho.setCurso(trabalhoInputDTO.curso());
        trabalho.setTema(trabalhoInputDTO.tema());
        trabalho.setCaminhoPendrive(trabalhoInputDTO.caminhoPendrive());
        trabalho.setCaminhoDrive(trabalhoInputDTO.caminhoDrive());
        trabalho.setObservacao(trabalhoInputDTO.observacao());
        trabalho.setValorTotal(trabalhoInputDTO.valorTotal());

        // Atualiza as entregas
        Map<Long, EntregaAtualizarDTO> entregasAtualizacaoMap = trabalhoInputDTO.entregas().stream()
                .collect(Collectors.toMap(EntregaAtualizarDTO::id, entrega -> entrega));

        trabalho.getEntregas().forEach(entrega -> {
            EntregaAtualizarDTO entregaAtualizarDTO = entregasAtualizacaoMap.get(entrega.getId());
            if (entregaAtualizarDTO != null) {
                entrega.setNome(entregaAtualizarDTO.nome());
                entrega.setData(entregaAtualizarDTO.data());
                entrega.setStatus(entregaAtualizarDTO.status());
            }
        });

        return trabalhoRepository.save(trabalho);
    }


    // Buscar trabalho por ID
    @Transactional
    public Trabalho buscarTrabalhoPorId(Long id) {
        return trabalhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado com o ID: " + id));
    }

    // Listar Todos os trabalhos
    @Transactional
    public List<TrabalhoResumoRetornoDTO> listarTodosTrabalhos() {

        return trabalhoRepository.findTrabalhosWithMinEntregaDate();
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
