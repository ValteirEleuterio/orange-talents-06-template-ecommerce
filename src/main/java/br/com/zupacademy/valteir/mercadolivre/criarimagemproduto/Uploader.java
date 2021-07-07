package br.com.zupacademy.valteir.mercadolivre.criarimagemproduto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Uploader {

    List<String> upload(List<MultipartFile> imagens);
}
