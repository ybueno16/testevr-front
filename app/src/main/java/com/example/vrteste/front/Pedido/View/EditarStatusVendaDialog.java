package com.example.vrteste.front.Pedido.View;

import com.example.vrteste.front.Venda.Model.StatusVenda;
import javax.swing.*;
import java.awt.*;

public class EditarStatusVendaDialog extends JDialog {
    private JComboBox<StatusVenda> cbStatus;
    private JButton btnSalvar;
    private StatusVenda statusSelecionado;

    public EditarStatusVendaDialog(Frame owner, StatusVenda statusAtual) {
        super(owner, "Editar Status da Venda", true);
        setSize(300, 150);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        cbStatus = new JComboBox<>(StatusVenda.values());
        cbStatus.setSelectedItem(statusAtual);
        add(cbStatus, BorderLayout.CENTER);

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            statusSelecionado = (StatusVenda) cbStatus.getSelectedItem();
            setVisible(false);
        });
        add(btnSalvar, BorderLayout.SOUTH);
    }

    public StatusVenda getStatusSelecionado() {
        return statusSelecionado;
    }
}
