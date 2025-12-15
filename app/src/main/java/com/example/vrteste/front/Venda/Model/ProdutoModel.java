package com.example.vrteste.front.Venda.Model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class ProdutoModel {
    private Long id;
    private String descricao;
    private Integer estoque;
    private Double preco;
    private String unidade;
    private ZonedDateTime ultimaAtualizacao;

    public ProdutoModel(Long id, String descricao, Integer estoque, Double preco, String unidade, ZonedDateTime ultimaAtualizacao) {
        this.id = id;
        this.descricao = descricao;
        this.estoque = estoque;
        this.preco = preco;
        this.unidade = unidade;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Long getId() { return id; }
    public String getDescricao() { return descricao; }
    public Integer getEstoque() { return estoque; }
    public Double getPreco() { return preco; }
    public String getUnidade() { return unidade; }
    public ZonedDateTime getUltimaAtualizacao() { return ultimaAtualizacao; }

    public void setId(Long id) { this.id = id; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
    public void setPreco(Double preco) { this.preco = preco; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    public void setUltimaAtualizacao(ZonedDateTime ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoModel produto = (ProdutoModel) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "ProdutoModel{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", estoque=" + estoque +
                ", preco=" + preco +
                ", unidade='" + unidade + '\'' +
                ", ultimaAtualizacao=" + ultimaAtualizacao +
                '}';
    }
}
