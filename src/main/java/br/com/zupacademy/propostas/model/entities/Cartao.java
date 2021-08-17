package br.com.zupacademy.propostas.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Cartao {
    @Id
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Vencimento vencimento;

    @OneToOne
    @NotNull
    @JoinColumn(name = "idProposta")
    private Proposta proposta;

    /**
     * @deprecated uso exclusivo dos frameworks
     */
    @Deprecated
    public Cartao() {
    }

    public Cartao(String id, LocalDateTime emitidoEm, String titular, Integer limite, Vencimento vencimento, Proposta proposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
        this.proposta = proposta;
    }
}
