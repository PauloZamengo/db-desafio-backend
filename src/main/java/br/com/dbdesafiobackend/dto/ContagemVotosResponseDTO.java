package br.com.dbdesafiobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContagemVotosResponseDTO {

    private Integer votosTotal;
    private Integer votosSim;
    private Integer votosNao;
    private Long idPauta;
}
