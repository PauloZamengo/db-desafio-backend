package br.com.dbdesafiobackend.converter;

import br.com.dbdesafiobackend.dto.VotoResponseDTO;
import br.com.dbdesafiobackend.votacao.entity.Voto;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class VotoConverter {

    public static VotoResponseDTO converterVotoEntityToDTO(Voto voto) {
        VotoResponseDTO votoResponseDTO = new VotoResponseDTO();
        if (!Objects.isNull(voto)) {
            votoResponseDTO.setIdVoto(voto.getIdVoto());
            votoResponseDTO.setPauta(voto.getPauta());
            votoResponseDTO.setValor(voto.isValor());
            votoResponseDTO.setIdAssociado(voto.getIdAssociado());
        }
        return votoResponseDTO;
    }
}
