package com.example.vrteste.front.Venda.View;

import com.example.vrteste.front.Venda.Controller.VendaApiController;
import com.example.vrteste.front.Venda.Model.VendaModel;
import com.example.vrteste.front.Venda.Model.ClienteDto;
import com.example.vrteste.front.Venda.Model.StatusVenda;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaVendas extends JFrame {
    private JTable tabelaVendas;
    private DefaultTableModel tableModel;
    private JButton btnEditar;
    private JButton btnVoltar;
    private JButton btnFinalizar;
    private VendaApiController vendaApiController;

    public ConsultaVendas() {
        setTitle("Consulta de Vendas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        vendaApiController = new VendaApiController();
        initComponents();
        carregarVendas();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Cliente", "Status", "Valor Total"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaVendas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaVendas);

        btnEditar = new JButton("Editar Venda");
        btnEditar.addActionListener(e -> editarVendaSelecionada());

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> this.dispose());

        btnFinalizar = new JButton("Finalizar Venda");
        btnFinalizar.addActionListener(e -> finalizarVendaSelecionada());

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnVoltar);
        panelBotoes.add(btnFinalizar);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelBotoes, BorderLayout.SOUTH);
}

private void finalizarVendaSelecionada() {
    int row = tabelaVendas.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Selecione uma venda para finalizar.");
        return;
    }
    Long vendaId = (Long) tableModel.getValueAt(row, 0);
    String status = (String) tableModel.getValueAt(row, 2);
    if (!StatusVenda.PENDENTE.getDescricao().equalsIgnoreCase(status) && !StatusVenda.PENDENTE.name().equalsIgnoreCase(status)) {
        JOptionPane.showMessageDialog(this, "Apenas vendas com status 'Pendente' podem ser finalizadas.");
        return;
    }
    int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente finalizar esta venda?", "Confirmar Finalização", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }
    try {
        java.util.List<VendaModel> vendas = VendaApiController.listarVendas();
        VendaModel vendaParaFinalizar = null;
        for (VendaModel v : vendas) {
            if (v.getId().equals(vendaId)) {
                vendaParaFinalizar = v;
                break;
            }
        }
        if (vendaParaFinalizar == null) {
            throw new Exception("Venda não encontrada para finalização.");
        }
        vendaParaFinalizar.setStatus(StatusVenda.SUCESSO.name());
        VendaApiController.updateVenda(vendaParaFinalizar);
        tableModel.setValueAt(StatusVenda.SUCESSO.getDescricao(), row, 2);
        JOptionPane.showMessageDialog(this, "Venda finalizada com sucesso.");
    } catch (Exception ex) {
        String msg = ex.getMessage();
        if (msg != null && msg.toLowerCase().contains("http error code")) {
            int idx = msg.indexOf(":");
            if (idx != -1 && idx + 1 < msg.length()) {
                msg = msg.substring(idx + 1).trim();
            }
        }
        if (msg != null && (msg.toLowerCase().contains("estoque") || msg.toLowerCase().contains("insuficiente"))) {
            JOptionPane.showMessageDialog(this, "Erro de estoque ao finalizar venda: " + msg.replaceAll("Failed.*", ""), "Erro de Estoque", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao finalizar venda: " + msg.replaceAll("Failed.*", ""));
        }
    }
}

    private void carregarVendas() {
        try {
            List<ClienteDto> clientes = VendaApiController.listarClientes();
            java.util.Map<Long, String> clienteIdNomeMap = new java.util.HashMap<>();
            for (ClienteDto cliente : clientes) {
                clienteIdNomeMap.put(cliente.getId(), cliente.getNome());
            }
            java.util.List<VendaModel> vendas = VendaApiController.listarVendas();
            tableModel.setRowCount(0);
            for (VendaModel venda : vendas) {
                String nomeCliente = clienteIdNomeMap.get(venda.getClienteId());
                if (nomeCliente == null) {
                    nomeCliente = "ID inválido: " + venda.getClienteId();
                }
        tableModel.addRow(new Object[]{
            venda.getId(),
            nomeCliente,
            venda.getStatus(),
            venda.getValor()
        });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar vendas: " + e.getMessage());
        }
    }

    private void editarVendaSelecionada() {
        int row = tabelaVendas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para editar.");
            return;
        }
        Long vendaId = (Long) tableModel.getValueAt(row, 0);
        
        List<VendaModel> vendas;
        try {
            vendas = VendaApiController.listarVendas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar vendas: " + ex.getMessage());
            return;
        }
        VendaModel vendaSelecionada = null;
        for (VendaModel v : vendas) {
            if (v.getId().equals(vendaId)) {
                vendaSelecionada = v;
                break;
            }
        }
        if (vendaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Venda não encontrada para edição.");
            return;
        }
        Integer quantidade = vendaSelecionada.getQuantidade();
        JTextField quantidadeField = new JTextField(quantidade != null ? quantidade.toString() : "");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Quantidade:"));
        panel.add(quantidadeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Quantidade da Venda (ID: " + vendaId + ")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int novaQuantidade = Integer.parseInt(quantidadeField.getText());
                vendaSelecionada.setQuantidade(novaQuantidade);
                VendaApiController.updateVenda(vendaSelecionada);
                carregarVendas();
                JOptionPane.showMessageDialog(this, "Quantidade da venda atualizada com sucesso na API.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar quantidade da venda: " + ex.getMessage());
            }
        }
    }
}
