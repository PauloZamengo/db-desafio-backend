package br.com.dbdesafiobackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDTO {

    @JsonProperty("campo1")
    private String campo1;

    @JsonProperty("campo2")
    private Integer campo2;

    @JsonProperty("idCampoTexto")
    private Integer idCampoTexto;

    @JsonProperty("idCampoNumerico")
    private Integer idCampoNumerico;

    @JsonProperty("idCampoData")
    private Integer idCampoData;
}
