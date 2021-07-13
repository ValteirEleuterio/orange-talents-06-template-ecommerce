package br.com.zupacademy.valteir.mercadolivre.criarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import br.com.zupacademy.valteir.mercadolivre.finalizarcompra.RetornoGatewayPagamento;
import br.com.zupacademy.valteir.mercadolivre.finalizarcompra.Transacao;
import io.jsonwebtoken.lang.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Compra {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Positive
    private Integer quantidade;
    private BigDecimal valorProduto;
    @NotNull
    @ManyToOne
    private Produto produto;
    @NotNull
    @ManyToOne
    private Usuario comprador;
    @NotNull
    @Enumerated(EnumType.STRING)
    private GatewayPagamento pagamento;
    @Enumerated(EnumType.STRING)
    private StatusCompra status;
    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Transacao> transacoes = new HashSet<>();

    @Deprecated
    private Compra() {}

    public Compra(@NotNull @Positive Integer quantidade, @NotNull Produto produto,
                  @NotNull Usuario comprador, @NotNull GatewayPagamento pagamento) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.comprador = comprador;
        this.pagamento = pagamento;
        this.valorProduto = produto.getValor();
        this.status = StatusCompra.INICIADA;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "quantidade=" + quantidade +
                ", produto=" + produto.getNome() +
                ", comprador=" + comprador.getEmail() +
                ", status=" + status +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getRedirecionamento(UriComponentsBuilder uriComponentsBuilder) {
        return pagamento.criaUrlRetorno(this, uriComponentsBuilder);
    }

    public Usuario getComprador() {
        return this.comprador;
    }

    public Usuario getVendedor() {
        return produto.getDono();
    }


    public void adicionaTransacao(RetornoGatewayPagamento request) {
        Transacao transacao = request.toModel(this);

        Assert.isTrue(!transacoes.contains(transacao), "Não é possível adicionar essa transação pois a mesma já foi adicionada anteriormente");
        Assert.isTrue(transacoesConcluidas().isEmpty(), "Não é possível adicionar a transação pois a compra já possui transacoes concluidas");

        transacoes.add(transacao);
    }

    private Set<Transacao> transacoesConcluidas() {
        Set<Transacao> transacoesConcluidas = transacoes.stream().filter(Transacao::estaConcluida).collect(Collectors.toSet());

        Assert.isTrue(transacoesConcluidas.size() <= 1, "Houve algum problema, a conta de id "+id+" possui mais de uma transacao concluida com sucesso");

        return transacoesConcluidas;
    }

    public boolean processadaComSucesso() {
        return !transacoesConcluidas().isEmpty();
    }
}
