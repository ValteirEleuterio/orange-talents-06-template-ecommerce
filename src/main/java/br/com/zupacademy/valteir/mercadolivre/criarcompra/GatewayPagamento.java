package br.com.zupacademy.valteir.mercadolivre.criarcompra;

import org.springframework.web.util.UriComponentsBuilder;

public enum GatewayPagamento {
    PAGSEGURO {
        @Override
        public String criaUrlRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
            String urlRetornoPagseguro = uriComponentsBuilder.path("/retorno-pagseguro/{id}")
                    .buildAndExpand(compra.getId()).toString();

            return "pagseguro.com?returnId="+compra.getId()+"&redirectUrl="+urlRetornoPagseguro;
        }
    },
    PAYPAL {
        @Override
        public String criaUrlRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
            String urlRetornoPaypal = uriComponentsBuilder.path("/retorno-paypal/{id}")
                    .buildAndExpand(compra.getId()).toString();

            return "paypal.com?buyerId="+compra.getId()+"&redirectUrl="+urlRetornoPaypal;
        }
    };




    abstract String criaUrlRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder);
}
