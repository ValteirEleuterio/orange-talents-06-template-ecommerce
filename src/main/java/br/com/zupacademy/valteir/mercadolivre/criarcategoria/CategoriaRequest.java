package br.com.zupacademy.valteir.mercadolivre.criarcategoria;

import br.com.zupacademy.valteir.mercadolivre.config.validators.ExistingId;
import br.com.zupacademy.valteir.mercadolivre.config.validators.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class CategoriaRequest {

    @NotBlank
    @UniqueValue(fieldName = "nome", domainClass = Categoria.class)
    private String nome;

    @ExistingId(fieldName = "id", domainClass = Categoria.class)
    private Long idCategoriaMae;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CategoriaRequest(String nome) {
        this.nome = nome;
    }

    public void setIdCategoriaMae(Long idCategoriaMae) {
        this.idCategoriaMae = idCategoriaMae;
    }

    public Categoria toModel(EntityManager em) {
        Categoria categoria = new Categoria(nome);

        if(idCategoriaMae != null) {
            Categoria categoriaMae = em.find(Categoria.class, idCategoriaMae);
            Assert.notNull(categoriaMae, "Categoria de id: " + idCategoriaMae + " não está cadastrada no sistema");
            categoria.setCategoriaMae(categoriaMae);
        }

        return categoria;
    }
}
