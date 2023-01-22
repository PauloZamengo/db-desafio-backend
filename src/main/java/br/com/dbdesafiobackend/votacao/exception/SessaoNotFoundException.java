package br.com.dbdesafiobackend.votacao.exception;

public class SessaoNotFoundException extends RuntimeException {

    public SessaoNotFoundException(String mensagem) {
        super(mensagem);
    }
}
