package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.dto.PautaRequestDTO;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.repository.PautaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PautaService {

    @Autowired
    PautaRepository pautaRepository;

    public PautaResponseDTO createPauta(PautaRequestDTO pautaRequestDTO) {
        Pauta pauta = new Pauta();
        pauta.setDescricao(pautaRequestDTO.getDescricao());
        pautaRepository.save(pauta);
        return getPautaResponseDTO(pauta);
    }

    public List<PautaResponseDTO> findPauta() {

        List<Pauta> pautaList = pautaRepository.findAll();
        List<PautaResponseDTO> pautaReponseDtoList = new ArrayList<>();
        pautaList.forEach(pauta -> pautaReponseDtoList.add(getPautaResponseDTO(pauta)));
        return pautaReponseDtoList;

    }

    private PautaResponseDTO getPautaResponseDTO(Pauta pauta) {
        PautaResponseDTO pautaResponseDTO = new PautaResponseDTO();
        pautaResponseDTO.mappingEntityToDTO(pauta);
        return pautaResponseDTO;
    }


}
