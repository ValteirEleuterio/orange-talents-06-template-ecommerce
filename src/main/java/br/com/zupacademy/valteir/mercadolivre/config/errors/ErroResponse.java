package br.com.zupacademy.valteir.mercadolivre.config.errors;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class ErroResponse {

    private String campo;
    private String mensagem;

    public ErroResponse(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public ErroResponse(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }
    public String getMensagem() {
        return mensagem;
    }


}
