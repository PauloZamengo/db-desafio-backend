package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.constants.Constants;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.dto.SessaoRequestDTO;
import br.com.dbdesafiobackend.dto.SessaoResponseDTO;
import br.com.dbdesafiobackend.enums.StatusSessaoEnum;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import br.com.dbdesafiobackend.votacao.exception.PautaNotFoundException;
import br.com.dbdesafiobackend.votacao.exception.SessaoExpiredException;
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
        SessaoRequestDTO sessaoRequestDTO = buildSessaoRequestDTO(20);
        Pauta pauta = buildPauta();
        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO();
        Sessao sessao = buildSessao(pauta, 20, StatusSessaoEnum.ABERTA);

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
        SessaoRequestDTO sessaoRequestDTO = buildSessaoRequestDTO(null);
        Pauta pauta = buildPauta();
        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO();
        Sessao sessao = buildSessao(pauta, Constants.TEMPO_ABERTURA_DEFAULT, StatusSessaoEnum.ABERTA);

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
        SessaoRequestDTO sessaoRequestDTO = buildSessaoRequestDTO(null);
        when(pautaRepository.findPautaDTOById(sessaoRequestDTO.getIdPauta())).thenReturn(null);

        Exception exception = assertThrows(PautaNotFoundException.class, () -> sessaoService.createSessao(sessaoRequestDTO));
        assertEquals("Pauta não encontrada!", exception.getMessage());
    }

    @Test
    public void createSessaoWhenSessaoIsOpenTest() {
        SessaoRequestDTO sessaoRequestDTO = buildSessaoRequestDTO(15);
        Pauta pauta = buildPauta();
        Sessao sessao = buildSessao(pauta, 15, StatusSessaoEnum.ABERTA);

        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO();
        when(sessaoRepository.findSessaoByIdPauta(sessaoRequestDTO.getIdPauta())).thenReturn(sessao);

        Exception exception = assertThrows(SessaoExpiredException.class, () -> sessaoService.createSessao(sessaoRequestDTO));
        assertEquals("Já existe uma sessão aberta referente a essa pauta!", exception.getMessage());
    }

    public SessaoRequestDTO buildSessaoRequestDTO(Integer tempoAbertura) {
        return SessaoRequestDTO.builder()
                .idPauta(1L)
                .tempoAbertura(tempoAbertura)
                .build();
    }

    public Pauta buildPauta() {
        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("Pauta Teste para sessao");
        return pauta;
    }

    public PautaResponseDTO buildPautaResponseDTO(){
        return PautaResponseDTO.builder()
                .idPauta(1L)
                .descricao("Pauta Teste para sessao")
                .build();
    }

    public Sessao buildSessao(Pauta pauta, Integer tempoAbertura, StatusSessaoEnum statusSessaoEnum) {
        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setTempoAbertura(tempoAbertura);
        sessao.setStatus(statusSessaoEnum.getStatusSessao());
        return  sessao;
    }

}
