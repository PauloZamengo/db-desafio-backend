package br.com.dbdesafiobackend.votacao.repository;

import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Voto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(MockitoJUnitRunner.class)
public class VotoRepositoryTest {

    @Mock
    private VotoRepository repository;

    @Test
    public void createVotoOKTest() {
        Voto voto = buildVoto();
        assertDoesNotThrow(() -> repository.save(voto));
    }

    private Voto buildVoto() {
        Voto voto = new Voto();
        voto.setIdVoto(1L);
        voto.setIdAssociado(1);
        voto.setValor(true);
        voto.setPauta(buildPauta());
        return voto;
    }

    private Pauta buildPauta() {
        Pauta pauta = new Pauta();
        pauta.setDescricao("Teste referente a descrição da pauta.");
        pauta.setIdPauta(1L);
        return pauta;
    }
}
