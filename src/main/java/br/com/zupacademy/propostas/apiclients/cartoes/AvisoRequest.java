package br.com.zupacademy.propostas.apiclients.cartoes;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AvisoRequest {
    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDateTime validoAte;

    public AvisoRequest(String destino, LocalDateTime validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDateTime getValidoAte() {
        return validoAte;
    }
}
