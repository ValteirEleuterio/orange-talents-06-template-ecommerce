package br.com.zupacademy.valteir.mercadolivre.emailutils;

import org.springframework.stereotype.Component;

@Component
public class EnviadorEmailFake implements EnviadorEmail{
    @Override
    public void enviar(Email email) {
        System.out.println(email);
    }
}
