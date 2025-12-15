package com.example.vrteste.front.Venda.Model;

public enum StatusVenda {
    PENDENTE("Pendente"),
    CONCLUIDA("Concluída"),
    SUCESSO("Sucesso"),
    ERRO("Erro"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusVenda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
