
package com.example.vrteste.front.Pedido.View;

import com.example.vrteste.front.Pedido.Controller.ProdutoApiController;
import com.example.vrteste.front.Venda.Controller.VendaApiController;
import com.example.vrteste.front.Venda.Model.ClienteDto;
import com.example.vrteste.front.Venda.Model.ProdutoModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Produtos extends JFrame {
    private JTable tabelaProdutos;
    private JButton btnAdicionarVenda;
    private DefaultTableModel tableModel;
    private VendaApiController vendaApiController;

    public Produtos() {
        setTitle("Produtos Disponíveis");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        vendaApiController = new VendaApiController();
        initComponents();
        carregarProdutosDaApi();
    }

    public void carregarProdutosDaApi() {
        try {
            List<ProdutoModel> produtos = ProdutoApiController.listarProdutos();
            tableModel.setRowCount(0);
            for (ProdutoModel prod : produtos) {
                tableModel.addRow(new Object[]{
                    prod.getId(),
                    prod.getDescricao(),
                    prod.getEstoque(),
                    prod.getPreco(),
                    prod.getUnidade(),
                    prod.getUltimaAtualizacao(),
                    false
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Descrição", "Estoque", "Preço", "Unidade", "Última Atualização", "Selecionar"}
        ) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 6) return Boolean.class;
                return super.getColumnClass(column);
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };
        tabelaProdutos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);

        btnAdicionarVenda = new JButton("Adicionar Venda");
        btnAdicionarVenda.addActionListener(e -> abrirCadastroVenda());

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            this.dispose();
        });

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnAdicionarVenda);
        panelBotoes.add(btnVoltar);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelBotoes, BorderLayout.SOUTH);
    }

    private void abrirCadastroVenda() {
        java.util.List<Integer> selecionados = new java.util.ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean marcado = (Boolean) tableModel.getValueAt(i, 6);
            if (marcado != null && marcado) {
                selecionados.add(i);
            }
        }
        if (selecionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione ao menos um produto para realizar a venda.");
            return;
        }
        try {
            List<ClienteDto> clientes = vendaApiController.listarClientes();
            if (clientes == null || clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente disponível.");
                return;
            }
            JComboBox<ClienteDto> cbClientes = new JComboBox<>(clientes.toArray(new ClienteDto[0]));
            cbClientes.setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof com.example.vrteste.front.Venda.Model.ClienteDto) {
                        setText(((com.example.vrteste.front.Venda.Model.ClienteDto) value).getNome());
                    }
                    return this;
                }
            });
            int option = JOptionPane.showConfirmDialog(this, cbClientes, "Selecione o cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }
            ClienteDto clienteSelecionado = (ClienteDto) cbClientes.getSelectedItem();
            if (clienteSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Cliente não selecionado.");
                return;
            }
            Long clienteId = clienteSelecionado.getId();
            if (selecionados.size() == 1) {
                int selectedRow = selecionados.get(0);
                ProdutoModel produto = new ProdutoModel(
                    ((Number) tableModel.getValueAt(selectedRow, 0)).longValue(),
                    (String) tableModel.getValueAt(selectedRow, 1),
                    (Integer) tableModel.getValueAt(selectedRow, 2),
                    (Double) tableModel.getValueAt(selectedRow, 3),
                    (String) tableModel.getValueAt(selectedRow, 4),
                    null
                );
                int quantidade = 1;
                CadastroVenda telaVenda = new CadastroVenda(produto, quantidade, this, clienteId);
                telaVenda.setVisible(true);
            } else {
                List<ProdutoModel> produtosSelecionados = new ArrayList<>();
                for (int row : selecionados) {
                    ProdutoModel produto = new ProdutoModel(
                        ((Number) tableModel.getValueAt(row, 0)).longValue(),
                        (String) tableModel.getValueAt(row, 1),
                        (Integer) tableModel.getValueAt(row, 2),
                        (Double) tableModel.getValueAt(row, 3),
                        (String) tableModel.getValueAt(row, 4),
                        null
                    );
                    produtosSelecionados.add(produto);
                }
                CadastroVendaLote telaLote = new CadastroVendaLote(this, produtosSelecionados, clienteId, this);
                telaLote.setVisible(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao registrar venda: " + ex.getMessage());
        }
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            carregarProdutosDaApi();
        }
        super.setVisible(b);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Produtos tela = new Produtos();
            tela.setVisible(true);
        });
    }
}

