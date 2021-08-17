package br.com.zupacademy.propostas.controllers.dto.response.consultaproposta;

import br.com.zupacademy.propostas.model.entities.Proposta;
import br.com.zupacademy.propostas.model.enums.EstadoProposta;

import java.math.BigDecimal;

public class ConsultaPropostaResponse {
    private Long id;
    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;
    private EstadoProposta estado;
    private ConsultaPropostaCartaoResponse cartao;

    public ConsultaPropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.estado = proposta.getEstado();

        if (proposta.getCartao() != null) {
            this.cartao = new ConsultaPropostaCartaoResponse(proposta.getCartao());
        }
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EstadoProposta getEstado() {
        return estado;
    }

    public ConsultaPropostaCartaoResponse getCartao() {
        return cartao;
    }
}
