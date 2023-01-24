package br.com.dbdesafiobackend.converter;

import br.com.dbdesafiobackend.dto.PautaRequestDTO;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class PautaConverter {

    public static PautaResponseDTO pautaConverterEntityToDtoResponse(Pauta pauta) {
        PautaResponseDTO pautaResponseDTO = new PautaResponseDTO();
        if (!Objects.isNull(pauta)) {
            pautaResponseDTO.setIdPauta(pauta.getIdPauta());
            pautaResponseDTO.setDescricao(pauta.getDescricao());
        }
        return pautaResponseDTO;
    }

    public static Pauta pautaConverterDtoToEntity(PautaRequestDTO pautaRequestDTO) {
        Pauta pauta = new Pauta();
        if(!Objects.isNull(pautaRequestDTO)) {
            pauta.setDescricao(pautaRequestDTO.getDescricao());
        }
        return pauta;
    }

    public static Pauta pautaConverterDtoRequestToEntity(PautaResponseDTO pautaResponseDTO) {
        Pauta pauta = new Pauta();
        if (!Objects.isNull(pautaResponseDTO)) {
            pauta.setIdPauta(pautaResponseDTO.getIdPauta());
            pauta.setDescricao(pautaResponseDTO.getDescricao());
        }
        return pauta;
    }
}
