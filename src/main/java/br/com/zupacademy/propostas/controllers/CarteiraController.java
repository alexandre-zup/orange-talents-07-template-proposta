package br.com.zupacademy.propostas.controllers;

import br.com.zupacademy.propostas.apiclients.cartoes.CartaoClient;
import br.com.zupacademy.propostas.controllers.dto.request.NovaCarteiraRequest;
import br.com.zupacademy.propostas.controllers.exception.ValidationErrorsOutputDto;
import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.entities.Carteira;
import br.com.zupacademy.propostas.model.repositories.CartaoRepository;
import br.com.zupacademy.propostas.model.repositories.CarteiraRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class CarteiraController {
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private CarteiraRepository carteiraRepository;
    @Autowired
    private CartaoClient cartaoClient;

    @PostMapping("/api/cartoes/{id}/carteiras")
    @Transactional
    public ResponseEntity<?> cadastra(@PathVariable Long id, @RequestBody @Valid NovaCarteiraRequest request) {
        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);

        if(optionalCartao.isEmpty()) return ResponseEntity.notFound().build();

        if(carteiraRepository.existsByNomeAndCartaoId(request.getCarteira(), id)) {
            ValidationErrorsOutputDto erro = new ValidationErrorsOutputDto("Cartão já associado a essa carteira");
            return ResponseEntity.unprocessableEntity().body(erro);
        }

        Cartao cartao = optionalCartao.get();
        Carteira carteira = request.toModel(cartao);
        carteiraRepository.save(carteira);

        try {
            cartaoClient.associaCarteira(cartao.getNumero(), request);
        } catch (FeignException.UnprocessableEntity ex) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (FeignException.BadRequest ex) {
            return ResponseEntity.badRequest().build();
        } catch (FeignException ex) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(carteira.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
