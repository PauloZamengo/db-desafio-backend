package br.com.dbdesafiobackend.model;

import br.com.dbdesafiobackend.votacao.entity.Pauta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContagemVotosResponse {

    private Integer qtdeVotosTotal;
    private Integer qtdeVotosSim;
    private Integer qtdeVotosNao;
    private Long idPauta;
}
