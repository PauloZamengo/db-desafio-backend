package br.com.dbdesafiobackend.dto;

import br.com.dbdesafiobackend.votacao.entity.Pauta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PautaResponseDTO {

    private Long idPauta;
    private String descricao;

    public void mappingEntityToDTO(Pauta pauta) {
        this.idPauta = pauta.getIdPauta();
        this.descricao = pauta.getDescricao();
    }
}
