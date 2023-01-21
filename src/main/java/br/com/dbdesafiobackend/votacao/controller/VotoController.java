package br.com.dbdesafiobackend.votacao.controller;

import br.com.dbdesafiobackend.model.ContagemVotosResponse;
import br.com.dbdesafiobackend.votacao.entity.Voto;
import br.com.dbdesafiobackend.votacao.service.VotoService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/voto")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @PostMapping
    public Voto createVoto(Voto voto) {
        return votoService.createVoto();
    }

    @PatchMapping("/{id_pauta}")
    public ContagemVotosResponse countVotos(@PathVariable(value = "id_pauta") @NonNull Long idPauta) {
        return votoService.countVotos(idPauta);
    }
}
