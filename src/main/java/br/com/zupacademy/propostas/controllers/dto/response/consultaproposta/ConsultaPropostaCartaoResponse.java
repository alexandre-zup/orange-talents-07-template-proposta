package br.com.zupacademy.propostas.controllers.dto.response.consultaproposta;

import br.com.zupacademy.propostas.model.entities.Cartao;

import java.time.LocalDateTime;

public class ConsultaPropostaCartaoResponse {
    private LocalDateTime emitidoEm;
    private Integer limite;
    private String id;
    private ConsultaPropostaVencimentoResponse vencimento;

    public ConsultaPropostaCartaoResponse(Cartao cartao) {
        if(cartao != null) {
            this.emitidoEm = cartao.getEmitidoEm();
            this.limite = cartao.getLimite();
            this.id = cartao.getId();
            this.vencimento = new ConsultaPropostaVencimentoResponse(cartao.getVencimento());
        }
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public Integer getLimite() {
        return limite;
    }

    public String getId() {
        return id;
    }

    public ConsultaPropostaVencimentoResponse getVencimento() {
        return vencimento;
    }
}
