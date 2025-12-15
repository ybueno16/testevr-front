package com.example.vrteste.front.Pedido.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class VendaModel {
    private Long id;
    private String cliente;
    private List<Long> produtos; // IDs dos produtos
    private Integer quantidade;
    private Double valorTotal;
    private LocalDateTime dataVenda;

    public VendaModel(Long id, String cliente, List<Long> produtos, Integer quantidade, Double valorTotal, LocalDateTime dataVenda) {
        this.id = id;
        this.cliente = cliente;
        this.produtos = produtos;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.dataVenda = dataVenda;
    }

    public Long getId() { return id; }
    public String getCliente() { return cliente; }
    public List<Long> getProdutos() { return produtos; }
    public Integer getQuantidade() { return quantidade; }
    public Double getValorTotal() { return valorTotal; }
    public LocalDateTime getDataVenda() { return dataVenda; }

    public void setId(Long id) { this.id = id; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setProdutos(List<Long> produtos) { this.produtos = produtos; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public void setDataVenda(LocalDateTime dataVenda) { this.dataVenda = dataVenda; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaModel venda = (VendaModel) o;
        return Objects.equals(id, venda.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "VendaModel{" +
                "id=" + id +
                ", cliente='" + cliente + '\'' +
                ", produtos=" + produtos +
                ", quantidade=" + quantidade +
                ", valorTotal=" + valorTotal +
                ", dataVenda=" + dataVenda +
                '}';
    }
}
