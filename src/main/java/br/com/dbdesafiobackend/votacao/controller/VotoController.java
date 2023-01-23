package br.com.dbdesafiobackend.votacao.controller;

import br.com.dbdesafiobackend.dto.ContagemVotosResponseDTO;
import br.com.dbdesafiobackend.dto.VotoRequestDTO;
import br.com.dbdesafiobackend.dto.VotoResponseDTO;
import br.com.dbdesafiobackend.votacao.service.VotoService;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "votacao/v1/voto")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @PostMapping
    public VotoResponseDTO createVoto(@RequestBody @NotNull @Validated VotoRequestDTO voto) throws Exception {
        return votoService.createVoto(voto);
    }

    @GetMapping("/{id_pauta}")
    public ContagemVotosResponseDTO countVotos(@PathVariable(value = "id_pauta") @NonNull @Validated Long idPauta) {
        return votoService.countVotos(idPauta);
    }
}
