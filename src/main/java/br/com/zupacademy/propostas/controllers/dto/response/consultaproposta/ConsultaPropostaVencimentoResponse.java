package br.com.zupacademy.propostas.controllers.dto.response.consultaproposta;

import br.com.zupacademy.propostas.model.entities.Vencimento;

import java.time.LocalDateTime;

public class ConsultaPropostaVencimentoResponse {
    private LocalDateTime dataDeCriacao;
    private Integer dia;

    public ConsultaPropostaVencimentoResponse(Vencimento vencimento) {
        if (vencimento != null) {
            this.dia = vencimento.getDia();
            this.dataDeCriacao = vencimento.getDataDeCriacao();
        }
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public Integer getDia() {
        return dia;
    }
}
