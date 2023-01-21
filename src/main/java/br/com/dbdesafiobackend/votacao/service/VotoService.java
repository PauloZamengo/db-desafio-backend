package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.model.ContagemVotosResponse;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Voto;
import br.com.dbdesafiobackend.votacao.repository.PautaRepository;
import br.com.dbdesafiobackend.votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    public Voto createVoto() {
        return null;
    }

    public ContagemVotosResponse countVotos(Long idPauta) {
        List<Voto> votos;
        votos = votoRepository.findVotosByIdPauta(idPauta);

        Integer qtdeVotosSim = convertLongToInt(votos.stream().filter(Voto::isValor).count());
        Integer qtdeVotosNao = convertLongToInt(votos.stream().filter(voto -> !voto.isValor()).count());

        return ContagemVotosResponse.builder()
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
