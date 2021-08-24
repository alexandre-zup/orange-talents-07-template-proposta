package br.com.zupacademy.propostas.apiclients.cartoes;

import br.com.zupacademy.propostas.model.entities.Cartao;
import br.com.zupacademy.propostas.model.entities.Proposta;
import br.com.zupacademy.propostas.model.entities.Vencimento;
import br.com.zupacademy.propostas.model.repositories.CartaoRepository;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class CartaoResponse {
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;
    private VencimentoResponse vencimento;
    private String idProposta;

    public CartaoResponse(String id, LocalDateTime emitidoEm, String titular, Integer limite, VencimentoResponse vencimento, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public Cartao toModel(Proposta proposta, CartaoRepository cartaoRepository) {
        Cartao cartao = new Cartao(id, emitidoEm, titular, limite, proposta);
        Vencimento vencimento = this.vencimento.toModel(cartao);
        cartao.atualizaVencimento(vencimento);
        cartaoRepository.save(cartao);
        return cartao;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public Integer getLimite() {
        return limite;
    }

    public VencimentoResponse getVencimento() {
        return vencimento;
    }

    public String getIdProposta() {
        return idProposta;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CartaoResponse.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("emitidoEm=" + emitidoEm)
                .add("titular='" + titular + "'")
                .add("limite=" + limite)
                .add("vencimento=" + vencimento)
                .add("idProposta='" + idProposta + "'")
                .toString();
    }
}
