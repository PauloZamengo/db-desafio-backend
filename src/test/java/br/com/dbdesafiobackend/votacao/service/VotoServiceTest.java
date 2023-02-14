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
        VotoRequestDTO votoRequestDTO = buildVotoRequestDTO("SIM");
        Pauta pauta = buildPauta();
        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO(pauta);
        Voto voto = buildVoto(true, pauta);
        Sessao sessao = buildSessao(20, LocalDateTime.now(), StatusSessaoEnum.ABERTA, pauta);

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
        VotoRequestDTO votoRequestDTO = buildVotoRequestDTO("NAO");
        Pauta pauta = buildPauta();
        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO(pauta);
        Voto voto = buildVoto(false, pauta);
        Sessao sessao = buildSessao(20, LocalDateTime.now(), StatusSessaoEnum.ABERTA, pauta);

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
        VotoRequestDTO votoRequestDTO = buildVotoRequestDTO("123");

        Exception exception = assertThrows(ValorVotoException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("O campo 'VALOR' deve conter apenas os valores 'SIM' ou 'NAO'!", exception.getMessage());
    }

    @Test
    public void createVotoValidateSessaoIsOpenWhenSessaoStatusIsFechadaExceptionTest() {
        VotoRequestDTO votoRequestDTO = buildVotoRequestDTO("SIM");
        Pauta pauta = buildPauta();
        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO(pauta);
        Sessao sessao = buildSessao(20, LocalDateTime.now(), StatusSessaoEnum.FECHADA, pauta);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);
        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(pautaResponseDTO);

        Exception exception = assertThrows(SessaoExpiredException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Essa sessão já está encerrada!", exception.getMessage());
    }

    @Test
    public void createVotoValidateSessaoIsOpenWhenTempoAberturaExpiredExceptionTest() {
        VotoRequestDTO votoRequestDTO = buildVotoRequestDTO("SIM");
        Pauta pauta = buildPauta();
        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO(pauta);
        Sessao sessao = buildSessao(1,
                LocalDateTime.of(2023,01,21,20,00),
                StatusSessaoEnum.ABERTA,
                pauta);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);
        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(pautaResponseDTO);

        Exception exception = assertThrows(SessaoExpiredException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Essa sessão já está encerrada!", exception.getMessage());
    }

    @Test
    public void createVotoValidateSetStatusSessaoExceptionTest() {
        VotoRequestDTO votoRequestDTO = buildVotoRequestDTO("SIM");
        Pauta pauta = buildPauta();
        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO(pauta);
        Sessao sessao = buildSessao(1, LocalDateTime.of(2023,01,21,20,00),
                StatusSessaoEnum.ABERTA,
                pauta);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);
        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(pautaResponseDTO);

        Exception exception = assertThrows(SessaoExpiredException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Essa sessão já está encerrada!", exception.getMessage());
        assertEquals(sessao.getStatus(), StatusSessaoEnum.FECHADA.getStatusSessao());
    }

    @Test
    public void createVotoSessaoIsNotFoundExceptionTest() {
        VotoRequestDTO votoRequestDTO = buildVotoRequestDTO("SIM");
        PautaResponseDTO pautaResponseDTO = new PautaResponseDTO();
        pautaResponseDTO.setIdPauta(1L);
        pautaResponseDTO.setDescricao("Essa pauta é de teste?");

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(null);
        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(pautaResponseDTO);

        Exception exception = assertThrows(SessaoNotFoundException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Sessão não encontrada!", exception.getMessage());
    }

    @Test
    public void createVotoSessaoPautaIsNotFoundExceptionTest() {
        VotoRequestDTO votoRequestDTO = buildVotoRequestDTO("SIM");
        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(null);

        Exception exception = assertThrows(PautaNotFoundException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Pauta não encontrada!", exception.getMessage());
    }

    @Test
    public void createVotoValidateIfExistVotoAssociadoExceptionTest() {
        VotoRequestDTO votoRequestDTO = buildVotoRequestDTO("SIM");
        Pauta pauta = buildPauta();
        Sessao sessao = buildSessao(10, LocalDateTime.now(), StatusSessaoEnum.ABERTA, pauta);
        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO(pauta);

        VotoResponseDTO voto = new VotoResponseDTO();
        voto.setIdAssociado(1);
        voto.setValor(true);
        voto.setIdVoto(1L);
        voto.setPauta(pauta);

        when(sessaoRepository.findSessaoByIdPauta(votoRequestDTO.getIdPauta())).thenReturn(sessao);
        when(votoRepository.findVotoByIdPautaAndIdAssociado(votoRequestDTO.getIdPauta(), votoRequestDTO.getIdAssociado())).thenReturn(voto);
        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(pautaResponseDTO);

        Exception exception = assertThrows(AssociadoException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Já existe voto desse associado nessa pauta!", exception.getMessage());
    }

    @Test
    public void createVotoWhenPautaDTOIsNotFoundExceptionTest() {
        VotoRequestDTO votoRequestDTO = buildVotoRequestDTO("SIM");
        when(pautaRepository.findPautaDTOById(votoRequestDTO.getIdPauta())).thenReturn(null);

        Exception exception = assertThrows(PautaNotFoundException.class, () -> votoService.createVoto(votoRequestDTO));
        assertEquals("Pauta não encontrada!", exception.getMessage());
    }



    @Test
    public void countVotosOKTest() {
        Long idPauta = 1L;
        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO2();

        when(votoRepository.findVotosByIdPauta(idPauta)).thenReturn(getListVotos());
        when(pautaRepository.findPautaDTOById(idPauta)).thenReturn(pautaResponseDTO);
        ContagemVotosResponseDTO contagem = votoService.countVotos(1L);

        assertEquals(contagem.getVotosNao(), 2);
        assertEquals(contagem.getVotosSim(), 3);
        assertEquals(contagem.getVotosTotal(), 5);
        assertEquals(contagem.getIdPauta(), idPauta);
    }

    @Test
    public void countVotosWhenPautaIsNotFoundExceptionTest() {
        Long idPauta = null;
        Exception exception = assertThrows(PautaNotFoundException.class, () -> votoService.countVotos(idPauta));
        assertEquals("Pauta não encontrada!", exception.getMessage());
    }

    @Test
    public void countVotosWhenPautaIsNotFoundExceptionTest2() {
        Long idPauta = 1L;
        when(votoRepository.findVotosByIdPauta(idPauta)).thenReturn(null);
        PautaResponseDTO pautaResponseDTO = buildPautaResponseDTO2();
        when(pautaRepository.findPautaDTOById(idPauta)).thenReturn(pautaResponseDTO);
        Exception exception = assertThrows(VotosNotFoundException.class, () -> votoService.countVotos(idPauta));
        assertEquals("Não foi possível encontrar os votos dessa pauta!", exception.getMessage());
    }

    private List<Voto> getListVotos() {
        Pauta pauta = buildPauta();
        Voto voto1 = buildVoto(false, pauta);
        Voto voto2 = buildVoto(false, pauta);
        Voto voto3 = buildVoto(true, pauta);
        Voto voto4 = buildVoto(true, pauta);
        Voto voto5 = buildVoto(true, pauta);

        List<Voto> votos = new ArrayList<>();
        votos.addAll(List.of(voto1, voto2, voto3, voto4, voto5));
        return votos;
    }

    public VotoRequestDTO buildVotoRequestDTO(String valor) {
        return VotoRequestDTO.builder()
                .idPauta(1L)
                .valor(valor)
                .idAssociado(1)
                .build();
    }

    public Pauta buildPauta() {
        Pauta pauta = new Pauta();
        pauta.setIdPauta(1L);
        pauta.setDescricao("Essa pauta é de teste?");
        return pauta;
    }

    public PautaResponseDTO buildPautaResponseDTO(Pauta pauta) {
        return PautaResponseDTO.builder()
                .idPauta(pauta.getIdPauta())
                .descricao(pauta.getDescricao())
                .build();
    }

    public PautaResponseDTO buildPautaResponseDTO2() {
        return PautaResponseDTO.builder()
                .idPauta(1L)
                .descricao("Essa pauta é de teste?")
                .build();
    }

    public Voto buildVoto(boolean valor, Pauta pauta) {
        Voto voto = new Voto();
        voto.setIdAssociado(1);
        voto.setValor(valor);
        voto.setPauta(pauta);
        return voto;
    }

    public Sessao buildSessao(int tempoAbertura, LocalDateTime localDateTime, StatusSessaoEnum status, Pauta pauta) {
        Sessao sessao = new Sessao();
        sessao.setIdSessao(1L);
        sessao.setTempoAbertura(tempoAbertura);
        sessao.setDataHoraAbertura(localDateTime);
        sessao.setStatus(status.getStatusSessao());
        sessao.setPauta(pauta);
        return sessao;
    }
}
