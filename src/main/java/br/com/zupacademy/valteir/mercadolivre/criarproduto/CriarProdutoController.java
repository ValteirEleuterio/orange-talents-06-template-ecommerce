package br.com.zupacademy.valteir.mercadolivre.criarproduto;

import br.com.zupacademy.valteir.mercadolivre.config.security.UsuarioLogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class CriarProdutoController {

    @Autowired
    private EntityManager manager;

    @PostMapping
    @Transactional
    public void criar(@Valid @RequestBody ProdutoRequest request, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Produto produto = request.toModel(usuarioLogado.get(), manager);
        manager.persist(produto);
    }
}
