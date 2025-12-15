package com.example.vrteste.front.Pedido.View;

import com.example.vrteste.front.Cliente.Controller.ClienteApiController;
import com.example.vrteste.front.Cliente.Model.ClienteModel;
import com.example.vrteste.front.Venda.Model.ProdutoModel;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class CadastroVenda extends JFrame {
    private JComboBox<ClienteModel> cbCliente;
    private JTextField txtDescricao;
    private JTextField txtValor;
    private JTextField txtQuantidade;
    private JButton btnSalvar;
    private JButton btnVoltar;

    private ProdutoModel produto;
    private List<ClienteModel> clientes;
    private int quantidade;
    private Produtos telaProdutos;
    private Long clienteIdSelecionado;
    private com.example.vrteste.front.Venda.Controller.VendaApiController vendaApiController;

    public CadastroVenda(ProdutoModel produto, int quantidade, Produtos telaProdutos, Long clienteIdSelecionado) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.telaProdutos = telaProdutos;
        this.clienteIdSelecionado = clienteIdSelecionado;
    setTitle("Cadastro de Venda");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);
    vendaApiController = new com.example.vrteste.front.Venda.Controller.VendaApiController();
    carregarClientes();
    initComponents();
    }

    private void carregarClientes() {
        try {
            clientes = ClienteApiController.listarClientes();
        } catch (Exception e) {
            clientes = java.util.Collections.emptyList();
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage());
        }
    }

    private void initComponents() {

        JLabel lblDescricao = new JLabel("Produto:");
        txtDescricao = new JTextField(produto.getDescricao());
        txtDescricao.setEditable(false);

        JLabel lblValor = new JLabel("Valor:");
        txtValor = new JTextField(String.valueOf(produto.getPreco()));
        txtValor.setEditable(false);

        JLabel lblQuantidade = new JLabel("Quantidade:");
    txtQuantidade = new JTextField(String.valueOf(quantidade));

        btnSalvar = new JButton("Salvar Venda");
        btnVoltar = new JButton("Voltar");

        btnSalvar.addActionListener(e -> salvarVenda());
        btnVoltar.addActionListener(e -> {
            this.dispose();
            if (telaProdutos != null) {
                telaProdutos.setVisible(true);
            }
        });

    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
    panel.add(lblDescricao);
    panel.add(txtDescricao);
    panel.add(lblValor);
    panel.add(txtValor);
    panel.add(lblQuantidade);
    panel.add(txtQuantidade);
    panel.add(btnSalvar);
    panel.add(btnVoltar);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void salvarVenda() {
        int quantidadeVenda;
        try {
            quantidadeVenda = Integer.parseInt(txtQuantidade.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida");
            return;
        }
        BigDecimal valor = BigDecimal.valueOf(produto.getPreco());
        try {
            vendaApiController.cadastrarVenda(produto, clienteIdSelecionado, quantidadeVenda);
            JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao registrar venda: " + ex.getMessage());
        }
        this.dispose();
        if (telaProdutos != null) {
            telaProdutos.setVisible(true);
        }
    }
}
