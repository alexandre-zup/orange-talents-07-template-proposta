package br.com.zupacademy.propostas.model.entities;

import br.com.zupacademy.propostas.model.enums.ServicoCarteira;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Carteira {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ServicoCarteira nome;

    @NotNull
    @NotBlank
    @Email
    private String email;


    @NotNull
    @ManyToOne
    private Cartao cartao;

    /**
     * @deprecated exclusivo para frameworks
     */
    @Deprecated
    public Carteira() {
    }

    public Carteira(ServicoCarteira nome, String email, Cartao cartao) {
        this.nome = nome;
        this.email = email;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carteira carteira = (Carteira) o;
        return nome == carteira.nome && cartao.equals(carteira.cartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cartao);
    }
}
