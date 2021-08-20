package br.com.zupacademy.propostas.model.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime bloqueadoEm;

    @NotNull
    @Column(nullable = false)
    private boolean ativo;

    @NotBlank
    @Column(nullable = false)
    private String enderecoIp;

    @NotBlank
    @Column(nullable = false)
    private String userAgent;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    /**
     * @deprecated exclusivo para frameworks
     */
    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(@NotBlank String enderecoIp, @NotBlank String userAgent, @NotNull Cartao cartao) {
        this.enderecoIp = enderecoIp;
        this.userAgent = userAgent;
        this.cartao = cartao;
        this.ativo = true;
    }

    public boolean estaAtivo() {
        return ativo;
    }
}
