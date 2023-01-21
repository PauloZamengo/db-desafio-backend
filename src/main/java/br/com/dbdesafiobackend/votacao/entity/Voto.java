package br.com.dbdesafiobackend.votacao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tb_voto", schema = "dbschema")
@SequenceGenerator(name = "seq_voto", sequenceName = "dbschema.seq_voto")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_voto")
    @Column(name = "id_voto", nullable = false)
    private Long idVoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pauta")
    private Pauta pauta;

    @Column(name = "id_associado", nullable = false)
    private Integer idAssociado;

    @Column(name = "valor", nullable = false)
    private boolean valor;

}
