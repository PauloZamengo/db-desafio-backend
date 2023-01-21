package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.constants.Constants;
import br.com.dbdesafiobackend.enums.StatusSessaoEnum;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import br.com.dbdesafiobackend.votacao.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    public Sessao createSessao(Sessao sessao) {
        validateTempoAbertura(sessao);
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());
        return sessaoRepository.save(sessao);
    }

    private static void validateTempoAbertura(Sessao sessao) {
        if (Objects.nonNull(sessao.getTempoAbertura())) {

        }

        sessao.setTempoAbertura(Constants.TEMPO_ABERTURA_DEFAULT);
    }
}
