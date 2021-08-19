package br.com.zupacademy.propostas.controllers;

import br.com.zupacademy.propostas.controllers.dto.request.NovaBiometriaRequest;
import br.com.zupacademy.propostas.model.entities.Biometria;
import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.repositories.BiometriaRepository;
import br.com.zupacademy.propostas.model.repositories.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class NovaBiometriaController {
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private BiometriaRepository biometriaRepository;

    @PostMapping("/api/cartoes/{cartaoId}/biometrias")
    @Transactional
    public ResponseEntity<Void> cadastra(@PathVariable String cartaoId,
                                         @RequestBody @Valid NovaBiometriaRequest request) {
        Optional<Cartao> optionalCartao = cartaoRepository.findById(cartaoId);

        if(optionalCartao.isEmpty())
            return ResponseEntity.notFound().build();

        Cartao cartao = optionalCartao.get();

        Biometria biometria = request.toModel(cartao);
        biometriaRepository.save(biometria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
