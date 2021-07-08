package br.com.zupacademy.valteir.mercadolivre.criarperguntaproduto;

import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class PerguntaRequest {

    @NotBlank
    private String titulo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PerguntaRequest(String titulo) {
        this.titulo = titulo;
    }

    public Pergunta toModel(Produto produto, Usuario usuario) {
        return new Pergunta(titulo, produto, usuario);
    }


}
