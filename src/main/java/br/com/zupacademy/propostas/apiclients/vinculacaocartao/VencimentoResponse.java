package br.com.zupacademy.propostas.apiclients.vinculacaocartao;

import br.com.zupacademy.propostas.model.entities.Vencimento;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class VencimentoResponse {
    private String id;
    private Integer dia;
    private LocalDateTime dataDeCriacao;

    public VencimentoResponse(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Vencimento toModel() {
        return new Vencimento(id, dia, dataDeCriacao);
    }

    public String getId() {
        return id;
    }

    public Integer getDia() {
        return dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", VencimentoResponse.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("dia=" + dia)
                .add("dataDeCriacao=" + dataDeCriacao)
                .toString();
    }
}
