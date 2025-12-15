package com.example.vrteste.front.Pedido.Model;

public class BaixaEstoqueRequest {
    private Long id;
    private Integer quantidade;

    public BaixaEstoqueRequest(Long id, Integer quantidade) {
        this.id = id;
        this.quantidade = quantidade;
    }

    public Long getId() { return id; }
    public Integer getQuantidade() { return quantidade; }
    public void setId(Long id) { this.id = id; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}
