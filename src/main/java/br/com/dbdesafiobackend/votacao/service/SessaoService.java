package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.constants.Constants;
import br.com.dbdesafiobackend.dto.SessaoDTO;
import br.com.dbdesafiobackend.enums.StatusSessaoEnum;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
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

    public Sessao createSessao(SessaoDTO sessaoDto) {
        Sessao sessao = new Sessao();
        validateTempoAberturaIsNull(sessaoDto, sessao);
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());
        sessao.setDataHoraAbertura(LocalDateTime.now());
        Pauta pautaSessao = getPauta(sessaoDto);
        sessao.setPauta(pautaSessao);

        return sessaoRepository.save(sessao);
    }

    private static void validateTempoAberturaIsNull(SessaoDTO sessaoDto, Sessao sessao) {
        if (Objects.isNull(sessaoDto.getTempoAbertura())) {
            sessao.setTempoAbertura(Constants.TEMPO_ABERTURA_DEFAULT);
        } else {
            sessao.setTempoAbertura(sessaoDto.getTempoAbertura());
        }
    }

    private Pauta getPauta(SessaoDTO sessaoDto) {
        return pautaRepository.findPautaById(sessaoDto.getIdPauta());
        //TODO FAZER TRATAMENTO DE EXCEÇÃO
    }
}
