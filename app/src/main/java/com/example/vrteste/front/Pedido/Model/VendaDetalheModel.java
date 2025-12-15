package com.example.vrteste.front.Pedido.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VendaDetalheModel {
    private Long id;
    private String clienteNome;
    private String status;
    private LocalDateTime data;
    private BigDecimal valorTotal;

    public VendaDetalheModel(Long id, String clienteNome, String status, LocalDateTime data, BigDecimal valorTotal) {
        this.id = id;
        this.clienteNome = clienteNome;
        this.status = status;
        this.data = data;
        this.valorTotal = valorTotal;
    }

    public Long getId() { return id; }
    public String getClienteNome() { return clienteNome; }
    public String getStatus() { return status; }
    public LocalDateTime getData() { return data; }
    public BigDecimal getValorTotal() { return valorTotal; }
}
