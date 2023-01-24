package br.com.dbdesafiobackend.votacao.repository;

import br.com.dbdesafiobackend.enums.StatusSessaoEnum;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(MockitoJUnitRunner.class)
public class SessaoRepositoryTest {

    @Mock
    private SessaoRepository repository;

    @Test
    public void createSessaoOKTest() {
        Sessao sessao = buildSessao();
        assertDoesNotThrow(() -> repository.save(sessao));
    }

    private Sessao buildSessao() {
        Sessao sessao = new Sessao();
        sessao.setIdSessao(1L);
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());
        sessao.setPauta(buildPauta());
        sessao.setDataHoraAbertura(LocalDateTime.now());
        sessao.setTempoAbertura(10);
        return sessao;
    }

    private Pauta buildPauta() {
        Pauta pauta = new Pauta();
        pauta.setDescricao("Teste referente a descrição da pauta.");
        pauta.setIdPauta(1L);
        return pauta;
    }
}
