package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.dto.UserResponseDTO;
import br.com.dbdesafiobackend.votacao.client.UserClient;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserClient userClient;

    @Autowired
    public UserService(UserClient userClient) {
        this.userClient = userClient;
    }

    public UserResponseDTO findUserByCpf(String cpf) {
        Response response = userClient.findUserByCpf(cpf);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        validateStatus(response, userResponseDTO);

        return userResponseDTO;
    }

    private static void validateStatus(Response response, UserResponseDTO userResponseDTO) {
        if (response.status() != HttpStatus.OK.value()) {
            userResponseDTO.setStatus("UNABLE_TO_VOTE");
        } else {
            userResponseDTO.setStatus("ABLE_TO_VOTE");
        }
    }
}
