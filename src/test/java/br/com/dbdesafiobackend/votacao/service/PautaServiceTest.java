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
        Pauta pauta = buildPauta("Teste da descricao");
        PautaResponseDTO pautaResponseDTO = pautaService.createPauta(pautaRequestDTO);

        ArgumentCaptor<Pauta> pautaArgumentCaptor = ArgumentCaptor.forClass(Pauta.class);
        verify(pautaRepository, times(1)).save(pautaArgumentCaptor.capture());

        assertEquals(pautaResponseDTO.getDescricao(), pauta.getDescricao());
    }

    @Test
    public void findAllPautaOKTest() {
        List<Pauta> pautaList = new ArrayList<>();
        Pauta pauta1 = buildPauta("Teste 1");
        Pauta pauta2 = buildPauta("Teste 2");
        Pauta pauta3 = buildPauta("Teste 3");
        Pauta pauta4 = buildPauta("Teste 4");
        Pauta pauta5 = buildPauta("Teste 5");
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

    public Pauta buildPauta(String descricao) {
        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao(descricao);
        return pauta;
    }
}
