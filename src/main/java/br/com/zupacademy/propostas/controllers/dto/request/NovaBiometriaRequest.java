package br.com.zupacademy.propostas.controllers.dto.request;

import br.com.zupacademy.propostas.controllers.validation.Base64;
import br.com.zupacademy.propostas.model.entities.Biometria;
import br.com.zupacademy.propostas.model.entities.Cartao;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.StringJoiner;

public class NovaBiometriaRequest {
    @NotBlank
    @Base64
    private String fingerPrint;

    public NovaBiometriaRequest(@JsonProperty(value = "fingerPrint") String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(fingerPrint, cartao);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NovaBiometriaRequest.class.getSimpleName() + "[", "]")
                .add("fingerPrint='" + fingerPrint + "'")
                .toString();
    }
}
