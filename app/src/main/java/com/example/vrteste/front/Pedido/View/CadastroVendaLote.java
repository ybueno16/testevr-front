package com.example.vrteste.front.Pedido.View;

import com.example.vrteste.front.Pedido.Controller.ProdutoBaixaApiController;
import com.example.vrteste.front.Pedido.Model.BaixaEstoqueRequest;
import com.example.vrteste.front.Venda.Model.ProdutoModel;
import com.example.vrteste.front.Venda.Controller.VendaApiController;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroVendaLote extends JDialog {
    private JPanel panel;
    private java.util.List<JTextField> txtQuantidades;
    private List<ProdutoModel> produtos;
    private Long clienteId;
    private Produtos telaProdutos;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private VendaApiController vendaApiController;

    public CadastroVendaLote(Frame parent, List<ProdutoModel> produtos, Long clienteId, Produtos telaProdutos) {
        super(parent, "Cadastro de Venda em Lote", true);
        this.produtos = produtos;
        this.clienteId = clienteId;
        this.telaProdutos = telaProdutos;
    setSize(700, 120 + produtos.size() * 50);
        setLocationRelativeTo(parent);
    vendaApiController = new VendaApiController();
    initComponents();
    }

    private void initComponents() {
    JPanel panelGrid = new JPanel(new GridLayout(produtos.size() + 1, 5, 20, 20));
        panelGrid.add(new JLabel("ID"));
        panelGrid.add(new JLabel("Descrição"));
        panelGrid.add(new JLabel("Estoque"));
        panelGrid.add(new JLabel("Preço"));
        panelGrid.add(new JLabel("Quantidade"));

        txtQuantidades = new java.util.ArrayList<>();
        for (ProdutoModel prod : produtos) {
            panelGrid.add(new JLabel(String.valueOf(prod.getId())));
            panelGrid.add(new JLabel(prod.getDescricao()));
            panelGrid.add(new JLabel(String.valueOf(prod.getEstoque())));
            panelGrid.add(new JLabel(String.valueOf(prod.getPreco())));
            JTextField txtQtd = new JTextField("1");
            txtQuantidades.add(txtQtd);
            panelGrid.add(txtQtd);
        }

        btnSalvar = new JButton("Salvar Venda");
        btnCancelar = new JButton("Cancelar");
        btnSalvar.addActionListener(e -> salvarVenda());
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotoes = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnCancelar);

        panel = new JPanel(new BorderLayout(10, 10));
        panel.add(panelGrid, BorderLayout.CENTER);
        panel.add(panelBotoes, BorderLayout.SOUTH);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void salvarVenda() {
        int sucesso = 0;
        int falha = 0;
    List<BaixaEstoqueRequest> baixas = new java.util.ArrayList<>();
        for (int i = 0; i < produtos.size(); i++) {
            ProdutoModel prod = produtos.get(i);
            int quantidade = 1;
            try {
                quantidade = Integer.parseInt(txtQuantidades.get(i).getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida para o produto " + prod.getDescricao());
                return;
            }
            try {
                vendaApiController.cadastrarVenda(prod, clienteId, quantidade);
                baixas.add(new BaixaEstoqueRequest(prod.getId(), quantidade));
                sucesso++;
            } catch (Exception ex) {
                falha++;
            }
        }
        try {
            java.util.List<BaixaEstoqueRequest> baixasPedido = new java.util.ArrayList<>();
            for (BaixaEstoqueRequest b : baixas) {
                baixasPedido.add(new com.example.vrteste.front.Pedido.Model.BaixaEstoqueRequest(b.getId(), b.getQuantidade()));
            }
            ProdutoBaixaApiController.baixarEstoqueEmLote(baixasPedido);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao baixar estoque: " + ex.getMessage());
        }
    JOptionPane.showMessageDialog(this, "Venda em lote cadastrada com sucesso!\nVendas registradas: " + sucesso + "\nFalhas: " + falha);
        if (telaProdutos != null) {
            telaProdutos.carregarProdutosDaApi();
        }
        dispose();
    }
}
