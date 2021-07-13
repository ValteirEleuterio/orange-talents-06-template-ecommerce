package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;
import org.springframework.web.util.UriComponentsBuilder;

public interface AcaoFalhaCompra {

    void processa(Compra compra, UriComponentsBuilder uriComponentsBuilder);
}
