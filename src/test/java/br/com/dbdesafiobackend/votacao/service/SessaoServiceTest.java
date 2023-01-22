package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.constants.Constants;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.dto.SessaoRequestDTO;
import br.com.dbdesafiobackend.dto.SessaoResponseDTO;
import br.com.dbdesafiobackend.enums.StatusSessaoEnum;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import br.com.dbdesafiobackend.votacao.exception.PautaNotFoundException;
import br.com.dbdesafiobackend.votacao.repository.PautaRepository;
import br.com.dbdesafiobackend.votacao.repository.SessaoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SessaoServiceTest {
    @InjectMocks
    private SessaoService sessaoService;
    @Mock
    private SessaoRepository sessaoRepository;
    @Mock
    private PautaRepository pautaRepository;
    @Test
    public void createSessaoOKTest() {

        SessaoRequestDTO sessaoRequestDTO = new SessaoRequestDTO();
        sessaoRequestDTO.setIdPauta(1L);
        sessaoRequestDTO.setTempoAbertura(20);

        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("Pauta Teste para sessao");

        PautaResponseDTO pautaResponseDTO = new PautaResponseDTO();
        pautaResponseDTO.setIdPauta(1L);
        pautaResponseDTO.setDescricao("Pauta Teste para sessao");

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setTempoAbertura(20);
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());

        when(pautaRepository.findPautaDTOById(sessaoRequestDTO.getIdPauta())).thenReturn(pautaResponseDTO);
        SessaoResponseDTO sessaoResponseDTO = sessaoService.createSessao(sessaoRequestDTO);

        ArgumentCaptor<Sessao> sessaoArgumentCaptor = ArgumentCaptor.forClass(Sessao.class);
        verify(sessaoRepository, times(1)).save(sessaoArgumentCaptor.capture());

        assertEquals(sessaoResponseDTO.getStatus(), sessao.getStatus());
        assertEquals(sessaoResponseDTO.getPauta().getIdPauta(), sessao.getPauta().getIdPauta());
        assertEquals(sessaoResponseDTO.getPauta().getDescricao(), sessao.getPauta().getDescricao());
        assertEquals(sessaoResponseDTO.getTempoAbertura(), sessao.getTempoAbertura());

        verify(pautaRepository, times(1)).findPautaDTOById(sessaoRequestDTO.getIdPauta());
    }
    @Test
    public void createSessaoOKWhenTempoAberturaIsNullTest() {

        SessaoRequestDTO sessaoRequestDTO = new SessaoRequestDTO();
        sessaoRequestDTO.setIdPauta(1L);
        sessaoRequestDTO.setTempoAbertura(null);

        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("Pauta Teste para sessao");

        PautaResponseDTO pautaResponseDTO = new PautaResponseDTO();
        pautaResponseDTO.setIdPauta(1L);
        pautaResponseDTO.setDescricao("Pauta Teste para sessao");

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setTempoAbertura(Constants.TEMPO_ABERTURA_DEFAULT);
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());

        when(pautaRepository.findPautaDTOById(sessaoRequestDTO.getIdPauta())).thenReturn(pautaResponseDTO);
        SessaoResponseDTO sessaoResponseDTO = sessaoService.createSessao(sessaoRequestDTO);

        ArgumentCaptor<Sessao> sessaoArgumentCaptor = ArgumentCaptor.forClass(Sessao.class);
        verify(sessaoRepository, times(1)).save(sessaoArgumentCaptor.capture());

        assertEquals(sessaoResponseDTO.getStatus(), sessao.getStatus());
        assertEquals(sessaoResponseDTO.getPauta().getIdPauta(), sessao.getPauta().getIdPauta());
        assertEquals(sessaoResponseDTO.getPauta().getDescricao(), sessao.getPauta().getDescricao());
        assertEquals(sessaoResponseDTO.getTempoAbertura(), sessao.getTempoAbertura());

        verify(pautaRepository, times(1)).findPautaDTOById(sessaoRequestDTO.getIdPauta());
    }

    @Test
    public void createSessaoWhenPautaIsNotFoundTest() {

        SessaoRequestDTO sessaoRequestDTO = new SessaoRequestDTO();
        sessaoRequestDTO.setIdPauta(1L);
        sessaoRequestDTO.setTempoAbertura(null);

        PautaResponseDTO pautaResponseDTO = new PautaResponseDTO();
        pautaResponseDTO.setIdPauta(1L);
        pautaResponseDTO.setDescricao("Pauta Teste para sessao");

        when(pautaRepository.findPautaDTOById(sessaoRequestDTO.getIdPauta())).thenReturn(null);

        Exception exception = assertThrows(PautaNotFoundException.class, () -> sessaoService.createSessao(sessaoRequestDTO));
        assertEquals("Pauta não encontrada!", exception.getMessage());
    }

    @Test
    public void createSessaoWhenIdPautaRequestIsNullTest() {

        SessaoRequestDTO sessaoRequestDTO = new SessaoRequestDTO();
        sessaoRequestDTO.setIdPauta(null);
        sessaoRequestDTO.setTempoAbertura(null);

        Exception exception = assertThrows(PautaNotFoundException.class, () -> sessaoService.createSessao(sessaoRequestDTO));
        assertEquals("Pauta não encontrada!", exception.getMessage());
    }
}
