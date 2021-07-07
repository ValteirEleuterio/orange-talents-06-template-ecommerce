package br.com.zupacademy.valteir.mercadolivre.criaropiniaoproduto;

import br.com.zupacademy.valteir.mercadolivre.config.security.UsuarioLogado;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class CriarOpiniaoProdutoController {

    @Autowired
    private EntityManager manager;

    @PostMapping("/{id}/opiniao")
    @Transactional
    public void criar(@PathVariable("id") Long idProduto, @Valid @RequestBody OpiniaoRequest request, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Produto produto = manager.find(Produto.class, idProduto);

        Assert.notNull(produto, "Produto de id: "+idProduto+" não encontrado");

        Opiniao opiniao = request.toModel(produto, usuarioLogado.get());

        manager.persist(opiniao);
    }
}
