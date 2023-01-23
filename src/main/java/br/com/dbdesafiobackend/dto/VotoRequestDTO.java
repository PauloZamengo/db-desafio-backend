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
public class VotoRequestDTO {

    @JsonProperty("id_pauta")
    @NotNull
    private Long idPauta;

    @JsonProperty("id_associado")
    @NotNull
    private Integer idAssociado;

    @JsonProperty("valor")
    @NotNull
    private String valor;
}
