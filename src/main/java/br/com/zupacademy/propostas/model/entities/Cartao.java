package br.com.zupacademy.propostas.model.entities;

import br.com.zupacademy.propostas.model.enums.EstadoCartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.zupacademy.propostas.model.enums.EstadoCartao.*;

@Entity
public class Cartao {
    @Id
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoCartao estado;

    @OneToOne(cascade = {CascadeType.ALL})
    private Vencimento vencimento;

    @OneToOne
    @NotNull
    @JoinColumn(name = "idProposta")
    private Proposta proposta;

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Biometria> biometrias = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Bloqueio> bloqueios = new ArrayList<>();

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
        this.estado = ATIVO;
    }

    public void bloqueia(@NotBlank String enderecoIp, @NotBlank String userAgent) {
        Assert.hasText(enderecoIp, "Endereço IP não pode ser em branco");
        Assert.hasText(userAgent, "User-Agent não pode ser em branco");

        Bloqueio bloqueio = new Bloqueio(enderecoIp, userAgent, this);
        this.bloqueios.add(bloqueio);
        this.estado = BLOQUEIO_PENDENTE;
    }

    public void bloqueioConfirmadoNoLegado() {
        this.estado = BLOQUEADO;
    }

    public boolean estaBloqueado() {
        return estado.equals(BLOQUEADO) || estado.equals(BLOQUEIO_PENDENTE);
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

    public List<Bloqueio> getBloqueios() {
        return bloqueios;
    }
}
