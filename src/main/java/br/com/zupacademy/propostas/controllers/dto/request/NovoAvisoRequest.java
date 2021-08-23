package br.com.zupacademy.propostas.controllers.dto.request;

import br.com.zupacademy.propostas.model.entities.Aviso;
import br.com.zupacademy.propostas.model.entities.Cartao;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class NovoAvisoRequest {
    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDateTime validoAte;

    public NovoAvisoRequest(String destino, LocalDateTime validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public Aviso toModel(Cartao cartao, String enderecoIp, String userAgent) {
        return new Aviso(validoAte, enderecoIp, userAgent, destino, cartao);
    }
}
