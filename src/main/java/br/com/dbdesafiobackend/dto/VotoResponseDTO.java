package br.com.dbdesafiobackend.dto;

import br.com.dbdesafiobackend.votacao.entity.Pauta;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VotoResponseDTO {

    @JsonProperty("id_voto")
    private Long idVoto;

    @JsonProperty("pauta")
    private Pauta pauta;

    @JsonProperty("id_associado")
    private Integer idAssociado;

    @JsonProperty("valor")
    private boolean valor;

}
