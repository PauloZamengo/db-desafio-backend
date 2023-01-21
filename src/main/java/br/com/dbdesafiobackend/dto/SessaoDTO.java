package br.com.dbdesafiobackend.dto;

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
public class SessaoDTO {

    @JsonProperty("id_pauta")
    private Long idPauta;

    @JsonProperty("tempo_abertura")
    private Integer tempoAbertura;
}
