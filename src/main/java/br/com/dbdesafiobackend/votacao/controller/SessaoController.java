package br.com.dbdesafiobackend.votacao.controller;

import br.com.dbdesafiobackend.dto.SessaoRequestDTO;
import br.com.dbdesafiobackend.dto.SessaoResponseDTO;
import br.com.dbdesafiobackend.votacao.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "votacao/v1/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @PostMapping
    public SessaoResponseDTO createSessao(@RequestBody SessaoRequestDTO sessao) {
        return sessaoService.createSessao(sessao);
    }
}
