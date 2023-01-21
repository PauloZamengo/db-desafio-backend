package br.com.dbdesafiobackend.votacao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@Table(name = "tb_pauta", schema = "dbschema")
@SequenceGenerator(name = "seq_pauta", sequenceName = "dbschema.seq_pauta")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbschema.seq_pauta")
    @Column(name = "id_pauta", nullable = false)
    private Long idPauta;

    @Column(name = "descricao")
    private String descricao;

}
