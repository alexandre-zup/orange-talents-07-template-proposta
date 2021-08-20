package br.com.zupacademy.propostas.model.enums;

public enum EstadoCartao {
    /*
    ATIVO - cartão sem bloqueio
    BLOQUEIO_PENDENTE - cartão bloqueado no sistema local, mas o sistema legado ainda não notificado
    BLOQUEADO - cartão bloqueado também no sistema legado
     */

    ATIVO, BLOQUEIO_PENDENTE, BLOQUEADO
}
