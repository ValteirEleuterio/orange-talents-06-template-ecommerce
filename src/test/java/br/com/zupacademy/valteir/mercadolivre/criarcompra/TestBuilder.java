package br.com.zupacademy.valteir.mercadolivre.criarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcategoria.Categoria;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.CaracteristicaRequest;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.SenhaLimpa;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import br.com.zupacademy.valteir.mercadolivre.finalizarcompra.RetornoGatewayPagamento;
import br.com.zupacademy.valteir.mercadolivre.finalizarcompra.StatusTransacao;
import br.com.zupacademy.valteir.mercadolivre.finalizarcompra.Transacao;

import java.math.BigDecimal;
import java.util.Set;

public class TestBuilder {

    public static class CompraFixture {
        private Compra compra;

        public CompraFixture(Compra compra) {
            this.compra = compra;
        }

        public Compra concluida() {
            RetornoGatewayPagamento retornoSucesso = (compra -> new Transacao("1", compra, StatusTransacao.SUCESSO));
            this.compra.adicionaTransacao(retornoSucesso);
            return compra;
        }

        public Compra queNaoFoiPagaComSucesso() {
            RetornoGatewayPagamento retornoSucesso = (compra -> new Transacao("1", compra, StatusTransacao.FALHA));
            this.compra.adicionaTransacao(retornoSucesso);
            return compra;
        }
    }

    public static CompraFixture novaCompra() {
        Set<CaracteristicaRequest> caracteristicas = Set.of(
                new CaracteristicaRequest("key", "value"),
                new CaracteristicaRequest("key2", "value2"),
                new CaracteristicaRequest("key3", "value3")
        );
        Usuario dono = new Usuario("dono@email.com.br", new SenhaLimpa("senhaaa"));
        Usuario comprador = new Usuario("comprador@email.com.br", new SenhaLimpa("senhaaa"));
        Categoria categoria = new Categoria("categoria");
        Produto produto = new Produto("nome", BigDecimal.TEN, 10, "descricao", categoria, dono, caracteristicas);


        return new CompraFixture(new Compra(1, produto, comprador, GatewayPagamento.PAYPAL));
    }

}
