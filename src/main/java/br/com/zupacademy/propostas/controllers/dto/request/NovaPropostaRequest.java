package br.com.zupacademy.propostas.controllers.dto.request;

import br.com.zupacademy.propostas.controllers.exception.exceptions.UnprocessableEntityException;
import br.com.zupacademy.propostas.controllers.validation.BrazilianDocument;
import br.com.zupacademy.propostas.model.entities.Proposta;
import br.com.zupacademy.propostas.model.repositories.PropostaRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {
    @NotBlank
    @BrazilianDocument
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    public NovaPropostaRequest(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    /**
     *
     * @param repository repositório de propostas
     * @return instância de Proposta, se os dados forem válidos
     * @throws UnprocessableEntityException se os dados forem inválidos
     */
    public Proposta toModel(PropostaRepository repository) throws UnprocessableEntityException {

        if(repository.existsByDocumento(documento))
            throw new UnprocessableEntityException("Não é permitido mais de uma proposta por documento");

        return new Proposta(documento, email, nome, endereco, salario);
    }
}
