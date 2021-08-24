package br.com.zupacademy.propostas.controllers.dto.request;

import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.entities.Carteira;
import br.com.zupacademy.propostas.model.enums.ServicoCarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaCarteiraRequest {

    @Email
    @NotBlank
    private String email;

    @NotNull
    private ServicoCarteira carteira;

    public NovaCarteiraRequest(String email, ServicoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public Carteira toModel(Cartao cartao) {
        return new Carteira(carteira, email, cartao);
    }

    public ServicoCarteira getCarteira() {
        return carteira;
    }

    public String getEmail() {
        return email;
    }
}
