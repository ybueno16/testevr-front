package com.example.vrteste.front.Pedido.View;

import com.example.vrteste.front.Venda.Controller.VendaApiController;
import com.example.vrteste.front.Venda.Model.StatusVenda;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultaVendas extends JFrame {
    private JTable tabelaVendas;
    private DefaultTableModel tableModel;
    private JButton btnEditar;
    private JButton btnFinalizarParcial;
    private JButton btnAtualizar;
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
                new String[]{"ID", "Cliente", "Status", "Data", "Valor Total"}
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

        btnFinalizarParcial = new JButton("Finalizar Parcialmente");
        btnFinalizarParcial.addActionListener(e -> finalizarParcialVendaSelecionada());

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> carregarVendas());

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnFinalizarParcial);
        panelBotoes.add(btnAtualizar);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelBotoes, BorderLayout.SOUTH);
    }

    private void carregarVendas() {
        try {
            java.util.List<com.example.vrteste.front.Venda.Model.VendaModel> vendas = com.example.vrteste.front.Venda.Controller.VendaApiController.listarVendas();
            tableModel.setRowCount(0);
            for (com.example.vrteste.front.Venda.Model.VendaModel venda : vendas) {
                tableModel.addRow(new Object[]{
                        venda.getId(),
                        venda.getClienteId(),
                        "-", // Status
                        "-", // Data (não disponível)
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
        String statusAtualStr = (String) tableModel.getValueAt(row, 2);
        StatusVenda statusAtual = StatusVenda.PENDENTE;
        for (StatusVenda s : StatusVenda.values()) {
            if (s.getDescricao().equals(statusAtualStr)) {
                statusAtual = s;
                break;
            }
        }
        EditarStatusVendaDialog dialog = new EditarStatusVendaDialog(this, statusAtual);
        dialog.setVisible(true);
        StatusVenda novoStatus = dialog.getStatusSelecionado();
        if (novoStatus != null && novoStatus != statusAtual) {
            // Aqui você deve chamar a API/controller para atualizar o status no backend
            // Exemplo: vendaApiController.atualizarStatusVenda(vendaId, novoStatus.name());
            tableModel.setValueAt(novoStatus.getDescricao(), row, 2);
            JOptionPane.showMessageDialog(this, "Status atualizado para: " + novoStatus.getDescricao());
        }
    }

    private void finalizarParcialVendaSelecionada() {
        int row = tabelaVendas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para finalizar parcialmente.");
            return;
        }
        Long vendaId = (Long) tableModel.getValueAt(row, 0);
        // Chamar lógica de finalização parcial (a ser implementada)
        JOptionPane.showMessageDialog(this, "Finalizar parcialmente venda: " + vendaId);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConsultaVendas tela = new ConsultaVendas();
            tela.setVisible(true);
        });
    }
}
