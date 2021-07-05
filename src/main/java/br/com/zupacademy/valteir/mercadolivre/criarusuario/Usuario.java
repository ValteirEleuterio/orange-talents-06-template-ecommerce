package br.com.zupacademy.valteir.mercadolivre.criarusuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String senha;
    private LocalDateTime instanteCriacao = LocalDateTime.now();

    public Usuario(String email, SenhaLimpa senhaLimpa) {
        this.email = email;
        this.senha = senhaLimpa.hash();
    }
}
