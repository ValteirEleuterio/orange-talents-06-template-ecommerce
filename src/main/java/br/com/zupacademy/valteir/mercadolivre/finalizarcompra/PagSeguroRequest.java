package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.config.validators.UniqueValue;
import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PagSeguroRequest implements RetornoGatewayPagamento {

    @NotBlank
    @UniqueValue(domainClass = Transacao.class, fieldName = "idPagamento")
    private String idPagamento;
    @NotNull
    @Pattern(regexp = "SUCESSO|ERRO")
    private String status;

    public PagSeguroRequest(String idPagamento, String status) {
        this.idPagamento = idPagamento;
        this.status = status;
    }

    @Override
    public Transacao toModel(Compra compra) {
        StatusTransacao statusTransacao = this.status.equals("SUCESSO") ? StatusTransacao.SUCESSO : StatusTransacao.FALHA;
        return new Transacao(idPagamento, compra, statusTransacao);
    }
}
