package br.com.dbdesafiobackend.votacao.controller;
import br.com.dbdesafiobackend.dto.PautaRequestDTO;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.votacao.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pauta")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public PautaResponseDTO createPauta(@RequestBody PautaRequestDTO pauta) {
        return pautaService.createPauta(pauta);
    }
}
