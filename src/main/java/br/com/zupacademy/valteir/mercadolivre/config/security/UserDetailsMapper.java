package br.com.zupacademy.valteir.mercadolivre.config.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsMapper {

    UserDetails map(Object shouldBeASystemUser);
}
