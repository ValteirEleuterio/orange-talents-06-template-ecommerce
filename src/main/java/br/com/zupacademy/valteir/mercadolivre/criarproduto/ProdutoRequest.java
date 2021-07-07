package br.com.zupacademy.valteir.mercadolivre.criarproduto;

import br.com.zupacademy.valteir.mercadolivre.config.validators.ExistingId;
import br.com.zupacademy.valteir.mercadolivre.criarcategoria.Categoria;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

public class ProdutoRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    @Size(min = 3)
    @Valid
    private Set<CaracteristicaRequest> caracteristicas;

    @NotBlank
    @Length(max = 1000)
    private String descricao;

    @NotNull
    @ExistingId(domainClass = Categoria.class, fieldName = "id")
    private Long idCategoria;

    public ProdutoRequest(String nome, BigDecimal valor, Integer quantidade, Set<CaracteristicaRequest> caracteristicas, String descricao, Long idCategoria) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
    }

    public Set<CaracteristicaRequest> getCaracteristicas() {
        return Collections.unmodifiableSet(caracteristicas);
    }

    public Produto toModel(Usuario usuario, EntityManager em) {
        Categoria categoria = em.find(Categoria.class, idCategoria);

        Assert.notNull(categoria, "Categoria de id: " +idCategoria+ " não esta cadastrada");

        return new Produto(nome, valor, quantidade, descricao, categoria, usuario, caracteristicas);
    }
}
