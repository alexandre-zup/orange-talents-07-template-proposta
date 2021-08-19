package br.com.zupacademy.propostas.model.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Biometria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String fingerPrint;

    @CreationTimestamp
    private LocalDateTime criadoEm;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cartao cartao;

    /**
     * @deprecated uso exclusivo de frameworks
     */
    @Deprecated
    public Biometria() {
    }

    public Biometria(String fingerPrint, Cartao cartao) {
        this.fingerPrint = fingerPrint;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometria biometria = (Biometria) o;
        return fingerPrint.equals(biometria.fingerPrint) && cartao.equals(biometria.cartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fingerPrint, cartao);
    }
}
