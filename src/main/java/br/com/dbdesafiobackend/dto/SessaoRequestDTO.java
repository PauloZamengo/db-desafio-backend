package br.com.dbdesafiobackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SessaoRequestDTO {

    @JsonProperty("id_pauta")
    @NotNull
    private Long idPauta;

    @JsonProperty("tempo_abertura")
    private Integer tempoAbertura;
}
