package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@Service
public class EventosNovaCompra {

    private Set<AcaoSucessoCompra> acoesSucessoCompra;
    private Set<AcaoFalhaCompra> acoesFalhaCompra;

    public EventosNovaCompra(Set<AcaoSucessoCompra> acoesSucessoCompra, Set<AcaoFalhaCompra> acoesFalhaCompra) {
        this.acoesSucessoCompra = acoesSucessoCompra;
        this.acoesFalhaCompra = acoesFalhaCompra;
    }

    public void processa(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
        if(compra.processadaComSucesso()) {
            acoesSucessoCompra.forEach(a -> a.processa(compra));
        } else {
            acoesFalhaCompra.forEach(a -> a.processa(compra, uriComponentsBuilder));
        }
    }
}
