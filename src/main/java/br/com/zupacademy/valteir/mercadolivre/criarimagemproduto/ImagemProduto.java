package br.com.zupacademy.valteir.mercadolivre.criarimagemproduto;

import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ImagemProduto {

    @Id
    @GeneratedValue
    private Long id;
    private String link;

    @ManyToOne
    private Produto produto;

    @Deprecated
    private ImagemProduto() {}

    public ImagemProduto(@URL @NotBlank String link, @NotNull Produto produto) {
        Assert.hasText(link, "link deve estar preenchido");
        Assert.notNull(produto, "produto não pode ser nulo");

        this.link = link;
        this.produto = produto;
    }

    public String getLink() {
        return link;
    }
}
