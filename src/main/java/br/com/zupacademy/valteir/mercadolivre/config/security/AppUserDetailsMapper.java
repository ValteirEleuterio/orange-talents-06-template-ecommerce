package br.com.zupacademy.valteir.mercadolivre.config.security;

import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AppUserDetailsMapper implements UserDetailsMapper {
    @Override
    public UserDetails map(Object shouldBeASystemUser) {
        return new UsuarioLogado((Usuario)shouldBeASystemUser);
    }
}
