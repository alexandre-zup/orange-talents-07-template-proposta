package br.com.zupacademy.propostas.controllers.dto.response.consultaproposta;

import br.com.zupacademy.propostas.model.entities.Cartao;

import java.time.LocalDateTime;

public class ConsultaPropostaCartaoResponse {
    private LocalDateTime emitidoEm;
    private Integer limite;
    private String numero;
    private ConsultaPropostaVencimentoResponse vencimento;

    public ConsultaPropostaCartaoResponse(Cartao cartao) {
        if(cartao != null) {
            this.emitidoEm = cartao.getEmitidoEm();
            this.limite = cartao.getLimite();
            this.numero = cartao.getNumero();
            this.vencimento = new ConsultaPropostaVencimentoResponse(cartao.getVencimento());
        }
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public Integer getLimite() {
        return limite;
    }

    public String getNumero() {
        return numero;
    }

    public ConsultaPropostaVencimentoResponse getVencimento() {
        return vencimento;
    }
}
