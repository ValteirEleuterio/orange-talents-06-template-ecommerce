package br.com.zupacademy.valteir.mercadolivre.emailutils;

import br.com.zupacademy.valteir.mercadolivre.criarperguntaproduto.Pergunta;

public class Email {

    private String destino;
    private String assunto;
    private String mensagem;

    public Email(String destino, String assunto, String mensagem) {
        this.destino = destino;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    public Email(Pergunta pergunta) {
        this.destino = pergunta.getEmailDonoProduto();
        this.assunto = "Pergunta";
        this.mensagem = pergunta.toString();
    }

    @Override
    public String toString() {
        return "Email{" +
                "destino='" + destino + '\'' +
                ", assunto='" + assunto + '\'' +
                ", mensagem='" + mensagem + '\'' +
                '}';
    }
}
