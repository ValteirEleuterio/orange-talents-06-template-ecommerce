package br.com.zupacademy.valteir.mercadolivre.criaropiniaoproduto;

import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Opiniao {

    @Id
    @GeneratedValue
    private Long id;
    private String titulo;
    @Column(length = 500)
    private String descricao;
    private Integer nota;
    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Usuario usuario;

    @Deprecated
    public Opiniao() {}

    public Opiniao(@NotBlank String titulo, @NotBlank @Length(max = 500) String descricao,
                   @NotNull @Min(1) @Max(5) Integer nota, @NotNull Produto produto, @NotNull Usuario usuario) {

        Assert.notNull(produto, "produto não pode ser nulo");
        Assert.notNull(usuario, "usuario não pode ser nulo");

        this.titulo = titulo;
        this.descricao = descricao;
        this.nota = nota;
        this.produto = produto;
        this.usuario = usuario;
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
