package br.com.dbdesafiobackend.votacao.repository;

import br.com.dbdesafiobackend.dto.PautaDTO;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    @Query("SELECT p FROM Pauta p WHERE id_pauta = :id_pauta")
        Pauta findPautaById(@Param("id_pauta") Long idPauta);

    @Query("SELECT new br.com.dbdesafiobackend.dto.PautaDTO(p.idPauta, p.descricao) FROM Pauta p WHERE id_pauta = :id_pauta")
        PautaDTO findPautaDTOById(@Param("id_pauta") Long idPauta);
}
