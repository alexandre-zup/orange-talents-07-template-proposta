package br.com.zupacademy.propostas.apiclients.avaliacaofinanceira;

import br.com.zupacademy.propostas.config.TimeValues;
import br.com.zupacademy.propostas.model.entities.Proposta;
import br.com.zupacademy.propostas.model.enums.EstadoProposta;
import br.com.zupacademy.propostas.model.repositories.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AvaliacaoTask {
    Logger log = LoggerFactory.getLogger(AvaliacaoTask.class);
    @Autowired
    private PropostaRepository repository;
    @Autowired
    private AvaliacaoService service;


    @Scheduled(fixedDelay = TimeValues.MINUTO)
    protected void avaliaPropostasNaoAvaliadasNaCriacao() {
        log.debug("Task iniciada");
        List<Proposta> propostas = repository.findAllByEstado(EstadoProposta.EM_ANALISE);

        propostas.forEach(proposta -> {
            Optional<ResultadoSolicitacao> optionalResultado = service.avalia(proposta);

            optionalResultado.ifPresent(resultado -> {
                proposta.atualizaDeAcordoComResultado(resultado);
                repository.save(proposta);
                log.info("Avaliação concluída - proposta " + proposta.getId());
            });

            if(optionalResultado.isEmpty())
                log.error("Erro na avaliação - proposta " + proposta.getId());
        });

        log.debug("Task finalizada");
    }
}
