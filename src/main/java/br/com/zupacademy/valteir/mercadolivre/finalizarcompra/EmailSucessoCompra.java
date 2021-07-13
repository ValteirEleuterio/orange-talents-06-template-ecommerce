package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;
import br.com.zupacademy.valteir.mercadolivre.emailutils.Email;
import br.com.zupacademy.valteir.mercadolivre.emailutils.EnviadorEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailSucessoCompra implements AcaoSucessoCompra {

    private EnviadorEmail enviadorEmail;

    public EmailSucessoCompra(EnviadorEmail enviadorEmail) {
        this.enviadorEmail = enviadorEmail;
    }

    @Override
    public void processa(Compra compra) {
        enviadorEmail.enviar(new Email(compra.getComprador().getEmail(), "Compra finalizada com sucesso!", compra.toString()));
    }
}
