package br.com.dbdesafiobackend.votacao.controller;

import br.com.dbdesafiobackend.dto.UserResponseDTO;
import br.com.dbdesafiobackend.votacao.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "votacao/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{cpf}")
    public UserResponseDTO findUserByCpf(@PathVariable(value = "cpf") @NonNull @Validated String cpf) {
        return userService.findUserByCpf(cpf);
    }
}
