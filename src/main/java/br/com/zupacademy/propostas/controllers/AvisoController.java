package br.com.zupacademy.propostas.controllers;

import br.com.zupacademy.propostas.apiclients.cartoes.AvisoRequest;
import br.com.zupacademy.propostas.apiclients.cartoes.CartaoClient;
import br.com.zupacademy.propostas.controllers.dto.request.NovoAvisoRequest;
import br.com.zupacademy.propostas.model.entities.Aviso;
import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.repositories.CartaoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AvisoController {
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private CartaoClient cartaoClient;

    @PostMapping("/api/cartoes/{id}/avisos")
    @Transactional
    public ResponseEntity<Void> cria(@PathVariable String id,
                                  @RequestBody @Valid NovoAvisoRequest requestDto,
                                  @RequestHeader("User-Agent") String userAgent,
                                  @RequestHeader(value = "X-Forward-For") String xForwardFor) {
        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);

        if (optionalCartao.isEmpty())
            return ResponseEntity.notFound().build();

        String ip = xForwardFor.split(",")[0];
        Cartao cartao = optionalCartao.get();
        Aviso aviso = requestDto.toModel(cartao, ip, userAgent);
        cartao.adicionaAviso(aviso);
        cartaoRepository.save(cartao);

        try {
            AvisoRequest request = new AvisoRequest(requestDto.getDestino(), requestDto.getValidoAte());
            cartaoClient.enviaAviso(id, request);
        } catch (FeignException.UnprocessableEntity ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (FeignException.BadRequest ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (FeignException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }

        return ResponseEntity.ok().build();
    }
}
