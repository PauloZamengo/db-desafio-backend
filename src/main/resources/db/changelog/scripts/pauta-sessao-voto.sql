CREATE SEQUENCE dbschema.seq_pauta
 INCREMENT BY 1
 START WITH 1
 NO MAXVALUE
 NO MINVALUE
 CACHE 1
;

CREATE TABLE dbschema.tb_pauta(
 id_pauta integer NOT NULL,
 descricao character varying(280),
 constraint pk_id_pauta primary key (id_pauta)
);

-------------------------------

CREATE SEQUENCE dbschema.seq_sessao
 INCREMENT BY 1
 START WITH 1
 NO MAXVALUE
 NO MINVALUE
 CACHE 1
;

CREATE TABLE dbschema.tb_sessao(
 id_sessao integer NOT NULL,
 id_pauta integer NOT NULL,
 status character varying(10),
 data_hora_abertura timestamp NOT NULL,
 tempo_abertura integer,
 constraint pk_id_sessao primary key (id_sessao)
);

ALTER TABLE dbschema.tb_sessao
ADD CONSTRAINT fk_id_pauta_sessao
FOREIGN KEY (id_pauta) REFERENCES dbschema.tb_pauta (id_pauta)
ON UPDATE NO ACTION ON DELETE NO ACTION;

-----------------------------------------

CREATE SEQUENCE dbschema.seq_voto
 INCREMENT BY 1
 START WITH 1
 NO MAXVALUE
 NO MINVALUE
 CACHE 1
;

CREATE TABLE dbschema.tb_voto(
 id_voto integer NOT NULL,
 id_pauta integer NOT NULL,
 id_associado integer NOT NULL,
 valor boolean NOT NULL,
 constraint pk_id_voto primary key (id_voto)
);

ALTER TABLE dbschema.tb_voto
ADD CONSTRAINT fk_id_pauta_voto
FOREIGN KEY (id_pauta) REFERENCES dbschema.tb_pauta (id_pauta)
ON UPDATE NO ACTION ON DELETE NO ACTION;