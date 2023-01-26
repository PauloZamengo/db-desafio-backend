package br.com.dbdesafiobackend.votacao.service;

import br.com.dbdesafiobackend.dto.ContagemVotosResponseDTO;
import br.com.dbdesafiobackend.dto.PautaResponseDTO;
import br.com.dbdesafiobackend.dto.VotoRequestDTO;
import br.com.dbdesafiobackend.dto.VotoResponseDTO;
import br.com.dbdesafiobackend.enums.StatusSessaoEnum;
import br.com.dbdesafiobackend.votacao.entity.Pauta;
import br.com.dbdesafiobackend.votacao.entity.Sessao;
import br.com.dbdesafiobackend.votacao.entity.Voto;
import br.com.dbdesafiobackend.votacao.exception.*;
import br.com.dbdesafiobackend.votacao.repository.PautaRepository;
import br.com.dbdesafiobackend.votacao.repository.SessaoRepository;
import br.com.dbdesafiobackend.votacao.repository.VotoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VotoServiceTest {

    @InjectMocks
    private VotoService votoService;

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private PautaRepository pautaRepository;


    @Test
    public void createVotoOKTest() throws Exception {

        VotoRequestDTO votoRequestDTO = new VotoRequestDTO();
        votoRequestDTO.setIdPauta(1L);
        votoRequestDTO.setValor("SIM");
        votoRequestDTO.setIdAssociado(1);

        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("Essa pauta é de teste?");

        PautaResponseDTO pautaResponseDTO = new PautaResponseDTO();
        pautaResponseDTO.setIdPauta(pauta.getIdPauta());
        pautaResponseDTO.setDescricao(pauta.getDescricao());

        Voto voto = new Voto();
        voto.setIdAssociado(1);
        voto.setValor(true);
        voto.setPauta(pauta);

        Sessao sessao = new Sessao();
        sessao.setIdSessao(1L);
        sessao.setTempoAbertura(20);
        sessao.setDataHoraAbertura(LocalDateTime.now());
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());
        sessao.setPauta(pauta);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);
        when(votoRepository.findVotoByIdPautaAndIdAssociado(votoRequestDTO.getIdPauta(), votoRequestDTO.getIdAssociado())).thenReturn(null);
        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(pautaResponseDTO);
        VotoResponseDTO votoResponse = votoService.createVoto(votoRequestDTO);

        ArgumentCaptor<Voto> votoArgumentCaptor = ArgumentCaptor.forClass(Voto.class);
        verify(votoRepository, times(1)).save(votoArgumentCaptor.capture());

        assertEquals(votoResponse.isValor(), voto.isValor());
        assertEquals(votoResponse.getIdAssociado(), voto.getIdAssociado());
        assertEquals(votoResponse.getIdVoto(), voto.getIdVoto());
        assertEquals(votoResponse.getPauta().getIdPauta(), voto.getPauta().getIdPauta());
        assertEquals(votoResponse.getPauta().getDescricao(), voto.getPauta().getDescricao());

    }

    @Test
    public void createVotoOKWhenValorIsNAOTest() throws Exception {

        VotoRequestDTO votoRequestDTO = new VotoRequestDTO();
        votoRequestDTO.setIdPauta(1L);
        votoRequestDTO.setValor("NAO");
        votoRequestDTO.setIdAssociado(1);

        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("Essa pauta é de teste?");

        PautaResponseDTO pautaResponseDTO = new PautaResponseDTO();
        pautaResponseDTO.setIdPauta(pauta.getIdPauta());
        pautaResponseDTO.setDescricao(pauta.getDescricao());

        Voto voto = new Voto();
        voto.setIdAssociado(1);
        voto.setValor(false);
        voto.setPauta(pauta);

        Sessao sessao = new Sessao();
        sessao.setIdSessao(1L);
        sessao.setTempoAbertura(20);
        sessao.setDataHoraAbertura(LocalDateTime.now());
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());
        sessao.setPauta(pauta);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);
        when(votoRepository.findVotoByIdPautaAndIdAssociado(votoRequestDTO.getIdPauta(), votoRequestDTO.getIdAssociado())).thenReturn(null);
        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(pautaResponseDTO);
        VotoResponseDTO votoResponse = votoService.createVoto(votoRequestDTO);

        ArgumentCaptor<Voto> votoArgumentCaptor = ArgumentCaptor.forClass(Voto.class);
        verify(votoRepository, times(1)).save(votoArgumentCaptor.capture());

        assertEquals(votoResponse.isValor(), voto.isValor());
        assertEquals(votoResponse.getIdAssociado(), voto.getIdAssociado());
        assertEquals(votoResponse.getIdVoto(), voto.getIdVoto());
        assertEquals(votoResponse.getPauta().getIdPauta(), voto.getPauta().getIdPauta());
        assertEquals(votoResponse.getPauta().getDescricao(), voto.getPauta().getDescricao());

    }

    @Test
    public void createVotoValidateValorVotoExceptionTest() throws Exception {
        VotoRequestDTO votoRequestDTO = new VotoRequestDTO();
        votoRequestDTO.setIdPauta(1L);
        votoRequestDTO.setValor("123");
        votoRequestDTO.setIdAssociado(1);


        Exception exception = assertThrows(ValorVotoException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("O campo 'VALOR' deve conter apenas os valores 'SIM' ou 'NAO'!", exception.getMessage());
    }

    @Test
    public void createVotoValidateSessaoIsOpenWhenSessaoStatusIsFechadaExceptionTest() {
        VotoRequestDTO votoRequestDTO = new VotoRequestDTO();
        votoRequestDTO.setIdPauta(1L);
        votoRequestDTO.setValor("SIM");
        votoRequestDTO.setIdAssociado(1);

        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("Essa pauta é de teste?");

        Sessao sessao = new Sessao();
        sessao.setIdSessao(1L);
        sessao.setTempoAbertura(20);
        sessao.setDataHoraAbertura(LocalDateTime.now());
        sessao.setStatus(StatusSessaoEnum.FECHADA.getStatusSessao());
        sessao.setPauta(pauta);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);

        Exception exception = assertThrows(SessaoExpiredException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Essa sessão já está encerrada!", exception.getMessage());
    }

    @Test
    public void createVotoValidateSessaoIsOpenWhenTempoAberturaExpiredExceptionTest() {
        VotoRequestDTO votoRequestDTO = new VotoRequestDTO();
        votoRequestDTO.setIdPauta(1L);
        votoRequestDTO.setValor("SIM");
        votoRequestDTO.setIdAssociado(1);

        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("Essa pauta é de teste?");

        Sessao sessao = new Sessao();
        sessao.setIdSessao(1L);
        sessao.setTempoAbertura(1);
        sessao.setDataHoraAbertura(LocalDateTime.of(2023,01,21,20,00));
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());
        sessao.setPauta(pauta);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);

        Exception exception = assertThrows(SessaoExpiredException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Essa sessão já está encerrada!", exception.getMessage());
    }

    @Test
    public void createVotoValidateSetStatusSessaoExceptionTest() {
        VotoRequestDTO votoRequestDTO = new VotoRequestDTO();
        votoRequestDTO.setIdPauta(1L);
        votoRequestDTO.setValor("SIM");
        votoRequestDTO.setIdAssociado(1);

        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("Essa pauta é de teste?");

        Sessao sessao = new Sessao();
        sessao.setIdSessao(1L);
        sessao.setTempoAbertura(1);
        sessao.setDataHoraAbertura(LocalDateTime.of(2023,01,21,20,00));
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());
        sessao.setPauta(pauta);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);

        Exception exception = assertThrows(SessaoExpiredException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Essa sessão já está encerrada!", exception.getMessage());
        assertEquals(sessao.getStatus(), StatusSessaoEnum.FECHADA.getStatusSessao());
    }

    @Test
    public void createVotoSessaoIsNotFoundExceptionTest() {
        VotoRequestDTO votoRequestDTO = new VotoRequestDTO();
        votoRequestDTO.setIdPauta(1L);
        votoRequestDTO.setValor("SIM");
        votoRequestDTO.setIdAssociado(1);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(null);

        Exception exception = assertThrows(SessaoNotFoundException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Sessão não encontrada!", exception.getMessage());
    }

    @Test
    public void createVotoSessaoPautaIsNotFoundExceptionTest() {
        VotoRequestDTO votoRequestDTO = new VotoRequestDTO();
        votoRequestDTO.setIdPauta(123L);
        votoRequestDTO.setValor("SIM");
        votoRequestDTO.setIdAssociado(1);

        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(null);

        Exception exception = assertThrows(PautaNotFoundException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Pauta não encontrada!", exception.getMessage());
    }

    @Test
    public void createVotoValidateIfExistVotoAssociadoExceptionTest() {
        VotoRequestDTO votoRequestDTO = new VotoRequestDTO();
        votoRequestDTO.setIdPauta(1L);
        votoRequestDTO.setValor("SIM");
        votoRequestDTO.setIdAssociado(1);

        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("Essa pauta é de teste?");

        Sessao sessao = new Sessao();
        sessao.setIdSessao(1L);
        sessao.setTempoAbertura(10);
        sessao.setDataHoraAbertura(LocalDateTime.now());
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());
        sessao.setPauta(pauta);

        VotoResponseDTO voto = new VotoResponseDTO();
        voto.setIdAssociado(1);
        voto.setValor(true);
        voto.setIdVoto(1L);
        voto.setPauta(pauta);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);
        when(votoRepository.findVotoByIdPautaAndIdAssociado(votoRequestDTO.getIdPauta(), votoRequestDTO.getIdAssociado())).thenReturn(voto);

        Exception exception = assertThrows(AssociadoException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Já existe voto desse associado nessa pauta!", exception.getMessage());
    }

    @Test
    public void createVotoWhenPautaDTOIsNotFoundExceptionTest() {
        VotoRequestDTO votoRequestDTO = new VotoRequestDTO();
        votoRequestDTO.setIdPauta(1l);
        votoRequestDTO.setValor("SIM");
        votoRequestDTO.setIdAssociado(1);

        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("TEste");

        Sessao sessao = new Sessao();
        sessao.setIdSessao(1L);
        sessao.setTempoAbertura(10);
        sessao.setDataHoraAbertura(LocalDateTime.now());
        sessao.setStatus(StatusSessaoEnum.ABERTA.getStatusSessao());
        sessao.setPauta(pauta);


        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);
        when(votoRepository.findVotoByIdPautaAndIdAssociado(votoRequestDTO.getIdPauta(), votoRequestDTO.getIdAssociado())).thenReturn(null);
        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(null);

        Exception exception = assertThrows(PautaNotFoundException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Pauta não encontrada!", exception.getMessage());
    }



    @Test
    public void countVotosOKTest() {

        Long idPauta = 1L;
        when(votoRepository.findVotosByIdPauta(idPauta)).thenReturn(getListVotos());
        ContagemVotosResponseDTO contagem = votoService.countVotos(1L);

        assertEquals(contagem.getVotosNao(), 2);
        assertEquals(contagem.getVotosSim(), 3);
        assertEquals(contagem.getVotosTotal(), 5);
        assertEquals(contagem.getIdPauta(), idPauta);

    }

    @Test
    public void countVotosWhenPautaIsNotFoundExceptionTest() {

        Long idPauta = null;
        ContagemVotosResponseDTO contagem = votoService.countVotos(1L);

        Exception exception = assertThrows(PautaNotFoundException.class, () -> votoService.countVotos(null));
        assertEquals("Pauta não encontrada!", exception.getMessage());

    }

    @Test
    public void countVotosWhenPautaIsNotFoundExceptionTest2() {

        Long idPauta = 1L;
        when(votoRepository.findVotosByIdPauta(idPauta)).thenReturn(null);

        Exception exception = assertThrows(VotosNotFoundException.class, () -> votoService.countVotos(idPauta));
        assertEquals("Não foi possível encontrar os votos dessa pauta!", exception.getMessage());

    }

    private List<Voto> getListVotos() {

        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("TEste");

        Voto voto1 = new Voto();
        voto1.setIdAssociado(1);
        voto1.setValor(false);
        voto1.setPauta(pauta);

        Voto voto2 = new Voto();
        voto2.setIdAssociado(1);
        voto2.setValor(false);
        voto2.setPauta(pauta);

        Voto voto3 = new Voto();
        voto3.setIdAssociado(1);
        voto3.setValor(true);
        voto3.setPauta(pauta);

        Voto voto4 = new Voto();
        voto4.setIdAssociado(1);
        voto4.setValor(true);
        voto4.setPauta(pauta);

        Voto voto5 = new Voto();
        voto5.setIdAssociado(1);
        voto5.setValor(true);
        voto5.setPauta(pauta);

        List<Voto> votos = new ArrayList<>();
        votos.addAll(List.of(voto1, voto2, voto3, voto4, voto5));
        return votos;

    }
}
