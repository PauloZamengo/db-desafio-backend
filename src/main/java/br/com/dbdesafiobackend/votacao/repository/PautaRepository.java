package br.com.dbdesafiobackend.votacao.repository;

import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    @Query("SELECT new br.com.dbdesafiobackend.dto.PautaResponseDTO(p.idPauta, p.descricao) FROM Pauta p WHERE id_pauta = :id_pauta")
    PautaResponseDTO findPautaDTOById(@Param("id_pauta") Long idPauta);
}
