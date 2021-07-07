package br.com.zupacademy.valteir.mercadolivre.criarproduto;

import br.com.zupacademy.valteir.mercadolivre.criarcategoria.Categoria;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private BigDecimal valor;
    private Integer quantidade;
    private LocalDateTime instanteCadastro = LocalDateTime.now();

    @Column(length = 1000)
    private String descricao;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<Caracteristica> caracteristicas;


    public Produto(@NotBlank String nome, @NotNull @Positive BigDecimal valor, @NotNull @Positive Integer quantidade,
                   @NotBlank @Length(max = 1000) String descricao, @NotNull Categoria categoria,
                   @NotNull Usuario usuario, @NotNull @Size(min = 3) Set<CaracteristicaRequest> listaCaracteristicas) {

        Assert.hasText(nome, "nome não pode ser nulo");
        Assert.notNull(valor, "valor não pode ser nulo");
        Assert.state(valor.compareTo(BigDecimal.ZERO) > 0, "valor deve ser maior que zero");
        Assert.notNull(quantidade, "quantidade deve ser maior que zero");
        Assert.state(quantidade > 0, "quantidade deve ser maior que zero");
        Assert.hasText(descricao, "descricao não pode ser nulo");
        Assert.state(descricao.length() <= 1000, "descricao não pode ter mais que 1000 caracteres");
        Assert.notNull(categoria, "categoria não pode ser nulo");
        Assert.notNull(usuario, "usuario não pode ser nulo");
        Assert.notNull(listaCaracteristicas, "listaCaracteristicas não pode ser nula");


        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
        this.caracteristicas = toCaracteristicas(listaCaracteristicas);

        Assert.isTrue(listaCaracteristicas.size() >= 3, "um produto deve ter no minimo 3 caracteristicas");
    }

    private Set<Caracteristica> toCaracteristicas(Set<CaracteristicaRequest> listaCaracteristicas) {
        return listaCaracteristicas
                .stream()
                .map(c -> c.toModel(this))
                .collect(Collectors.toSet());
    }

    public Set<Caracteristica> getCaracteristicas() {
        return Collections.unmodifiableSet(caracteristicas);
    }
}
