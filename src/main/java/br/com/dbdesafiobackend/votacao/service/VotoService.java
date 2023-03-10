package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.converter.PautaConverter;
import br.com.dbdesafiobackend.converter.VotoConverter;
import br.com.dbdesafiobackend.dto.VotoResponseDTO;
import br.com.dbdesafiobackend.dto.VotoRequestDTO;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.dto.ContagemVotosResponseDTO;
import br.com.dbdesafiobackend.enums.StatusSessaoEnum;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.exception.PautaNotFoundException;
import br.com.dbdesafiobackend.votacao.exception.ValorVotoException;
import br.com.dbdesafiobackend.votacao.exception.AssociadoException;
import br.com.dbdesafiobackend.votacao.exception.SessaoExpiredException;
import br.com.dbdesafiobackend.votacao.exception.SessaoNotFoundException;
import br.com.dbdesafiobackend.votacao.exception.VotosNotFoundException;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import br.com.dbdesafiobackend.votacao.entity.Voto;
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

    public VotoResponseDTO createVoto(VotoRequestDTO voto) throws Exception {
        validateValorVoto(voto);
        validateIfPautaExists(voto.getIdPauta());
        validateSessaoIsOpen(voto);
        validateIfExistVotoAssociado(voto);

        Voto newVoto = new Voto();
        newVoto.setValor(convertValorToBoolean(voto.getValor()));
        newVoto.setIdAssociado(voto.getIdAssociado());
        Pauta pauta = PautaConverter.pautaConverterDtoRequestToEntity(getPauta(voto));
        newVoto.setPauta(pauta);
        votoRepository.save(newVoto);
        return VotoConverter.converterVotoEntityToDTO(newVoto);
    }

    private void validateIfPautaExists(Long idPauta) {
        PautaResponseDTO pauta = pautaRepository.findPautaDTOById(idPauta);
        if (Objects.isNull(pauta)){
            throw new PautaNotFoundException("Pauta n??o encontrada!");
        }
    }

    private boolean convertValorToBoolean(String valor) {
        return Objects.equals(valor, "SIM");
    }

    private PautaResponseDTO getPauta(VotoRequestDTO votoDTO) {
        PautaResponseDTO pautaResponseDTO = pautaRepository.findPautaDTOById(votoDTO.getIdPauta());
        validatePautaIsNull(pautaResponseDTO);
        return pautaResponseDTO;
    }

    private static void validatePautaIsNull(PautaResponseDTO pautaResponseDTO) {
        if (pautaResponseDTO == null) {
            throw new PautaNotFoundException("Pauta n??o encontrada!");
        }
    }

    private static void validateValorVoto(VotoRequestDTO voto) {
        if (!voto.getValor().matches("SIM|NAO")) {
            throw new ValorVotoException("O campo 'VALOR' deve conter apenas os valores 'SIM' ou 'NAO'!");
        }
    }

    private void validateIfExistVotoAssociado(VotoRequestDTO voto) {
        VotoResponseDTO existVotoIdAssociado = votoRepository.findVotoByIdPautaAndIdAssociado(voto.getIdPauta(), voto.getIdAssociado());
        if(!Objects.isNull(existVotoIdAssociado)) {
            throw new AssociadoException("J?? existe voto desse associado nessa pauta!");
        }
    }

    private void validateSessaoIsOpen(VotoRequestDTO voto) throws Exception {
        Sessao sessaoVoto = sessaoRepository.findSessaoByIdPauta(voto.getIdPauta());
        validateSessaoIsNull(sessaoVoto);

        if (LocalDateTime.now().isAfter(sessaoVoto.getDataHoraAbertura().plusMinutes(sessaoVoto.getTempoAbertura())) ||
                Objects.equals(sessaoVoto.getStatus(), StatusSessaoEnum.FECHADA.getStatusSessao())) {
            setStatusSessao(sessaoVoto);
            throw new SessaoExpiredException("Essa sess??o j?? est?? encerrada!");
        }
    }

    private void setStatusSessao(Sessao sessaoVoto) {
        if (Objects.equals(sessaoVoto.getStatus(), StatusSessaoEnum.ABERTA.getStatusSessao())) {
            sessaoVoto.setStatus(StatusSessaoEnum.FECHADA.getStatusSessao());
            sessaoRepository.save(sessaoVoto);
        }
    }

    private static void validateSessaoIsNull(Sessao sessaoVoto) {
        if (sessaoVoto == null) {
            throw new SessaoNotFoundException("Sess??o n??o encontrada!");
        }
    }

    public ContagemVotosResponseDTO countVotos(Long idPauta) {
        validateIdPautaIsNull(idPauta);
        validateIfPautaExists(idPauta);
        List<Voto> votos = votoRepository.findVotosByIdPauta(idPauta);
        validateVotosByIdPautaIsNull(votos);

        Integer votosSim = convertLongToInt(votos.stream().filter(Voto::isValor).count());
        Integer votosNao = convertLongToInt(votos.stream().filter(voto -> !voto.isValor()).count());

        return ContagemVotosResponseDTO.builder()
                .votosTotal(votos.size())
                .votosNao(votosNao)
                .votosSim(votosSim)
                .idPauta(idPauta)
                .build();
    }

    private void validateVotosByIdPautaIsNull(List<Voto> votos) {
        if (votos == null) {
            throw new VotosNotFoundException("N??o foi poss??vel encontrar os votos dessa pauta!");
        }
    }

    private void validateIdPautaIsNull(Long idPauta) {
        if (Objects.isNull(idPauta)) {
            throw new PautaNotFoundException("Pauta n??o encontrada!");
        }
    }

    private Integer convertLongToInt(Long n) {
        return Integer.parseInt(n.toString());
    }
}
