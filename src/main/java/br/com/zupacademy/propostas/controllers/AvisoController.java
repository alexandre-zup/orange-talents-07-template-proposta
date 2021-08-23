package br.com.zupacademy.propostas.controllers;

import br.com.zupacademy.propostas.controllers.dto.request.NovoAvisoRequest;
import br.com.zupacademy.propostas.model.entities.Aviso;
import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.repositories.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AvisoController {
    @Autowired
    private CartaoRepository cartaoRepository;

    @PostMapping("/api/cartoes/{id}/avisos")
    @Transactional
    public ResponseEntity<?> cria(@PathVariable String id,
                                  @RequestBody @Valid NovoAvisoRequest requestDto,
                                  @RequestHeader("User-Agent") String userAgent,
                                  @RequestHeader(value = "X-Forward-For") String xForwardFor,
                                  HttpServletRequest httpRequest) {
        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);

        if (optionalCartao.isEmpty())
            return ResponseEntity.notFound().build();

        String ip = xForwardFor.split(",")[0];
        Cartao cartao = optionalCartao.get();
        Aviso aviso = requestDto.toModel(cartao, ip, userAgent);
        cartao.adicionaAviso(aviso);
        cartaoRepository.save(cartao);
        return ResponseEntity.ok().build();
    }
}
