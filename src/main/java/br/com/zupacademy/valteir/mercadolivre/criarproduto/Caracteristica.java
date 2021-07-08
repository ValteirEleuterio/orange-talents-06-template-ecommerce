package br.com.zupacademy.valteir.mercadolivre.criarproduto;

import io.jsonwebtoken.lang.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Caracteristica {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String descricao;

    @ManyToOne
    private Produto produto;

    @Deprecated
    private Caracteristica() {}

    public Caracteristica(@NotBlank String nome, @NotBlank String descricao, @NotNull Produto produto) {
        Assert.hasText(nome, "nome não pode ser nulo");
        Assert.hasText(descricao, "descricao não pode ser nulo");
        Assert.notNull(produto, "produto não pode ser nulo");

        this.nome = nome;
        this.descricao = descricao;
        this.produto = produto;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
