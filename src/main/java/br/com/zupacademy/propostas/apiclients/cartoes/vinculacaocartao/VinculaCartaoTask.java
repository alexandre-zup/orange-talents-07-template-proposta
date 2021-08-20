package br.com.zupacademy.propostas.apiclients.cartoes.vinculacaocartao;

import br.com.zupacademy.propostas.apiclients.cartoes.CartaoClient;
import br.com.zupacademy.propostas.apiclients.cartoes.CartaoResponse;
import br.com.zupacademy.propostas.config.TimeValues;
import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.entities.Proposta;
import br.com.zupacademy.propostas.model.enums.EstadoProposta;
import br.com.zupacademy.propostas.model.repositories.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VinculaCartaoTask {
    Logger log = LoggerFactory.getLogger(VinculaCartaoTask.class);

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private CartaoClient cartaoClient;

    /**
     * Método que consulta um cartão em uma API externa e, se houver, o vincula à proposta
     * Executa periodicamente conforme o fixedDelay ou fixedRate
     */
    @Scheduled(fixedDelay = TimeValues.MINUTO)
    public void vinculaCartoesGeradosAsPropostas() {
        log.info("Iniciada tarefa de vinculação de cartões");

        List<Proposta> propostas = propostaRepository.findAllByEstado(EstadoProposta.ELEGIVEL);
        propostas.forEach(proposta -> {
            try {
                CartaoResponse response = cartaoClient.consultaCartao(proposta.getId().toString());
                Cartao cartao = response.toModel(proposta);
                proposta.adicionaCartao(cartao);
                propostaRepository.save(proposta);
                log.info("Vinculação de cartão concluída - proposta " + proposta.getId());
            } catch (FeignException ex) {
                log.error("Erro na vinculação de cartão - proposta " + proposta.getId());
            }

        });

        log.info("Finalizada tarefa de vinculação de cartões");
    }
}
