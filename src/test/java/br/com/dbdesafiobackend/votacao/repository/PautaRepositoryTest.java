package br.com.dbdesafiobackend.votacao.repository;

import br.com.dbdesafiobackend.votacao.entity.Pauta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PautaRepositoryTest {

    @Mock
    private PautaRepository repository;

    @Test
    public void createPautaOKTest() {
        Pauta pautaReq = new Pauta();
        pautaReq.setDescricao("Teste referente a descrição da pauta.");

        Pauta pauta = buildPauta();
        assertEquals(pautaReq.getDescricao(), pauta.getDescricao());
        assertDoesNotThrow(() -> repository.save(pauta));
    }

    private Pauta buildPauta() {
        Pauta pauta = new Pauta();
        pauta.setDescricao("Teste referente a descrição da pauta.");
        pauta.setIdPauta(1L);
        return pauta;
    }
}
