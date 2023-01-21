package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.dto.ContagemVotosDTO;
import br.com.dbdesafiobackend.dto.PautaDTO;
import br.com.dbdesafiobackend.dto.VotoDTO;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.exception.AssociadoException;
import br.com.dbdesafiobackend.votacao.exception.PautaNotFoundException;
import br.com.dbdesafiobackend.votacao.exception.SessaoExpiredException;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import br.com.dbdesafiobackend.votacao.entity.Voto;
import br.com.dbdesafiobackend.votacao.exception.ValorVotoException;
import br.com.dbdesafiobackend.votacao.repository.PautaRepository;
import br.com.dbdesafiobackend.votacao.repository.SessaoRepository;
import br.com.dbdesafiobackend.votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    public Voto createVoto(VotoDTO voto) throws Exception {
        validateValorVoto(voto);
        validateSessaoIsOpen(voto);
        validateIfExistVotoAssociado(voto);

        Voto newVoto = new Voto();
        newVoto.setValor(convertValorToBoolean(voto.getValor()));
        newVoto.setIdAssociado(voto.getIdAssociado());
        Pauta pauta = convertPautaDTOToPauta(getPauta(voto));
        newVoto.setPauta(pauta);
        return votoRepository.save(newVoto);
    }

    private boolean convertValorToBoolean(String valor) {
        return Objects.equals(valor, "SIM");
    }

    private PautaDTO getPauta(VotoDTO votoDTO) {
        if (Objects.isNull(votoDTO.getIdPauta())) {
            throw new PautaNotFoundException("Pauta não encontrada!");
        }
        return pautaRepository.findPautaDTOById(votoDTO.getIdPauta());
    }

    private Pauta convertPautaDTOToPauta(PautaDTO pautaDTO) {
        Pauta newPauta = new Pauta();
        newPauta.setIdPauta(pautaDTO.getIdPauta());
        newPauta.setDescricao(pautaDTO.getDescricao());
        return newPauta;
    }

    private static void validateValorVoto(VotoDTO voto) {
        if (!voto.getValor().matches("SIM|NAO")) {
            throw new ValorVotoException("O campo 'VALOR' deve conter apenas os valores 'SIM' ou 'NAO'!");
        }
    }

    private void validateIfExistVotoAssociado(VotoDTO voto) {
        Voto existVotoIdAssociado = votoRepository.findVotoByIdPautaAndIdAssociado(voto.getIdPauta(), voto.getIdAssociado());
        if(!Objects.isNull(existVotoIdAssociado)) {
            throw new AssociadoException("Já existe voto desse associado nessa pauta!");
        }
    }

    private void validateSessaoIsOpen(VotoDTO voto) throws Exception {
        Sessao sessaoVoto = sessaoRepository.findSessaoByIdPauta(voto.getIdPauta());
        if (LocalDateTime.now().isAfter(sessaoVoto.getDataHoraAbertura().plusMinutes(sessaoVoto.getTempoAbertura()))) {
            throw new SessaoExpiredException("Essa sessão já está encerrada!");
        }
    }

    public ContagemVotosDTO countVotos(Long idPauta) {
        List<Voto> votos;
        votos = votoRepository.findVotosByIdPauta(idPauta);

        Integer qtdeVotosSim = convertLongToInt(votos.stream().filter(Voto::isValor).count());
        Integer qtdeVotosNao = convertLongToInt(votos.stream().filter(voto -> !voto.isValor()).count());

        return ContagemVotosDTO.builder()
                .qtdeVotosTotal(votos.size())
                .qtdeVotosNao(qtdeVotosNao)
                .qtdeVotosSim(qtdeVotosSim)
                .idPauta(idPauta)
                .build();
    }

    private Integer convertLongToInt(Long n) {
        return Integer.parseInt(n.toString());
    }
}
