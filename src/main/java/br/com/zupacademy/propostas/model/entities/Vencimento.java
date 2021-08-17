package br.com.zupacademy.propostas.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Vencimento {
    @Id
    private String id;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer dia;

    @NotNull
    private LocalDateTime dataDeCriacao;

    /**
     * @deprecated uso exclusivo de frameworks
     */
    @Deprecated
    public Vencimento() {
    }

    public Vencimento(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Integer getDia() {
        return dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }
}
