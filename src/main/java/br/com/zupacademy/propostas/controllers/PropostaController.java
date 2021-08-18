package br.com.zupacademy.propostas.controllers;

import br.com.zupacademy.propostas.apiclients.avaliacaofinanceira.*;
import br.com.zupacademy.propostas.controllers.dto.request.NovaPropostaRequest;
import br.com.zupacademy.propostas.controllers.exception.ValidationErrorsOutputDto;
import br.com.zupacademy.propostas.controllers.exception.exceptions.UnprocessableEntityException;
import br.com.zupacademy.propostas.model.entities.Proposta;
import br.com.zupacademy.propostas.model.repositories.PropostaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository repository;
    @Autowired
    private AvaliacaoClient client;

    @PostMapping
    @Transactional
    public ResponseEntity<?> cria(@RequestBody @Valid NovaPropostaRequest request) {
        Proposta proposta = null;

        try {
            proposta = request.toModel(repository);
        } catch (UnprocessableEntityException e) {
            ValidationErrorsOutputDto erro = new ValidationErrorsOutputDto(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(erro);
        }

        repository.save(proposta);
        ResultadoSolicitacao resultado = avalia(proposta);
        proposta.atualizaEstado(resultado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    private ResultadoSolicitacao avalia(Proposta proposta) throws ResponseStatusException {
        AvaliacaoRequest request = new AvaliacaoRequest(proposta.getDocumento(),
                proposta.getNome(), proposta.getId().toString());

        AvaliacaoResponse response;

        try {
            return client.avalia(request).getResultadoSolicitacao();
        } catch (FeignException ex) {
            if (ex.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                return ResultadoSolicitacao.COM_RESTRICAO;
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro durante a avaliação da proposta");
            }
        }
    }
}
