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
import static javax.persistence.CascadeType.*;

@Entity
public class Cartao {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String numero;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoCartao estado;

    @OneToOne(mappedBy = "cartao", cascade = {MERGE, PERSIST, REMOVE})
    private Vencimento vencimento;

    @OneToOne
    @NotNull
    @JoinColumn(name = "idProposta")
    private Proposta proposta;

    @OneToMany(mappedBy = "cartao", cascade = {MERGE, PERSIST, REMOVE})
    private List<Biometria> biometrias = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", cascade = {MERGE, PERSIST, REMOVE})
    private List<Bloqueio> bloqueios = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", cascade = {MERGE, PERSIST, REMOVE})
    private List<Aviso> avisos = new ArrayList<>();

    /**
     * @deprecated uso exclusivo dos frameworks
     */
    @Deprecated
    public Cartao() {
    }

    public Cartao(String numero, LocalDateTime emitidoEm, String titular, Integer limite, Proposta proposta) {
        this.numero = numero;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
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

    public void adicionaBiometria(@NotNull Biometria biometria) {
        Assert.notNull(biometria, "Biometria nao pode ser nula");
        this.biometrias.add(biometria);
    }

    public void adicionaAviso(@NotNull Aviso aviso) {
        Assert.notNull(aviso, "Aviso nao pode ser nulo");
        this.avisos.add(aviso);
    }

    public void atualizaVencimento(Vencimento vencimento) {
        this.vencimento = vencimento;
    }

    public boolean estaBloqueado() {
        return estado.equals(BLOQUEADO) || estado.equals(BLOQUEIO_PENDENTE);
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
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
