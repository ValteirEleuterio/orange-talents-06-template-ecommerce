package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;

public interface RetornoGatewayPagamento {

    Transacao toModel(Compra compra);
}
