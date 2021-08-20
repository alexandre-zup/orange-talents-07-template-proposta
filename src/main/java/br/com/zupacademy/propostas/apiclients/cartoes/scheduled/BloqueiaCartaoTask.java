package br.com.zupacademy.propostas.apiclients.cartoes.scheduled;

import br.com.zupacademy.propostas.apiclients.cartoes.BloqueioRequest;
import br.com.zupacademy.propostas.apiclients.cartoes.CartaoClient;
import br.com.zupacademy.propostas.config.TimeValues;
import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.repositories.CartaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static br.com.zupacademy.propostas.model.enums.EstadoCartao.BLOQUEIO_PENDENTE;

/**
 * Após um cartão ser bloqueado no sistema, ele fica com estado BLOQUEIO_PENDENTE, pois ainda precisa ser bloqueado
 * no sistema legado. Essa classe é responsável por verificar esses cartões pendentes periodicamente e solicitar o
 * bloqueio no sistema legado.
 */
@Service
public class BloqueiaCartaoTask {
    Logger log = LoggerFactory.getLogger(BloqueiaCartaoTask.class);
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private CartaoClient cartaoClient;

    @Scheduled(fixedDelay = 5 * TimeValues.SEGUNDO)
    private void bloqueiaCartoes() {
        Pageable paginacao = PageRequest.ofSize(10);
        Page<Cartao> paginaDeCartoes = cartaoRepository.findAllByEstado(BLOQUEIO_PENDENTE, paginacao);


        System.out.println(Arrays.toString(paginaDeCartoes.toList().toArray()));

        while(paginaDeCartoes.hasNext()) {
            executaBloqueios(paginaDeCartoes);
            paginaDeCartoes = cartaoRepository.findAllByEstado(BLOQUEIO_PENDENTE, paginacao);
        }

        executaBloqueios(paginaDeCartoes);
    }

    private void executaBloqueios(Page<Cartao> paginaDeCartoes) {
        paginaDeCartoes.forEach(cartao -> {
            BloqueioRequest request = new BloqueioRequest();
            try {
                cartaoClient.solicitaBloqueio(cartao.getId(), request);
                cartao.bloqueioConfirmadoNoLegado();
                cartaoRepository.save(cartao);
                log.info("Cartão bloqueado no sistema legado: " + cartao.getId());
            } catch (FeignException e) {
                log.error("Erro ao bloquear cartão no sistema legado: " + cartao.getId());
            }
        });
    }
}
