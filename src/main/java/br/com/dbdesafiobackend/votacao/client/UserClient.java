package br.com.dbdesafiobackend.votacao.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${url.user}/users")
public interface UserClient {

    @GetMapping("/{cpf}")
    Response findUserByCpf(@PathVariable("cpf") String cpf);
}
