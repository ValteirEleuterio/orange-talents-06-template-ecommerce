package br.com.zupacademy.valteir.mercadolivre.criarperguntaproduto;

public class PerguntaResponse {

    private Long id;
    private String titulo;
    private String produto;
    private String usuario;

    public PerguntaResponse(Pergunta pergunta) {
        this.id = pergunta.getId();
        this.titulo = pergunta.getTitulo();
        this.produto = pergunta.getNomeProduto();
        this.usuario = pergunta.getEmailUsuario();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getProduto() {
        return produto;
    }

    public String getUsuario() {
        return usuario;
    }
}
