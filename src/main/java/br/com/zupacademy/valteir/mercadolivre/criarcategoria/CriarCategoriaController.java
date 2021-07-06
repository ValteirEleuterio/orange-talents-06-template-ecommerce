package br.com.zupacademy.valteir.mercadolivre.criarcategoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categoria")
public class CriarCategoriaController {

    @Autowired
    private EntityManager manager;

    @PostMapping
    @Transactional
    public void criar(@Valid @RequestBody CategoriaRequest request) {
        Categoria categoria = request.toModel(manager);
        manager.persist(categoria);
    }
}
