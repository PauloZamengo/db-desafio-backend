package br.com.dbdesafiobackend.votacao.exception;

import javax.persistence.EntityNotFoundException;

public class PautaNotFoundException extends EntityNotFoundException {

    public PautaNotFoundException(String mensagem) {
        super(mensagem);
    }
}
