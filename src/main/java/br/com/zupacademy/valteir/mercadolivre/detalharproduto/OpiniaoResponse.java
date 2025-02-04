package br.com.zupacademy.valteir.mercadolivre.detalharproduto;

import br.com.zupacademy.valteir.mercadolivre.criaropiniaoproduto.Opiniao;

public class OpiniaoResponse {

    private String titulo;
    private String descricao;
    private Integer nota;

    public OpiniaoResponse(Opiniao opiniao) {
        this.titulo = opiniao.getTitulo();
        this.descricao = opiniao.getDescricao();
        this.nota = opiniao.getNota();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getNota() {
        return nota;
    }
}
