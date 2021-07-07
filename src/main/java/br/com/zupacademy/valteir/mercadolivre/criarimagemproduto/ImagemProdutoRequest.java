package br.com.zupacademy.valteir.mercadolivre.criarimagemproduto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

public class ImagemProdutoRequest {

    @Size(min = 1)
    @NotNull
    private List<MultipartFile> imagens;

    public void setImagens(List<MultipartFile> imagens) {
        this.imagens = imagens;
    }

    public List<MultipartFile> getImagens() {
        return Collections.unmodifiableList(this.imagens);
    }


}
