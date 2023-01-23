package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.dto.PautaRequestDTO;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.repository.PautaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PautaServiceTest {

    @InjectMocks
    private PautaService pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Test
    public void createPautaOKTest() {
        PautaRequestDTO pautaRequestDTO = new PautaRequestDTO();
        pautaRequestDTO.setDescricao("Teste da descricao");

        Pauta pauta = new Pauta();
        pauta.setDescricao("Teste da descricao");

        PautaResponseDTO pautaResponseDTO = pautaService.createPauta(pautaRequestDTO);

        ArgumentCaptor<Pauta> pautaArgumentCaptor = ArgumentCaptor.forClass(Pauta.class);
        verify(pautaRepository, times(1)).save(pautaArgumentCaptor.capture());

        assertEquals(pautaResponseDTO.getDescricao(), pauta.getDescricao());
    }

    @Test
    public void findAllPautaOKTest() {
        List<Pauta> pautaList = new ArrayList<>();
        Pauta pauta1 = new Pauta();
        pauta1.setIdPauta(1L);
        pauta1.setDescricao("Teste 1");

        Pauta pauta2 = new Pauta();
        pauta2.setIdPauta(2L);
        pauta2.setDescricao("Teste 2");

        Pauta pauta3 = new Pauta();
        pauta3.setIdPauta(3L);
        pauta3.setDescricao("Teste 3");

        Pauta pauta4 = new Pauta();
        pauta4.setIdPauta(4L);
        pauta4.setDescricao("Teste 4");

        Pauta pauta5 = new Pauta();
        pauta5.setIdPauta(5L);
        pauta5.setDescricao("Teste 5");

        pautaList.addAll(List.of(pauta1, pauta2, pauta3, pauta4, pauta5));

        when(pautaRepository.findAll()).thenReturn(pautaList);
        List<PautaResponseDTO> pautaResponseDTOList = pautaService.findPauta();

        assertEquals(pautaResponseDTOList.get(0).getIdPauta(), pautaList.get(0).getIdPauta());
        assertEquals(pautaResponseDTOList.get(0).getDescricao(), pautaList.get(0).getDescricao());

        assertEquals(pautaResponseDTOList.get(1).getIdPauta(), pautaList.get(1).getIdPauta());
        assertEquals(pautaResponseDTOList.get(1).getDescricao(), pautaList.get(1).getDescricao());

        assertEquals(pautaResponseDTOList.get(2).getIdPauta(), pautaList.get(2).getIdPauta());
        assertEquals(pautaResponseDTOList.get(2).getDescricao(), pautaList.get(2).getDescricao());

        assertEquals(pautaResponseDTOList.get(3).getIdPauta(), pautaList.get(3).getIdPauta());
        assertEquals(pautaResponseDTOList.get(3).getDescricao(), pautaList.get(3).getDescricao());

        assertEquals(pautaResponseDTOList.get(4).getIdPauta(), pautaList.get(4).getIdPauta());
        assertEquals(pautaResponseDTOList.get(4).getDescricao(), pautaList.get(4).getDescricao());

    }
}
