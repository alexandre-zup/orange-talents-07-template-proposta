package br.com.zupacademy.propostas.apiclients.cartoes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "cartaoClient",url = "${values.consulta-cartao-url}")
public interface CartaoClient {
    @GetMapping(value = "/cartoes")
    CartaoResponse consultaCartao(@RequestParam String idProposta);

    @PostMapping(value = "/cartoes/{id}/bloqueios")
    BloqueioResponse solicitaBloqueio(@PathVariable String id, @RequestBody BloqueioRequest request);
}
