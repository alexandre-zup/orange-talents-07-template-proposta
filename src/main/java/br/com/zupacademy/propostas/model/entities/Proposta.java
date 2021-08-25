package br.com.zupacademy.propostas.model.entities;

import br.com.zupacademy.propostas.apiclients.avaliacaofinanceira.ResultadoSolicitacao;
import br.com.zupacademy.propostas.model.enums.EstadoProposta;
import br.com.zupacademy.propostas.utils.Criptografia;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.StringJoiner;

import static javax.persistence.CascadeType.*;

@Entity
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String documento;

    @NotBlank
    @Column(nullable = false)
    private String documentoHash;

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

    @OneToOne(mappedBy = "proposta", cascade = {PERSIST, MERGE, REMOVE})
    private Cartao cartao;

    /**
     * @deprecated para uso de frameworks
     */
    @Deprecated
    public Proposta() {
    }

    /**
     *
     * @param documentoTextoLimpo documento do solicitante em texto limpo
     * @param email email do solicitante em formato válido
     * @param nome nome do solicitante
     * @param endereco endereço do solicitante
     * @param salario salario do solicitante
     */
    public Proposta(String documentoTextoLimpo, String email, String nome, String endereco, BigDecimal salario) {
        Criptografia criptografia = Criptografia.getInstance();
        this.documento = criptografia.cifrar(documentoTextoLimpo);
        this.documentoHash = criptografia.gerarHash(documentoTextoLimpo);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.estado = EstadoProposta.EM_ANALISE;
    }

    /**
     * Atualiza o estado de acordo com o {@code resultado} fornecido
     * @param resultado resultado da avaliação financeira
     */
    public void atualizaDeAcordoComResultado(ResultadoSolicitacao resultado) {
        this.estado = resultado.getElegibilidade();
    }

    public void adicionaCartao(Cartao cartao) {
        this.cartao = cartao;
        this.estado = EstadoProposta.CARTAO_GERADO;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    /**
     * O documento é armazenado cifrado, mas o getter já retorna em texto limpo
     * @return documento do cliente decifrado
     */
    public String getDocumento() {
        Criptografia criptografia = Criptografia.getInstance();
        return criptografia.decifrar(documento);
    }

    public String getEmail() {
        return email;
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

    public Cartao getCartao() {
        return cartao;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Proposta.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("documento='" + documento + "'")
                .add("nome='" + nome + "'")
                .add("estado=" + estado)
                .add("cartao=" + cartao)
                .toString();
    }
}
