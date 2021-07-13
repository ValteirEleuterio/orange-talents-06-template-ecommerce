package br.com.zupacademy.valteir.mercadolivre.criarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcategoria.Categoria;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.CaracteristicaRequest;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.SenhaLimpa;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import br.com.zupacademy.valteir.mercadolivre.finalizarcompra.RetornoGatewayPagamento;
import br.com.zupacademy.valteir.mercadolivre.finalizarcompra.StatusTransacao;
import br.com.zupacademy.valteir.mercadolivre.finalizarcompra.Transacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class CompraTest {

    @Test
    @DisplayName("deveria adicionar uma transação")
    void teste1() {
        Compra novaCompra = novaCompra();

        RetornoGatewayPagamento retornoGatewayPagamento = (compra) -> new Transacao("1", compra, StatusTransacao.SUCESSO);

        novaCompra.adicionaTransacao(retornoGatewayPagamento);
    }

    @Test
    @DisplayName("não pode aceitar um transacao igual")
    void teste2() {
        Compra novaCompra = novaCompra();

        RetornoGatewayPagamento retornoGatewayPagamento = (compra) -> new Transacao("1", compra, StatusTransacao.FALHA);

        novaCompra.adicionaTransacao(retornoGatewayPagamento);

        RetornoGatewayPagamento retornoGatewayPagamento2 = (compra) -> new Transacao("1", compra, StatusTransacao.SUCESSO);


        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            novaCompra.adicionaTransacao(retornoGatewayPagamento2);
        });
    }

    @Test
    @DisplayName("Não pode aceitar transacao se a compra já foi concluida")
    void teste3() {
        Compra novaCompra = novaCompra();

        RetornoGatewayPagamento retornoGatewayPagamento = (compra) -> new Transacao("1", compra, StatusTransacao.SUCESSO);

        novaCompra.adicionaTransacao(retornoGatewayPagamento);

        RetornoGatewayPagamento retornoGatewayPagamento2 = (compra) -> new Transacao("12", compra, StatusTransacao.SUCESSO);


        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            novaCompra.adicionaTransacao(retornoGatewayPagamento2);
        });

    }

    @ParameterizedTest
    @MethodSource("geradorTeste4")
    @DisplayName("Deveria verificar se uma compra foi concluida com sucesso")
    void teste4(boolean resultadoEsperado, Collection<RetornoGatewayPagamento> retornos) {
        Compra novaCompra = novaCompra();

        retornos.forEach(novaCompra::adicionaTransacao);

        Assertions.assertEquals(resultadoEsperado, novaCompra.processadaComSucesso());

    }

    private static Stream<Arguments> geradorTeste4() {
        RetornoGatewayPagamento retornoGatewayPagamento = (compra) -> new Transacao("1", compra, StatusTransacao.SUCESSO);
        RetornoGatewayPagamento retornoGatewayPagamento2 = (compra) -> new Transacao("1", compra, StatusTransacao.FALHA);

        return Stream.of(
                Arguments.of(true, List.of(retornoGatewayPagamento)),
                Arguments.of(false, List.of(retornoGatewayPagamento2)),
                Arguments.of(false, List.of())
        );
    }

    private Compra novaCompra() {
        Set<CaracteristicaRequest> caracteristicas = Set.of(
                new CaracteristicaRequest("key", "value"),
                new CaracteristicaRequest("key2", "value2"),
                new CaracteristicaRequest("key3", "value3")
        );
        Usuario dono = new Usuario("dono@email.com.br", new SenhaLimpa("senhaaa"));
        Usuario comprador = new Usuario("comprador@email.com.br", new SenhaLimpa("senhaaa"));
        Categoria categoria = new Categoria("categoria");
        Produto produto = new Produto("nome", BigDecimal.TEN, 10, "descricao", categoria, dono, caracteristicas);

        return new Compra(1, produto, comprador, GatewayPagamento.PAYPAL);
    }

}