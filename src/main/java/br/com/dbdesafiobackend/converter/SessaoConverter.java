package br.com.dbdesafiobackend.converter;

import br.com.dbdesafiobackend.dto.SessaoResponseDTO;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class SessaoConverter {

    public static SessaoResponseDTO sessaoConverterEntityToDto(Sessao sessao) {
        SessaoResponseDTO sessaoResponseDTO = new SessaoResponseDTO();
        if (!Objects.isNull(sessao)) {
            sessaoResponseDTO.setIdSessao(sessao.getIdSessao());
            sessaoResponseDTO.setPauta(sessao.getPauta());
            sessaoResponseDTO.setStatus(sessao.getStatus());
            sessaoResponseDTO.setTempoAbertura(sessao.getTempoAbertura());
            sessaoResponseDTO.setDataHoraAbertura(sessao.getDataHoraAbertura());
        }
        return sessaoResponseDTO;
    }
}
