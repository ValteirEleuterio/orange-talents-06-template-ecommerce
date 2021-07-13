package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transacao {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String idPagamento;
    private LocalDateTime instanteProcessamento = LocalDateTime.now();
    @NotNull
    @ManyToOne
    private Compra compra;
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusTransacao status;

    @Deprecated
    private Transacao() { }

    public Transacao(@NotBlank String idPagamento, @NotNull Compra compra, @NotNull StatusTransacao status) {
        this.idPagamento = idPagamento;
        this.compra = compra;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return idPagamento.equals(transacao.idPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPagamento);
    }

    public boolean estaConcluida() {
        return status.equals(StatusTransacao.SUCESSO);
    }
}
