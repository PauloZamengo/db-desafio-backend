package br.com.dbdesafiobackend.dto;

import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SessaoResponseDTO {
    @JsonProperty("id_sessao")
    private Long idSessao;
    @JsonProperty("pauta")
    private Pauta pauta;
    @JsonProperty("status")
    private String status;
    @JsonProperty("data_hora_abertura")
    private LocalDateTime dataHoraAbertura;
    @JsonProperty("tempo_abertura")
    private Integer tempoAbertura;

}
