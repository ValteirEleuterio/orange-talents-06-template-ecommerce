package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;

public interface AcaoSucessoCompra {

    void processa(Compra compra);
}
