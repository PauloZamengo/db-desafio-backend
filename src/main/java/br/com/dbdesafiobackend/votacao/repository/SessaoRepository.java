package br.com.dbdesafiobackend.votacao.repository;

import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    @Query("SELECT s FROM Sessao s WHERE id_pauta = :id_pauta")
    Sessao findSessaoByIdPauta(@Param("id_pauta") Long idPauta);
}
