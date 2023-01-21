package br.com.dbdesafiobackend.enums;

public enum StatusSessaoEnum {

    ABERTA("ABERTA"),
    FECHADA("FECHADA");

    private final String statusSessao;
    StatusSessaoEnum(String statusSessao) { this.statusSessao = statusSessao; }
    public String getStatusSessao() { return statusSessao; }

}
