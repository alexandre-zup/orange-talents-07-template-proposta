package br.com.zupacademy.propostas.apiclients.vinculacaocartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cartaoClient",url = "${values.consulta-cartao-url}")
public interface CartaoClient {
    @RequestMapping(method = RequestMethod.GET, value = "/cartoes")
    CartaoResponse consultaCartao(@RequestParam String idProposta);
}
