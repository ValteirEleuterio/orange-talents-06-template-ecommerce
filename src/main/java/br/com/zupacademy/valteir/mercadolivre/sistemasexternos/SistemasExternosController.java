package br.com.zupacademy.valteir.mercadolivre.sistemasexternos;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sistemas-internos")
public class SistemasExternosController {

    @PostMapping("/notas-fiscais")
    public void nota(@Valid @RequestBody NotaRequest  request) throws InterruptedException {
        System.out.println("criando nota "+request);
        Thread.sleep(200);
    }

    @PostMapping("/ranking")
    public void ranking(@Valid @RequestBody RankingRequest request) throws InterruptedException {
        System.out.println("atualizando ranking "+ request);
        Thread.sleep(200);
    }
}
