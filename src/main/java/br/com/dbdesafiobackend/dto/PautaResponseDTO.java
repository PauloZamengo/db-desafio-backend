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
public class PautaResponseDTO {

    @JsonProperty("id_pauta")
    private Long idPauta;
    @JsonProperty("descricao")
    private String descricao;

    public void mappingEntityToDTO(Pauta pauta) {
        this.idPauta = pauta.getIdPauta();
        this.descricao = pauta.getDescricao();
    }
}
