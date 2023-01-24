package br.com.dbdesafiobackend.votacao.controller;
import br.com.dbdesafiobackend.dto.PautaRequestDTO;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.votacao.service.PautaService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "votacao/v1/pautas")

public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public PautaResponseDTO createPauta(@RequestBody @NotNull @Validated PautaRequestDTO pauta) {
        return pautaService.createPauta(pauta);
    }

    @GetMapping
    public List<PautaResponseDTO> findPauta() {
        return pautaService.findPauta();
    }
}
