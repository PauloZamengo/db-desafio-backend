package br.com.dbdesafiobackend.votacao.exception;

public class PautaNotFoundException extends RuntimeException {

    public PautaNotFoundException(String mensagem) {
        super(mensagem);
    }
}
