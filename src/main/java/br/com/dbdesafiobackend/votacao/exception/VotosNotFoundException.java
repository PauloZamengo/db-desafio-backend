package br.com.dbdesafiobackend.votacao.exception;

import javax.persistence.EntityNotFoundException;

public class VotosNotFoundException extends EntityNotFoundException {

    public VotosNotFoundException(String mensagem) {
        super(mensagem);
    }
}
