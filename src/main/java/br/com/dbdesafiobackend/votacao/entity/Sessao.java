package br.com.dbdesafiobackend.votacao.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_sessao", schema = "dbschema")
@SequenceGenerator(name = "seq_sessao", sequenceName = "dbschema.seq_sessao")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_sessao")
    @Column(name = "id_sessao", nullable = false)
    private Long idSessao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pauta")
    private Pauta pauta;

    @Column(name = "status")
    private String status;

    @Column(name = "data_hora_abertura")
    private LocalDateTime dataHoraAbertura;

    @Column(name = "tempo_abertura")
    private Integer tempoAbertura;

}
