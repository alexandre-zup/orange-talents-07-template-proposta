package br.com.zupacademy.propostas.model.entities;

import br.com.zupacademy.propostas.apiclients.avaliacaofinanceira.ResultadoSolicitacao;
import br.com.zupacademy.propostas.controllers.validation.BrazilianDocument;
import br.com.zupacademy.propostas.model.enums.EstadoProposta;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @BrazilianDocument
    @NotBlank
    @Column(nullable = false)
    private String documento;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String endereco;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private EstadoProposta estado;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    /**
     * Atualiza o estado de acordo com o {@code resultado} fornecido
     * @param resultado resultado da avaliação financeira
     */
    public void atualizaEstado(ResultadoSolicitacao resultado) {
        this.estado = resultado.getElegibilidade();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }
}
