package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;
import br.com.zupacademy.valteir.mercadolivre.sistemasexternos.NotaRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotaFiscal implements AcaoSucessoCompra {
    @Override
    public void processa(Compra compra) {
        RestTemplate restTemplate = new RestTemplate();
        NotaRequest request = new NotaRequest(compra.getId(), compra.getComprador().getId());

        restTemplate.postForEntity("http://localhost:8080/sistemas-internos/notas-fiscais", request, String.class);
    }
}
