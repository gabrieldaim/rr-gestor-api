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
import com.rr.gestor_api.dto.trabalho.TrabalhoResumoRetornoDTO;
import com.rr.gestor_api.repositories.ClienteRepository;
import com.rr.gestor_api.repositories.TrabalhoRepository;
import com.rr.gestor_api.repositories.UsuarioRepository;
import com.rr.gestor_api.service.erro.ErroException;
import com.rr.gestor_api.service.usuario.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrabalhoService {

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
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + trabalhoInputDTO.clienteId()));

        // Cria o trabalho associado ao cliente
        Trabalho trabalho = new Trabalho();
        trabalho.setResponsavel(usuarioService.capturaUsuarioToken());
        trabalho.setCliente(cliente);
        trabalho.setTipoTrabalho(trabalhoInputDTO.tipoTrabalho());
        trabalho.setFaculdade(trabalhoInputDTO.faculdade());
        trabalho.setCurso(trabalhoInputDTO.curso());
        trabalho.setTema(trabalhoInputDTO.tema());
        trabalho.setCaminhoPendrive(trabalhoInputDTO.caminhoPendrive());
        trabalho.setCaminhoDrive(trabalhoInputDTO.caminhoDrive());
        trabalho.setObservacao(trabalhoInputDTO.observacao());
        trabalho.setValorTotal(trabalhoInputDTO.valorTotal());
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
                .orElseThrow(() -> new ErroException("responsavelId","Responsavel não encontrado com o Email: " + trabalhoInputDTO.responsavelEmail()));

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

        // Atualiza as parcelas
        Map<Long, ParcelaAtualizarDTO> parcelasAtualizacaoMap = trabalhoInputDTO.parcelas().stream()
                .collect(Collectors.toMap(ParcelaAtualizarDTO::id, parcela -> parcela));

        trabalho.getParcelas().forEach(parcela -> {
            ParcelaAtualizarDTO parcelaAtualizarDTO = parcelasAtualizacaoMap.get(parcela.getId());
            if (parcelaAtualizarDTO != null) {
                parcela.setNome(parcelaAtualizarDTO.nome());
                parcela.setData(parcelaAtualizarDTO.data());
                parcela.setStatus(parcelaAtualizarDTO.status());
                parcela.setValor(parcelaAtualizarDTO.valor());
            }
        });

        return trabalhoRepository.save(trabalho);
    }


    // Buscar trabalho por ID
    @Transactional
    public Trabalho buscarTrabalhoPorId(Long id) {
        return trabalhoRepository.findById(id)
                .orElseThrow(() -> new ErroException("id","Trabalho não encontrado com o ID: " + id));
    }

    // Listar Todos os trabalhos
    @Transactional
    public List<TrabalhoResumoRetornoDTO> listarTodosTrabalhos() {

        return trabalhoRepository.findTrabalhosWithMinEntregaDate();
    }

    @Transactional
    public List<TrabalhoResumoRetornoDTO> listarTodosTrabalhosEmail(String email) {

        return trabalhoRepository.findTrabalhosWithMinEntregaDateByClienteEmail(email);
    }

    @Transactional
    public List<TrabalhoResumoParcelasRetornoDTO> listarTodosTrabalhosParcela() {

        return trabalhoRepository.findTrabalhosWithMinParcelaDate();
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
        // Busca todos os trabalhos com parcelas aguardando data
        List<Trabalho> trabalhosParcelas = trabalhoRepository.findTrabalhosWithParcelasStatus(StatusParcela.AGUARDANDO_DATA);

        // Data atual
        LocalDate dataAtual = LocalDate.now();

        // Varre os trabalhos
        for (Trabalho trabalho : trabalhosParcelas) {
            boolean possuiParcelaAtrasada = false;


            // Atualiza o status das parcelas
            for (Parcela parcela : trabalho.getParcelas()) {
                if (parcela.getStatus() == StatusParcela.AGUARDANDO_DATA && parcela.getData().isBefore(dataAtual)) {
                    parcela.setStatus(StatusParcela.ATRASADA);
                    System.out.println("Trabalho:" + parcela.getTrabalho().getId() + "atualizado com sucesso!");
                    possuiParcelaAtrasada = true; // Marca que o trabalho tem ao menos uma parcela atrasada
                }
            }

            // Atualiza o status do trabalho, se necessário
            if (possuiParcelaAtrasada) {
                trabalho.setStatusParcelas(StatusParcela.ATRASADA);
            }
        }

        List<StatusEntrega> statuses = Arrays.asList(
                StatusEntrega.NAO_INICIADA,
                StatusEntrega.EM_ANDAMENTO,
                StatusEntrega.EM_REVISAO
        );

        List<Trabalho> trabalhosEntrega = trabalhoRepository.findTrabalhosWithEntregasStatus(statuses);

        for (Trabalho trabalho : trabalhosEntrega) {
            boolean possuiEntregaAtrasada = false;


            // Atualiza o status das Entregas
            for (Entrega entrega : trabalho.getEntregas()) {
                if ((entrega.getStatus() == StatusEntrega.EM_REVISAO || entrega.getStatus() == StatusEntrega.EM_ANDAMENTO || entrega.getStatus() == StatusEntrega.NAO_INICIADA) && entrega.getData().isBefore(dataAtual)) {
                    entrega.setStatus(StatusEntrega.ATRASADA);
                    System.out.println("Trabalho:" + entrega.getTrabalho().getId() + "atualizado com sucesso!");
                    possuiEntregaAtrasada = true; // Marca que o trabalho tem ao menos uma entrega atrasada
                }
            }

            // Atualiza o status do trabalho, se necessário
            if (possuiEntregaAtrasada) {
                trabalho.setStatusEntregas(StatusEntrega.ATRASADA);
            }
        }
        System.out.println("####################trabalhos atualizados####################");
    }

}
