package br.com.zupacademy.valteir.mercadolivre.criarperguntaproduto;

import br.com.zupacademy.valteir.mercadolivre.config.security.UsuarioLogado;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import br.com.zupacademy.valteir.mercadolivre.emailutils.Email;
import br.com.zupacademy.valteir.mercadolivre.emailutils.EnviadorEmail;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class CriarPerguntaProdutoController {

    @Autowired
    private EntityManager manager;

    @Autowired
    private EnviadorEmail enviadorEmail;

    @PostMapping("/{id}/perguntas")
    @Transactional
    public PerguntaResponse criar(@PathVariable("id") Long idProduto, @Valid @RequestBody PerguntaRequest request,
                      @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Produto produto = manager.find(Produto.class, idProduto);

        Assert.notNull(produto, "Produto de id: "+idProduto+" não encontrado");

        Pergunta pergunta = request.toModel(produto, usuarioLogado.get());

        manager.persist(pergunta);

        enviadorEmail.enviar(new Email(pergunta));

        return new PerguntaResponse(pergunta);
    }
}
