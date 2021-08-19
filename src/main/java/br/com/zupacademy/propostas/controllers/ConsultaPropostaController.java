package br.com.zupacademy.propostas.controllers;

import br.com.zupacademy.propostas.controllers.dto.response.consultaproposta.ConsultaPropostaResponse;
import br.com.zupacademy.propostas.model.entities.Proposta;
import br.com.zupacademy.propostas.model.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/propostas")
public class ConsultaPropostaController {
    @Autowired
    private PropostaRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaPropostaResponse> consulta(@PathVariable Long id) {
        Optional<Proposta> optionalProposta = repository.findById(id);

        if(optionalProposta.isEmpty())
            return ResponseEntity.notFound().build();

        ConsultaPropostaResponse response = new ConsultaPropostaResponse(optionalProposta.get());
        return ResponseEntity.ok().body(response);
    }
}
