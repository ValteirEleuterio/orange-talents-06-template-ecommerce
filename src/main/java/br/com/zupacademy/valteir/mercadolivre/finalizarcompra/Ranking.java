package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;
import br.com.zupacademy.valteir.mercadolivre.sistemasexternos.RankingRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Ranking implements AcaoSucessoCompra {
    @Override
    public void processa(Compra compra) {
        RestTemplate restTemplate = new RestTemplate();
        RankingRequest request = new RankingRequest(compra.getId(), compra.getVendedor().getId());

        restTemplate.postForEntity("http://localhost:8080/sistemas-internos/ranking", request, String.class);
    }
}
