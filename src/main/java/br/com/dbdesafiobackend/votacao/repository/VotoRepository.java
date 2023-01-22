package br.com.dbdesafiobackend.votacao.repository;

import br.com.dbdesafiobackend.dto.VotoResponseDTO;
import br.com.dbdesafiobackend.votacao.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    @Query("SELECT v FROM Voto v WHERE id_pauta = :id_pauta")
    List<Voto> findVotosByIdPauta(@Param("id_pauta") Long idPauta);

    @Query("SELECT v FROM Voto v WHERE id_pauta = :id_pauta AND id_associado = :id_associado")
    VotoResponseDTO findVotoByIdPautaAndIdAssociado(@Param("id_pauta") Long idPauta,
                                                    @Param("id_associado") Integer idAssociado);
}
