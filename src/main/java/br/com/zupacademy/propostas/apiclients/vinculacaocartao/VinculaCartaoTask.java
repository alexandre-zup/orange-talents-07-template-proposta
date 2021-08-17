package br.com.zupacademy.propostas.apiclients.vinculacaocartao;

import br.com.zupacademy.propostas.config.TimeValues;
import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.entities.Proposta;
import br.com.zupacademy.propostas.model.enums.EstadoProposta;
import br.com.zupacademy.propostas.model.repositories.CartaoRepository;
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
    private CartaoRepository cartaoRepository;
    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private CartaoClient cartaoClient;

    @Scheduled(fixedRate = 10 * TimeValues.SEGUNDO)
    public void vinculaCartoesGeradosAsPropostas() {
        log.info("Iniciada tarefa de vinculação de cartões");

        List<Proposta> propostas = propostaRepository.findAllByEstadoAndCartao(EstadoProposta.ELEGIVEL, null);
        propostas.forEach(proposta -> {
            try {
                CartaoResponse response = cartaoClient.consultaCartao(proposta.getId().toString());
                Cartao cartao = response.toModel(proposta);
                cartaoRepository.save(cartao);
                log.info("Vinculação de cartão concluída - proposta " + proposta.getId());
            } catch (FeignException ex) {
                log.error("Erro na vinculação de cartão - proposta " + proposta.getId());
            }

        });

        log.info("Finalizada tarefa de vinculação de cartões");
    }
}
