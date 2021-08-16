package br.com.zupacademy.propostas.apiclients.avaliacaofinanceira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "avalicaoFinanceiraClient",url = "${values.avaliacao-financeira-url}")
public interface AvaliacaoFinanceiraClient {
    @RequestMapping(method = RequestMethod.POST, value = "/solicitacao")
    AvaliacaoFinanceiraResponse avalia(@RequestBody AvaliacaoFinanceiraRequest request);
}
