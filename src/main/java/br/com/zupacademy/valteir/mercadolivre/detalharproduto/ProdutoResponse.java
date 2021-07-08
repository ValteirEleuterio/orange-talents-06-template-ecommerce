package br.com.zupacademy.valteir.mercadolivre.detalharproduto;

import br.com.zupacademy.valteir.mercadolivre.criarimagemproduto.ImagemProduto;
import br.com.zupacademy.valteir.mercadolivre.criaropiniaoproduto.Opiniao;
import br.com.zupacademy.valteir.mercadolivre.criarperguntaproduto.Pergunta;
import br.com.zupacademy.valteir.mercadolivre.criarperguntaproduto.PerguntaResponse;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoResponse {

    private String nome;
    private String descricao;
    private Double mediaOpinioes;
    private Integer numeroOpinioes;
    private BigDecimal preco;
    private List<String> imagens;
    private List<CaracteristicaResponse> caracteristicas;
    private List<OpiniaoResponse> opinioes;
    private List<PerguntaResponse> perguntas;

    public ProdutoResponse(Produto produto) {
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.mediaOpinioes = produto.getMediaOpinioes();
        this.numeroOpinioes = produto.getNumeroOpinioes();
        this.preco = produto.getValor();
        this.imagens = produto.mapImagens(ImagemProduto::getLink);
        this.perguntas = produto.mapPerguntas(PerguntaResponse::new);
        this.caracteristicas =  produto.mapCaracteristicas(CaracteristicaResponse::new);
        this.opinioes = produto.mapOpinioes(OpiniaoResponse::new);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getMediaOpinioes() {
        return mediaOpinioes;
    }

    public Integer getNumeroOpinioes() {
        return numeroOpinioes;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public List<CaracteristicaResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public List<OpiniaoResponse> getOpinioes() {
        return opinioes;
    }

    public List<PerguntaResponse> getPerguntas() {
        return perguntas;
    }
}
