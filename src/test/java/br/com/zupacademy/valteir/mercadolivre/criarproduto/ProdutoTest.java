package br.com.zupacademy.valteir.mercadolivre.criarproduto;

import br.com.zupacademy.valteir.mercadolivre.criarcategoria.Categoria;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.SenhaLimpa;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class ProdutoTest {

    @DisplayName("um produto precisa de no mínimo 3 caracteristicas")
    @ParameterizedTest
    @MethodSource("geradorTeste1")
    void teste1(Set<CaracteristicaRequest> caracteristicas) {
        Categoria categoria = new Categoria("categoria");
        Usuario dono = new Usuario("email@email..com.br", new SenhaLimpa("123456"));

        new Produto("nome", BigDecimal.TEN, 10, "descricao", categoria, dono, caracteristicas);
    }

    static Stream<Arguments> geradorTeste1() {
        return Stream.of(
                Arguments.of(
                        Set.of(
                                new CaracteristicaRequest("key", "value"),
                                new CaracteristicaRequest("key2", "value2"),
                                new CaracteristicaRequest("key3", "value3")
                        )
                ),
                Arguments.of(
                        Set.of(
                                new CaracteristicaRequest("key", "value"),
                                new CaracteristicaRequest("key2", "value2"),
                                new CaracteristicaRequest("key3", "value3"),
                                new CaracteristicaRequest("key4", "value4")
                        )
                )
        );
    }


    @DisplayName("um produto não pode ser criado com menos de 3 caracteristicas")
    @ParameterizedTest
    @MethodSource("geradorTeste2")
    void teste2(Set<CaracteristicaRequest> caracteristicas) {
        Categoria categoria = new Categoria("categoria");
        Usuario dono = new Usuario("email@email..com.br", new SenhaLimpa("123456"));


        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Produto("nome", BigDecimal.TEN, 10, "descricao", categoria, dono, caracteristicas);
        });

    }

    static Stream<Arguments> geradorTeste2() {
        return Stream.of(
                Arguments.of(
                        Set.of(
                                new CaracteristicaRequest("key", "value"),
                                new CaracteristicaRequest("key2", "value2")
                        )
                ),
                Arguments.of(
                        Set.of(
                                new CaracteristicaRequest("key", "value")
                        )
                )
        );
    }


    @DisplayName("verifica estoque do prdouto")
    @ParameterizedTest
    @CsvSource({"1,1,true", "1,2,false", "4,2,true", "1,5,false" })
    void teste3(int estoque, int quantidadePedida, boolean resultadoEsperado) {
        Set<CaracteristicaRequest> caracteristicas = Set.of(
                new CaracteristicaRequest("key", "value"),
                new CaracteristicaRequest("key2", "value2"),
                new CaracteristicaRequest("key3", "value3")
        );
        Categoria categoria = new Categoria("categoria");
        Usuario dono = new Usuario("email@email.com.br", new SenhaLimpa("senhalimpa"));
        Produto produto = new Produto("nome", BigDecimal.TEN, estoque, "descricao", categoria, dono, caracteristicas);

        boolean resultado = produto.abateEstoque(quantidadePedida);

        Assertions.assertEquals(resultadoEsperado, resultado);
    }


    @DisplayName("Não abate estoque <= zero")
    @ParameterizedTest
    @CsvSource({"0", "-1", "-100"})
    void teste4(int quantidadePedida) {
        Set<CaracteristicaRequest> caracteristicas = Set.of(
                new CaracteristicaRequest("key", "value"),
                new CaracteristicaRequest("key2", "value2"),
                new CaracteristicaRequest("key3", "value3")
        );
        Categoria categoria = new Categoria("categoria");
        Usuario dono = new Usuario("email@email.com.br", new SenhaLimpa("senhalimpa"));
        Produto produto = new Produto("nome", BigDecimal.TEN, 10, "descricao", categoria, dono, caracteristicas);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            produto.abateEstoque(quantidadePedida);
        });
    }



}