package br.com.dbdesafiobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContagemVotosDTO {

    private Integer qtdeVotosTotal;
    private Integer qtdeVotosSim;
    private Integer qtdeVotosNao;
    private Long idPauta;
}
