package br.com.zupacademy.valteir.mercadolivre.criarproduto;

import br.com.zupacademy.valteir.mercadolivre.criarcategoria.Categoria;
import br.com.zupacademy.valteir.mercadolivre.criarimagemproduto.ImagemProduto;
import br.com.zupacademy.valteir.mercadolivre.criaropiniaoproduto.Opiniao;
import br.com.zupacademy.valteir.mercadolivre.criarperguntaproduto.Pergunta;
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
import java.util.List;
import java.util.Set;
import java.util.function.Function;
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
    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private List<ImagemProduto> imagens;
    @OneToMany(mappedBy = "produto")
    private List<Pergunta> perguntas;
    @OneToMany(mappedBy = "produto")
    private List<Opiniao> opinioes;


    @Deprecated
    private Produto() {}

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

    public String getEmailDono() {
        return usuario.getEmail();
    }

    public <T> List<T> mapCaracteristicas(Function<Caracteristica, T> funcao) {
        return caracteristicas.stream().map(funcao).collect(Collectors.toList());
    }

    public boolean usuarioDonoEh(Usuario usuario) {
        return this.usuario.equals(usuario);
    }

    public void setLinksImagens(List<String> linksImagens) {
        this.imagens = linksImagens.stream().map(
                url -> new ImagemProduto(url, this)
        ).collect(Collectors.toList());
    }

    public String getNome() {
        return nome;
    }

    public <T> List<T> mapImagens(Function<ImagemProduto, T> function) {
        return imagens.stream().map(function).collect(Collectors.toList());
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public <T> List<T> mapPerguntas(Function<Pergunta, T> function) {
        return perguntas.stream().map(function).collect(Collectors.toList());
    }

    public <T> List<T> mapOpinioes(Function<Opiniao, T> function) {
        return opinioes.stream().map(function).collect(Collectors.toList());
    }

    public Integer getNumeroOpinioes() {
        return opinioes.size();
    }

    public Double getMediaOpinioes() {
        return opinioes.stream().mapToInt(Opiniao::getNota).average().orElse(0.0);
    }
}
