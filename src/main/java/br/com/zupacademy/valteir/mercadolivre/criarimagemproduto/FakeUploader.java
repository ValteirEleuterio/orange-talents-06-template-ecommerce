package br.com.zupacademy.valteir.mercadolivre.criarimagemproduto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FakeUploader implements Uploader{

    private static final String BASE_URL = "http://localhost:8080/";

    @Override
    public List<String> upload(List<MultipartFile> imagens) {
        return imagens.stream().map(i -> BASE_URL + i.getOriginalFilename()).collect(Collectors.toList());
    }
}
