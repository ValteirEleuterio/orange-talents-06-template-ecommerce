package br.com.zupacademy.valteir.mercadolivre.criarperguntaproduto;

import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta {
    @Id
    @GeneratedValue
    private Long id;
    private String titulo;
    private LocalDateTime instanteCriacao = LocalDateTime.now();
    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Usuario usuario;

    public Pergunta(@NotBlank String titulo, @NotNull Produto produto, @NotNull Usuario usuario) {
        Assert.hasText(titulo, "Titulo é obrigatório");
        Assert.notNull(produto, "Produto não pode ser nulo");
        Assert.notNull(usuario, "Usuario não pode ser nulo");

        this.titulo = titulo;
        this.produto = produto;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Pergunta{" +
                "titulo='" + titulo + '\'' +
                ", data=" + instanteCriacao +
                ", produto=" + produto.getNome() +
                ", usuario=" + usuario.getEmail() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getNomeProduto() {
        return produto.getNome();
    }

    public String getEmailUsuario() {
        return usuario.getEmail();
    }

    public String getEmailDonoProduto() {
        return produto.getEmailDono();
    }
}
