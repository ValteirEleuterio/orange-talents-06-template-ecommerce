package br.com.zupacademy.valteir.mercadolivre.criarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

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
}
