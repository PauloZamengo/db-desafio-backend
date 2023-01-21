package br.com.dbdesafiobackend.votacao.repository;

import br.com.dbdesafiobackend.votacao.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {

}
