package br.com.zupacademy.propostas.apiclients.avaliacaofinanceira;

import br.com.zupacademy.propostas.model.entities.Proposta;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AvaliacaoService {
    @Autowired
    private AvaliacaoClient client;

    public Optional<ResultadoSolicitacao> avalia(Proposta proposta) {
        AvaliacaoRequest request = new AvaliacaoRequest(proposta.getDocumento(), proposta.getNome(),
                proposta.getId().toString());

        try {
            AvaliacaoResponse response = client.avalia(request);
            return Optional.of(response.getResultadoSolicitacao());
        } catch (FeignException.UnprocessableEntity ex) {
            return Optional.of(ResultadoSolicitacao.COM_RESTRICAO);
        } catch (FeignException ex) {
            return Optional.empty();
        }
    }
}
