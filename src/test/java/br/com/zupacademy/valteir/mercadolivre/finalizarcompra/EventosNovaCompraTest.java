package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;
import br.com.zupacademy.valteir.mercadolivre.criarcompra.TestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

class EventosNovaCompraTest {

    @Test
    @DisplayName("deveria disparar eventos de sucesso")
    void teste1() {
        Compra compraConcluida = TestBuilder.novaCompra().concluida();
        UriComponentsBuilder uriBuilder = Mockito.mock(UriComponentsBuilder.class);
        AcaoSucessoCompra acaoSucesso = Mockito.mock(AcaoSucessoCompra.class);
        AcaoFalhaCompra acaoFalha = Mockito.mock(AcaoFalhaCompra.class);
        Set<AcaoSucessoCompra> eventosSucesso = Set.of(acaoSucesso);
        Set<AcaoFalhaCompra> eventosFalha = Set.of(acaoFalha);
        EventosNovaCompra eventosNovaCompra = new EventosNovaCompra(eventosSucesso, eventosFalha);

        eventosNovaCompra.processa(compraConcluida, uriBuilder);

        Mockito.verify(acaoSucesso).processa(compraConcluida);
        Mockito.verify(acaoFalha, Mockito.never()).processa(compraConcluida, uriBuilder);
    }

    @Test
    @DisplayName("deveria disparar eventos de falha")
    void teste2() {
        UriComponentsBuilder uriBuilder = Mockito.mock(UriComponentsBuilder.class);
        Compra compraConcluida = TestBuilder.novaCompra().queNaoFoiPagaComSucesso();
        AcaoSucessoCompra acaoSucesso = Mockito.mock(AcaoSucessoCompra.class);
        AcaoFalhaCompra acaoFalha = Mockito.mock(AcaoFalhaCompra.class);
        Set<AcaoSucessoCompra> eventosSucesso = Set.of(acaoSucesso);
        Set<AcaoFalhaCompra> eventosFalha = Set.of(acaoFalha);
        EventosNovaCompra eventosNovaCompra = new EventosNovaCompra(eventosSucesso, eventosFalha);

        eventosNovaCompra.processa(compraConcluida, uriBuilder);

        Mockito.verify(acaoFalha).processa(compraConcluida, uriBuilder);
        Mockito.verify(acaoSucesso, Mockito.never()).processa(compraConcluida);
    }
}