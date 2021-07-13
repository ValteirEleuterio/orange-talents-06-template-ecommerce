package br.com.zupacademy.valteir.mercadolivre.sistemasexternos;

import javax.validation.constraints.NotNull;

public class NotaRequest {

    @NotNull
    private Long idCompra;
    @NotNull
    private Long idComprador;

    public NotaRequest(Long idCompra, Long idComprador) {
        this.idCompra = idCompra;
        this.idComprador = idComprador;
    }

    @Override
    public String toString() {
        return "NotaRequest{" +
                "idCompra=" + idCompra +
                ", idComprador=" + idComprador +
                '}';
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdComprador() {
        return idComprador;
    }
}
