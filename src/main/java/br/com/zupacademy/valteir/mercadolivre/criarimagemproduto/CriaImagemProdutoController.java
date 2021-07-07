package br.com.zupacademy.valteir.mercadolivre.criarimagemproduto;

import br.com.zupacademy.valteir.mercadolivre.config.errors.ErroResponse;
import br.com.zupacademy.valteir.mercadolivre.config.security.UsuarioLogado;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class CriaImagemProdutoController {

    @Autowired
    private EntityManager manager;

    @Autowired
    private Uploader uploader;

    @PostMapping("/{id}/imagens")
    @Transactional
    public ResponseEntity<?> criar(@Valid ImagemProdutoRequest request, @PathVariable("id") Long idProduto,
                                @AuthenticationPrincipal UsuarioLogado usuarioLogado) {

        Produto produto = manager.find(Produto.class, idProduto);

        Assert.notNull(produto, "Produto de id: "+idProduto+" não encontrado");

        boolean usuarioEhDono = produto.usuarioDonoEh(usuarioLogado.get());

        if(!usuarioEhDono) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ErroResponse("" +
                            "Usuário " + usuarioLogado.getUsername() + " não é dono do produto de id: " + idProduto
                    ));
        }

        List<String> linksImagens = uploader.upload(request.getImagens());

        produto.setLinksImagens(linksImagens);

        manager.merge(produto);

        return ResponseEntity.ok().build();
    }
}
