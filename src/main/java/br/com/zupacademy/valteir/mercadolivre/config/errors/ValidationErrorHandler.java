package br.com.zupacademy.valteir.mercadolivre.config.errors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public List<ErroResponse> handle(BindException exception) {
        BindingResult result =  exception.getBindingResult();

        List<ErroResponse> erros = new ArrayList<>();

        result.getFieldErrors().forEach(e -> erros.add(
                new ErroResponse(e.getField(),e.getDefaultMessage())));

        result.getAllErrors().forEach(e -> erros.add(new ErroResponse(e.getDefaultMessage())));

        return erros;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ErroResponse handle(RuntimeException exception) {
        return new ErroResponse(exception.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErroResponse handle(Exception exception) {
        return new ErroResponse("Ocorreu um erro interno\n"+ exception.getMessage());
    }
}
