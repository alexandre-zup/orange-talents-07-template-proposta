package br.com.zupacademy.propostas.controllers;

import br.com.zupacademy.propostas.controllers.exception.ValidationErrorsOutputDto;
import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.repositories.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class BloqueioController {
    @Autowired
    private CartaoRepository cartaoRepository;

    @Transactional
    @PostMapping("/api/cartoes/{id}/bloqueios")
    public ResponseEntity<?> cria(@PathVariable String id,
                                  @RequestHeader("User-Agent") String userAgent,
                                  @RequestHeader("X-Forward-For") String xForwardFor) {
        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);

        if (optionalCartao.isEmpty())
            return ResponseEntity.notFound().build();

        Cartao cartao = optionalCartao.get();

        if (cartao.estaBloqueado()) {
            ValidationErrorsOutputDto erro = new ValidationErrorsOutputDto("Cartão já está bloqueado");
            return ResponseEntity.unprocessableEntity().body(erro);
        }

        String enderecoIp = xForwardFor.split(",")[0];
        cartao.bloqueia(enderecoIp, userAgent);
        return ResponseEntity.ok().build();
    }
}
