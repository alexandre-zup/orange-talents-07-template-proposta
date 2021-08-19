package br.com.zupacademy.propostas.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cartao {
    @Id
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;

    @OneToOne(cascade = {CascadeType.ALL})
    private Vencimento vencimento;

    @OneToOne
    @NotNull
    @JoinColumn(name = "idProposta")
    private Proposta proposta;

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.ALL})
    private List<Biometria> biometrias = new ArrayList<>();

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

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public Integer getLimite() {
        return limite;
    }

    public Vencimento getVencimento() {
        return vencimento;
    }
}
