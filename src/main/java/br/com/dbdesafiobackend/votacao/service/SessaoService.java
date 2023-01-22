package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.constants.Constants;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.dto.SessaoRequestDTO;
import br.com.dbdesafiobackend.dto.SessaoResponseDTO;
import br.com.dbdesafiobackend.enums.StatusSessaoEnum;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import br.com.dbdesafiobackend.votacao.exception.PautaNotFoundException;
import br.com.dbdesafiobackend.votacao.repository.PautaRepository;
import br.com.dbdesafiobackend.votacao.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    public SessaoResponseDTO createSessao(SessaoRequestDTO sessaoDto) {
        Sessao sessao = new Sessao();
        validateTempoAberturaIsNull(sessaoDto, sessao);
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());
        sessao.setDataHoraAbertura(LocalDateTime.now());
        Pauta pautaSessao = convertPautaDTOToPauta(getPauta(sessaoDto));
        sessao.setPauta(pautaSessao);

        sessaoRepository.save(sessao);
        return getSessaoResponseDTO(sessao);
    }

    private Pauta convertPautaDTOToPauta(PautaResponseDTO pautaDTO) {
        Pauta newPauta = new Pauta();
        newPauta.setIdPauta(pautaDTO.getIdPauta());
        newPauta.setDescricao(pautaDTO.getDescricao());
        return newPauta;
    }

    private SessaoResponseDTO getSessaoResponseDTO(Sessao sessao) {
        SessaoResponseDTO sessaoResponseDTO = new SessaoResponseDTO();
        sessaoResponseDTO.mappingEntityToDTO(sessao);
        return sessaoResponseDTO;
    }

    private static void validateTempoAberturaIsNull(SessaoRequestDTO sessaoDto, Sessao sessao) {
        if (Objects.isNull(sessaoDto.getTempoAbertura())) {
            sessao.setTempoAbertura(Constants.TEMPO_ABERTURA_DEFAULT);
        } else {
            sessao.setTempoAbertura(sessaoDto.getTempoAbertura());
        }
    }

    private PautaResponseDTO getPauta(SessaoRequestDTO sessaoDto) {
        if (Objects.isNull(sessaoDto.getIdPauta())) {
            throw new PautaNotFoundException("Pauta não encontrada!");
        }

        PautaResponseDTO pauta = pautaRepository.findPautaDTOById(sessaoDto.getIdPauta());
        validatePautaIsNull(pauta);
        return pauta;
    }

    private void validatePautaIsNull(PautaResponseDTO pauta) {
        if (pauta == null) {
            throw new PautaNotFoundException("Pauta não encontrada!");
        }
    }
}
