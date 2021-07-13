package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;
import io.jsonwebtoken.lang.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
public class FinalizarCompraController {

    private EntityManager manager;
    private EventosNovaCompra eventosNovaCompra;

    public FinalizarCompraController(EntityManager manager, EventosNovaCompra eventosNovaCompra) {
        this.manager = manager;
        this.eventosNovaCompra = eventosNovaCompra;
    }

    @PostMapping("/retorno-pagseguro/{id}")
    @Transactional
    public void processamentoPagSeguro(@PathVariable("id") Long idCompra, @Valid PagSeguroRequest request, UriComponentsBuilder uriComponentsBuilder) {
        processa(idCompra, request, uriComponentsBuilder);
    }

    @PostMapping("/retorno-paypal/{id}")
    @Transactional
    public void processamentoPagSeguro(@PathVariable("id") Long idCompra, @Valid PayPalRequest request, UriComponentsBuilder uriComponentsBuilder) {
        processa(idCompra, request, uriComponentsBuilder);
    }

    private void processa(Long idCompra, RetornoGatewayPagamento request, UriComponentsBuilder uriComponentsBuilder) {
        Compra compra = manager.find(Compra.class, idCompra);

        Assert.notNull(compra, "Compra de id: "+ idCompra +" não encontrada");

        compra.adicionaTransacao(request);

        eventosNovaCompra.processa(compra, uriComponentsBuilder);

        manager.merge(compra);
    }
}
