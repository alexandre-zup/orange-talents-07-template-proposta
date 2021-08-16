package br.com.zupacademy.propostas.apiclients.avaliacaofinanceira;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AvaliacaoService {
    @Autowired
    private AvaliacaoFinanceiraClient client;

    /**
     *
     * @param request dados para a avaliação
     * @return resultado da avaliação
     * @throws ResponseStatusException caso receba uma resposta inesperada do serviço terceiro
     */
    public AvaliacaoFinanceiraResponse avalia(AvaliacaoFinanceiraRequest request) throws ResponseStatusException {
        AvaliacaoFinanceiraResponse response;

        try {
            return client.avalia(request);
        } catch (FeignException ex) {
            if (ex.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                return new AvaliacaoFinanceiraResponse(request.getDocumento(),
                        request.getNome(), request.getIdProposta(), ResultadoSolicitacao.COM_RESTRICAO);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro durante a avaliação da proposta");
            }
        }
    }
}
