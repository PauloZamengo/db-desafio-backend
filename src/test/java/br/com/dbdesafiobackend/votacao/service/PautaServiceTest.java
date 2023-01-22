package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.dto.PautaRequestDTO;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import br.com.dbdesafiobackend.votacao.repository.PautaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PautaServiceTest {

    @InjectMocks
    private PautaService pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Test
    public void createPauta() {
        PautaRequestDTO pautaRequestDTO = new PautaRequestDTO();
        pautaRequestDTO.setDescricao("Teste da descricao");

        Pauta pauta = new Pauta();
        pauta.setDescricao("Teste da descricao");

        PautaResponseDTO pautaResponseDTO = pautaService.createPauta(pautaRequestDTO);

        ArgumentCaptor<Pauta> pautaArgumentCaptor = ArgumentCaptor.forClass(Pauta.class);
        verify(pautaRepository, times(1)).save(pautaArgumentCaptor.capture());

        assertEquals(pautaResponseDTO.getDescricao(), pauta.getDescricao());
    }
}
