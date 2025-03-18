package com.rr.gestor_api.service.trabalho;

import com.rr.gestor_api.domain.cliente.Cliente;
import com.rr.gestor_api.domain.entrega.Entrega;
import com.rr.gestor_api.domain.entrega.StatusEntrega;
import com.rr.gestor_api.domain.parcela.Parcela;
import com.rr.gestor_api.domain.parcela.StatusParcela;
import com.rr.gestor_api.domain.trabalho.Trabalho;
import com.rr.gestor_api.domain.usuario.Usuario;
import com.rr.gestor_api.dto.entrega.EntregaAtualizarDTO;
import com.rr.gestor_api.dto.parcela.ParcelaAtualizarDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoAtualizarDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoCriarDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoParcelasRetornoDTO;
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoProxEntregasRetornoDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import com.rr.gestor_api.repositories.UsuarioRepository;
import com.rr.gestor_api.service.erro.ErroException;
import com.rr.gestor_api.service.usuario.UsuarioService;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrabalhoService {

    private static final Logger logger = LoggerFactory.getLogger(TrabalhoService.class);


    private final TrabalhoRepository trabalhoRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public TrabalhoService(TrabalhoRepository trabalhoRepository, ClienteRepository clienteRepository, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.trabalhoRepository = trabalhoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    public Trabalho criarTrabalho(TrabalhoCriarDTO trabalhoInputDTO) {
        // Busca o cliente pelo ID
        Cliente cliente = clienteRepository.findById(trabalhoInputDTO.clienteId())
                .orElseThrow(() -> new ErroException("clienteId", "Cliente não encontrado com o ID: " + trabalhoInputDTO.clienteId()));

        Usuario usuario = usuarioRepository.findByEmail(trabalhoInputDTO.responsavelEmail())
                .orElseThrow(() -> new ErroException("responsavelEmail", "Usuario não encontrado com o email: " + trabalhoInputDTO.responsavelEmail()));

        // Cria o trabalho associado ao cliente
        Trabalho trabalho = new Trabalho();
        trabalho.setResponsavel(usuario);
        trabalho.setCliente(cliente);
        trabalho.setTipoTrabalho(trabalhoInputDTO.tipoTrabalho());
        trabalho.setFaculdade(trabalhoInputDTO.faculdade());
        trabalho.setCurso(trabalhoInputDTO.curso());
        trabalho.setTema(trabalhoInputDTO.tema());
        trabalho.setCaminhoPendrive(trabalhoInputDTO.caminhoPendrive());
        trabalho.setCaminhoDrive(trabalhoInputDTO.caminhoDrive());
        trabalho.setObservacao(trabalhoInputDTO.observacao());
        trabalho.setValorTotal(trabalhoInputDTO.valorTotal());
        trabalho.setTipoPagamento(trabalhoInputDTO.tipoPagamento());

        // Atualiza o status do trabalho com base nos status das entregas
        if (trabalhoInputDTO.entregas().stream().anyMatch(entrega -> entrega.status() == StatusEntrega.ATRASADA)) {
            trabalho.setStatusEntregas(StatusEntrega.ATRASADA);
        } else if (trabalhoInputDTO.entregas().stream().anyMatch(entrega -> entrega.status() == StatusEntrega.EM_REVISAO)) {
            trabalho.setStatusEntregas(StatusEntrega.EM_REVISAO);
        } else if (trabalhoInputDTO.entregas().stream().anyMatch(entrega -> entrega.status() == StatusEntrega.EM_ANDAMENTO)) {
            trabalho.setStatusEntregas(StatusEntrega.EM_ANDAMENTO);
        } else if (trabalhoInputDTO.entregas().stream().anyMatch(entrega -> entrega.status() == StatusEntrega.NAO_INICIADA)) {
            trabalho.setStatusEntregas(StatusEntrega.NAO_INICIADA);
        } else {
            trabalho.setStatusEntregas(StatusEntrega.CONCLUIDA);
        }

        //criando entregas
        List<Entrega>entregas = trabalhoInputDTO.entregas().stream().map(entregaCriarDTO -> {
            Entrega entrega = new Entrega();
            entrega.setTrabalho(trabalho);
            entrega.setNome(entregaCriarDTO.nome());
            entrega.setData(entregaCriarDTO.data());
            entrega.setStatus(entregaCriarDTO.status());
            return entrega;
        }).toList();
        trabalho.setEntregas(entregas);

        //criando parcelas
        List<Parcela>parcelas = trabalhoInputDTO.parcelas().stream().map(parcelaCriarDTO -> {
            Parcela parcela = new Parcela();
            parcela.setTrabalho(trabalho);
            parcela.setNome(parcelaCriarDTO.nome());
            parcela.setData(parcelaCriarDTO.data());
            parcela.setStatus(parcelaCriarDTO.status());
            parcela.setValor(parcelaCriarDTO.valor());
            return parcela;
        }).toList();
        trabalho.setParcelas(parcelas);

        // Atualiza o status do trabalho com base na entrega de menor data
        trabalho.getParcelas().stream()
        .sorted((e1, e2) -> e1.getData().compareTo(e2.getData()))
        .filter(parcela -> parcela.getStatus() != StatusParcela.PAGA)
        .findFirst()
        .ifPresent(parcela -> trabalho.setStatusParcelas(parcela.getStatus()));

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

        Usuario responsavel = usuarioRepository.findByEmail(trabalhoInputDTO.responsavelEmail())
                .orElseThrow(() -> new ErroException("responsavelEmail","Responsavel não encontrado com o Email: " + trabalhoInputDTO.responsavelEmail()));

        usuarioService.usuarioIsResponsavel(trabalho.getResponsavel().getId());

        // Atualiza os campos do trabalho
        trabalho.setCliente(cliente);
        trabalho.setResponsavel(responsavel);
        trabalho.setTipoTrabalho(trabalhoInputDTO.tipoTrabalho());
        trabalho.setFaculdade(trabalhoInputDTO.faculdade());
        trabalho.setCurso(trabalhoInputDTO.curso());
        trabalho.setTema(trabalhoInputDTO.tema());
        trabalho.setCaminhoPendrive(trabalhoInputDTO.caminhoPendrive());
        trabalho.setCaminhoDrive(trabalhoInputDTO.caminhoDrive());
        trabalho.setObservacao(trabalhoInputDTO.observacao());
        trabalho.setValorTotal(trabalhoInputDTO.valorTotal());
        trabalho.setTipoPagamento(trabalhoInputDTO.tipoPagamento());

        // Atualiza o status do trabalho com base nos status das entregas
        if (trabalhoInputDTO.entregas().stream().anyMatch(entrega -> entrega.status() == StatusEntrega.ATRASADA)) {
            trabalho.setStatusEntregas(StatusEntrega.ATRASADA);
        } else if (trabalhoInputDTO.entregas().stream().anyMatch(entrega -> entrega.status() == StatusEntrega.EM_REVISAO)) {
            trabalho.setStatusEntregas(StatusEntrega.EM_REVISAO);
        } else if (trabalhoInputDTO.entregas().stream().anyMatch(entrega -> entrega.status() == StatusEntrega.EM_ANDAMENTO)) {
            trabalho.setStatusEntregas(StatusEntrega.EM_ANDAMENTO);
        } else if (trabalhoInputDTO.entregas().stream().anyMatch(entrega -> entrega.status() == StatusEntrega.NAO_INICIADA)) {
            trabalho.setStatusEntregas(StatusEntrega.NAO_INICIADA);
        } else {
            trabalho.setStatusEntregas(StatusEntrega.CONCLUIDA);
        }

        // Atualiza as entregas
        Map<Long, EntregaAtualizarDTO> entregasAtualizacaoMap = trabalhoInputDTO.entregas().stream()
                .filter(nullEntrega -> nullEntrega.id() != null)
                .collect(Collectors.toMap(EntregaAtualizarDTO::id, entrega -> entrega));

        // Atualiza as entregas existentes e remove as que não estão no DTO
        trabalho.getEntregas().removeIf(entrega -> {
            EntregaAtualizarDTO entregaAtualizarDTO = entregasAtualizacaoMap.get(entrega.getId());
            if (entregaAtualizarDTO != null) {
            entrega.setNome(entregaAtualizarDTO.nome());
            entrega.setData(entregaAtualizarDTO.data());
            entrega.setStatus(entregaAtualizarDTO.status());
            return false;
            } else {
            return true;
            }
        });

        // Adiciona novas entregas que não possuem ID (novas entregas)
        trabalhoInputDTO.entregas().stream()
            .filter(entregaDTO -> entregaDTO.id() == null)
            .forEach(entregaDTO -> {
                Entrega novaEntrega = new Entrega();
                novaEntrega.setTrabalho(trabalho);
                novaEntrega.setNome(entregaDTO.nome());
                novaEntrega.setData(entregaDTO.data());
                novaEntrega.setStatus(entregaDTO.status());
                trabalho.getEntregas().add(novaEntrega);
            });

        // Atualiza as parcelas
        Map<Long, ParcelaAtualizarDTO> parcelasAtualizacaoMap = trabalhoInputDTO.parcelas().stream()
                .filter(nullParcela -> nullParcela.id() != null)
                .collect(Collectors.toMap(ParcelaAtualizarDTO::id, parcela -> parcela));

        // Atualiza as parcelas existentes e remove as que não estão no DTO
        trabalho.getParcelas().removeIf(parcela -> {
            ParcelaAtualizarDTO parcelaAtualizarDTO = parcelasAtualizacaoMap.get(parcela.getId());
            if (parcelaAtualizarDTO != null) {
            parcela.setNome(parcelaAtualizarDTO.nome());
            parcela.setData(parcelaAtualizarDTO.data());
            parcela.setStatus(parcelaAtualizarDTO.status());
            parcela.setValor(parcelaAtualizarDTO.valor());
            return false;
            } else {
            return true;
            }
        });

        // Adiciona novas parcelas que não possuem ID (novas parcelas)
        trabalhoInputDTO.parcelas().stream()
            .filter(parcelaDTO -> parcelaDTO.id() == null)
            .forEach(parcelaDTO -> {
            Parcela novaParcela = new Parcela();
            novaParcela.setTrabalho(trabalho);
            novaParcela.setNome(parcelaDTO.nome());
            novaParcela.setData(parcelaDTO.data());
            novaParcela.setStatus(parcelaDTO.status());
            novaParcela.setValor(parcelaDTO.valor());
            trabalho.getParcelas().add(novaParcela);
            });

        // Atualiza o status do trabalho com base na entrega de menor data
        trabalho.getParcelas().stream()
        .sorted((e1, e2) -> e1.getData().compareTo(e2.getData()))
        .filter(parcela -> parcela.getStatus() != StatusParcela.PAGA)
        .findFirst()
        .ifPresent(parcela -> trabalho.setStatusParcelas(parcela.getStatus()));

        return trabalhoRepository.save(trabalho);
    }


    // Buscar trabalho por ID
    @Transactional
    public Trabalho buscarTrabalhoPorId(Long id) {

        Trabalho trabalho = trabalhoRepository.findById(id)
                .orElseThrow(() -> new ErroException("id","Trabalho não encontrado com o ID: " + id));

                usuarioService.usuarioIsResponsavel(trabalho.getResponsavel().getId());

        return trabalho;
    }

    // // Listar Todos os trabalhos
    // @Transactional
    // public List<TrabalhoResumoRetornoDTO> listarTodosTrabalhos() {

    //     return trabalhoRepository.findTrabalhosWithMinEntregaDate();
    // }

        // Listar Todos os trabalhos
        @Transactional
        public List<TrabalhoResumoProxEntregasRetornoDTO> listarTodosTrabalhos() {
            usuarioService.userIsAuthorized();
            return trabalhoRepository.findAllTrabalhos();
        }
    

    @Transactional
    public List<TrabalhoResumoProxEntregasRetornoDTO> listarTodosTrabalhosEmail(String email) {

        return trabalhoRepository.findTrabalhosWithMinEntregaDateByClienteEmail(email);
    }

    // @Transactional
    // public List<TrabalhoResumoParcelasRetornoDTO> listarTodosTrabalhosParcela() {
    //     usuarioService.userIsAuthorized();
    //     return trabalhoRepository.findTrabalhosWithMinParcelaDate();
    // }

    @Transactional
    public List<TrabalhoResumoParcelasRetornoDTO> listarTodosTrabalhosProxParcela() {
        usuarioService.userIsAuthorized();
        return trabalhoRepository.findTrabalhosProxParcelas();
    }

    @Transactional
    public List<TrabalhoResumoProxEntregasRetornoDTO> listarTodosTrabalhosProxEntrega() {
        usuarioService.userIsAuthorized();
        return trabalhoRepository.findTrabalhosProxEntregas();
    }

    @Transactional
    public List<TrabalhoResumoProxEntregasRetornoDTO> listarMeusTrabalhos() {
        Usuario usuario = usuarioService.capturaUsuarioToken();
        return trabalhoRepository.findMeusTrabalhos(usuario.getEmail());
    }


    // Deletar Trabalho
    @Transactional
    public void deletarTrabalho(Long id) {
        Trabalho trabalho = trabalhoRepository.findById(id)
                .orElseThrow(() -> new ErroException("id","Trabalho não encontrado com o ID: " + id));
        usuarioService.usuarioIsResponsavel(trabalho.getResponsavel().getId());

        trabalhoRepository.deleteById(id);
    }

    @Transactional
    public void atualizarStatusTrabalhos() {
        LocalDate dataAtual = LocalDate.now();

        // Atualiza status das parcelas diretamente no banco
        int parcelasAtualizadas = trabalhoRepository.atualizarParcelasAtrasadas(StatusParcela.AGUARDANDO_DATA, StatusParcela.ATRASADA, dataAtual);
        logger.info(parcelasAtualizadas + " parcelas atualizadas para ATRASADA.");
    
        // Atualiza status dos trabalhos cujas parcelas ficaram atrasadas
        int trabalhosParcelasAtualizados = trabalhoRepository.atualizarTrabalhosParcelasAtrasadas(StatusParcela.ATRASADA);
        logger.info(trabalhosParcelasAtualizados + " trabalhos atualizados para status de parcelas ATRASADA.");
    
        // Atualiza status das entregas diretamente no banco
        List<StatusEntrega> statuses = Arrays.asList(StatusEntrega.NAO_INICIADA, StatusEntrega.EM_ANDAMENTO, StatusEntrega.EM_REVISAO);
        int entregasAtualizadas = trabalhoRepository.atualizarEntregasAtrasadas(statuses, StatusEntrega.ATRASADA, dataAtual);
        logger.info(entregasAtualizadas + " entregas atualizadas para ATRASADA.");
    
        // Atualiza status dos trabalhos cujas entregas ficaram atrasadas
        int trabalhosEntregasAtualizados = trabalhoRepository.atualizarTrabalhosEntregasAtrasadas(StatusEntrega.ATRASADA);
        logger.info(trabalhosEntregasAtualizados + " trabalhos atualizados para status de entregas ATRASADA.");
    }
}
