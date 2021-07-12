package br.com.zupacademy.valteir.mercadolivre.criarcompra;

import br.com.zupacademy.valteir.mercadolivre.config.validators.ExistingId;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraRequest {

    @NotNull
    @ExistingId(domainClass = Produto.class, fieldName = "id")
    private Long idProduto;
    @NotNull
    @Positive
    private Integer quantidade;
    @NotNull

    private GatewayPagamento gateway;

    public CompraRequest(Long idProduto, Integer quantidade, GatewayPagamento gateway) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.gateway = gateway;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public GatewayPagamento getPagamento() {
        return gateway;
    }
}
