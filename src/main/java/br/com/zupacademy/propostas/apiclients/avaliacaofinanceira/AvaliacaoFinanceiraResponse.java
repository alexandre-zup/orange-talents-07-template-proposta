package br.com.zupacademy.propostas.apiclients.avaliacaofinanceira;

import java.util.StringJoiner;

public class AvaliacaoFinanceiraResponse {
    private String documento;
    private String nome;
    private String idProposta;
    private ResultadoSolicitacao resultadoSolicitacao;

    public AvaliacaoFinanceiraResponse(String documento, String nome, String idProposta, ResultadoSolicitacao resultadoSolicitacao) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public ResultadoSolicitacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AvaliacaoFinanceiraResponse.class.getSimpleName() + "[", "]")
                .add("documento='" + documento + "'")
                .add("nome='" + nome + "'")
                .add("idProposta='" + idProposta + "'")
                .add("resultadoSolicitacao=" + resultadoSolicitacao)
                .toString();
    }
}
