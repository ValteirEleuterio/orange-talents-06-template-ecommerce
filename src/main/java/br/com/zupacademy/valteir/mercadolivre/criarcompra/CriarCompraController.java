package br.com.zupacademy.valteir.mercadolivre.criarcompra;

import br.com.zupacademy.valteir.mercadolivre.config.security.UsuarioLogado;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import br.com.zupacademy.valteir.mercadolivre.emailutils.Email;
import br.com.zupacademy.valteir.mercadolivre.emailutils.EnviadorEmail;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/compras")
public class CriarCompraController {

    @Autowired
    private EntityManager manager;

    @Autowired
    private EnviadorEmail enviadorEmail;

    @PostMapping
    @Transactional
    public String criar(@Valid @RequestBody CompraRequest request, @AuthenticationPrincipal UsuarioLogado usuarioLogado,
                        UriComponentsBuilder uriComponentsBuilder) throws BindException {
        Produto produto = manager.find(Produto.class, request.getIdProduto());

        Assert.notNull(produto, "Produto de id: "+request.getIdProduto()+" não encontrado");

        boolean abateu = produto.abateEstoque(request.getQuantidade());

        if(abateu) {
            Compra compra = new Compra(request.getQuantidade(), produto, usuarioLogado.get(), request.getPagamento());
            manager.persist(compra);
            enviadorEmail.enviar(new Email(produto.getEmailDono(), "Produto comprado", compra.toString()));
            return compra.getRedirecionamento(uriComponentsBuilder);
        }

        BindException estoqueInsuficiente = new BindException(request, "CompraRequest");
        estoqueInsuficiente.reject(null, "Não foi possível realizar a compra por falta de estoque");
        throw estoqueInsuficiente;
    }

}
