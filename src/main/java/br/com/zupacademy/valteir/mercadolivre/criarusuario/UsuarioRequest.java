package br.com.zupacademy.valteir.mercadolivre.criarusuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String senha;

    public UsuarioRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario toModel() {
        return new Usuario(this.email, new SenhaLimpa(senha));
    }
}
