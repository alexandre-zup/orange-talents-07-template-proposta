package br.com.zupacademy.propostas.model.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Aviso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Future
    private LocalDateTime validoAte;

    @CreationTimestamp
    private LocalDateTime avisadoEm;

    @NotNull
    @NotBlank
    private String enderecoIp;

    @NotNull
    @NotBlank
    private String userAgent;

    @NotNull
    @NotBlank
    private String destino;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    /**
     * @deprecated uso exclusivo de frameworks
     */
    @Deprecated
    public Aviso() {
    }

    public Aviso(LocalDateTime validoAte, String enderecoIp, String userAgent, String destino, Cartao cartao) {
        this.validoAte = validoAte;
        this.enderecoIp = enderecoIp;
        this.userAgent = userAgent;
        this.destino = destino;
        this.cartao = cartao;
    }
}
