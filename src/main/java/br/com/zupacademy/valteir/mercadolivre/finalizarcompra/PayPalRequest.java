package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class  PayPalRequest implements RetornoGatewayPagamento {

    @NotBlank
    private String idPagamento;
    @Max(1)
    @Min(0)
    @NotNull
    private Integer status;

    public PayPalRequest(String idPagamento, Integer status) {
        this.idPagamento = idPagamento;
        this.status = status;
    }

    @Override
    public Transacao toModel(Compra compra) {
        StatusTransacao statusTransacao = status == 1 ? StatusTransacao.SUCESSO : StatusTransacao.FALHA;
        return new Transacao(idPagamento, compra, statusTransacao);
    }
}
