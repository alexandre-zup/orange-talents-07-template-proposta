package br.com.zupacademy.propostas.apiclients.cartoes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueioResponse {
    private String resultado;

    public BloqueioResponse(@JsonProperty("resultado") String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
