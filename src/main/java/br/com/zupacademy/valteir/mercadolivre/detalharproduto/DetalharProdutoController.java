package br.com.zupacademy.valteir.mercadolivre.detalharproduto;

import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

@RestController
@RequestMapping("/produtos")
public class DetalharProdutoController {

    @Autowired
    private EntityManager manager;

    @GetMapping("/{id}")
    public ProdutoResponse detalhar(@PathVariable("id") Long idProduto) {
        Produto produto = manager.find(Produto.class, idProduto);

        Assert.notNull(produto, "Produto de id: "+idProduto+" não encontrado");

        return new ProdutoResponse(produto);
    }
}
