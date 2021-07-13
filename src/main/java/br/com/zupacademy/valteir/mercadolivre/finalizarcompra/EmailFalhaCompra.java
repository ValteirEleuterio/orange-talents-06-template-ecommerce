package br.com.zupacademy.valteir.mercadolivre.finalizarcompra;

import br.com.zupacademy.valteir.mercadolivre.criarcompra.Compra;
import br.com.zupacademy.valteir.mercadolivre.emailutils.Email;
import br.com.zupacademy.valteir.mercadolivre.emailutils.EnviadorEmail;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EmailFalhaCompra implements AcaoFalhaCompra{

    private EnviadorEmail enviadorEmail;

    public EmailFalhaCompra(EnviadorEmail enviadorEmail) {
        this.enviadorEmail = enviadorEmail;
    }

    @Override
    public void processa(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
        enviadorEmail.enviar(new Email(
                compra.getComprador().getEmail(),
                "O pagamento falhou, tente novamente",
                compra.getRedirecionamento(uriComponentsBuilder)));
    }
}
