package br.com.zupacademy.propostas.apiclients.avaliacaofinanceira;

import br.com.zupacademy.propostas.model.enums.EstadoProposta;

public enum ResultadoSolicitacao {
    SEM_RESTRICAO {
        @Override
        public EstadoProposta getElegibilidade() {
            return EstadoProposta.ELEGIVEL;
        }
    }, COM_RESTRICAO {
        @Override
        public EstadoProposta getElegibilidade() {
            return EstadoProposta.NAO_ELEGIVEL;
        }
    };

    public abstract EstadoProposta getElegibilidade();
}
