package br.com.zupacademy.valteir.mercadolivre.criarcompra;

import br.com.zupacademy.valteir.mercadolivre.config.security.UsuarioLogado;
import br.com.zupacademy.valteir.mercadolivre.criarcategoria.Categoria;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.CaracteristicaRequest;
import br.com.zupacademy.valteir.mercadolivre.criarproduto.Produto;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.SenhaLimpa;
import br.com.zupacademy.valteir.mercadolivre.criarusuario.Usuario;
import br.com.zupacademy.valteir.mercadolivre.emailutils.Email;
import br.com.zupacademy.valteir.mercadolivre.emailutils.EnviadorEmail;
import br.com.zupacademy.valteir.mercadolivre.emailutils.EnviadorEmailFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Set;

class CriarCompraControllerTest {

    private EntityManager manager = Mockito.mock(EntityManager.class);
    private Usuario comprador = new Usuario("usuario@zup.com.br", new SenhaLimpa("senhaaa"));
    private Usuario dono = new Usuario("usuario6@email.com.br", new SenhaLimpa("senhaaa"));
    private UsuarioLogado usuarioLogado = new UsuarioLogado(comprador);
    private EnviadorEmail enviadorEmail = Mockito.mock(EnviadorEmail.class);
    private Categoria categoria = new Categoria("categoria");
    private Set<CaracteristicaRequest> caracteristicas =  Set.of(
            new CaracteristicaRequest("key", "value"),
            new CaracteristicaRequest("key2", "value2"),
            new CaracteristicaRequest("key3", "value3")
    );
    private UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080");
    private CriarCompraController controller = new CriarCompraController(manager, enviadorEmail);

    @Test
    @DisplayName("redireciona para gateway em caso de estoque aceito")
    void teste1() throws Exception {
        Produto produto = new Produto("nome", BigDecimal.TEN, 1, "descricao", categoria, dono, caracteristicas);
        Mockito.when(manager.find(Produto.class, 1l)).thenReturn(produto);

        Mockito.doAnswer(invocation -> {
            Compra compra = invocation.<Compra>getArgument(0);
            ReflectionTestUtils.setField(compra, "id", 1l);
            return null;
        }).when(manager).persist(Mockito.any(Compra.class));

        CompraRequest request = new CompraRequest(1l, 1, GatewayPagamento.PAGSEGURO);
        String endereco = controller.criar(request, usuarioLogado, uriComponentsBuilder);

        Assertions.assertEquals("pagseguro.com?returnId=1&redirectUrl=http://localhost:8080/retorno-pagseguro/1", endereco);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        Mockito.verify(enviadorEmail).enviar(new Email(produto.getEmailDono(), "Produto comprado", argument.capture()));
    }

    @Test
    @DisplayName("lança exception em caso de estoque não disponível")
    void teste2() throws Exception {
        Produto produto = new Produto("nome", BigDecimal.TEN, 1, "descricao", categoria, dono, caracteristicas);
        Mockito.when(manager.find(Produto.class, 1l)).thenReturn(produto);

        CompraRequest request = new CompraRequest(1l, 2, GatewayPagamento.PAGSEGURO);

        Assertions.assertThrows(BindException.class, () -> {
            controller.criar(request, usuarioLogado, uriComponentsBuilder);
        });

        Mockito.verify(enviadorEmail, Mockito.never()).enviar(Mockito.any(Email.class));
    }
}