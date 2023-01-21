package br.com.dbdesafiobackend.votacao.exception;

public class SessaoExpiredException extends RuntimeException {

    public SessaoExpiredException(String mensagem) {
        super(mensagem);
    }
}
