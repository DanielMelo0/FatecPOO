package View;

import DAO.PedidoDAO;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

/**
 *
 * @author William Marques
 */
public class TelaConsultaPedido extends JFrame {

    private PedidoDAO pedidos;
    private ResultSet objetoResultSet;
    private JButton btnOrdemAlfabetica, btnOrdemCodigoPedido, btnSair;
    private JScrollPane scrollTable;
    private JTable objetoTabela;
    private PreparedStatement objetoPreparedStatement;
    private String vSQL;

    public TelaConsultaPedido() {
        inicializacomponentes();
        windowOpened();
        definirEventos();
    }

    public void inicializacomponentes() {        
        this.setVisible(true);        
        this.setSize(1000, 550);        
        setLocationRelativeTo(null);
        this.setTitle("Consultas");
        this.setResizable(true);        

        JPanel panel = new JPanel();
        this.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        btnOrdemAlfabetica = new JButton("Ordem alfabética");
        btnOrdemCodigoPedido = new JButton("Ordenar por Código do Pedido");
        btnSair = new JButton("Sair");
        scrollTable = new JScrollPane();

        panel.add(scrollTable);
        panel.add(btnOrdemAlfabetica);
        panel.add(btnOrdemCodigoPedido);
        panel.add(btnSair);
    }

    public void windowOpened() {
        try {
            pedidos = new PedidoDAO();
            if (!pedidos.objetoConexaoBD.getConnection()) {
                JOptionPane.showMessageDialog(null, "Falha na conexão!");
                System.exit(0);
            }

            vSQL = "select * from pedidos";
            objetoPreparedStatement = pedidos.objetoConexaoBD.objetoConnection.prepareStatement(vSQL);
            objetoResultSet = objetoPreparedStatement.executeQuery();
            DefaultTableModel tableModel = new DefaultTableModel();
            int qtcolunas = objetoResultSet.getMetaData().getColumnCount();
            for (int indice = 1; indice <= qtcolunas; indice++) {
                tableModel.addColumn(objetoResultSet.getMetaData().getColumnName(indice));
            }
            objetoTabela = new JTable(tableModel);
            objetoTabela.getColumnModel().getColumn(0).setPreferredWidth(15);
            objetoTabela.getColumnModel().getColumn(1).setPreferredWidth(150);
            objetoTabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            

            while (objetoResultSet.next()) {
                try {
                    String[] dados = new String[qtcolunas];
                    for (int i = 1; i <= qtcolunas; i++) {
                        dados[i - 1] = objetoResultSet.getString(i);
                    }
                    tableModel.addRow(dados);
                    System.out.println();

                } catch (SQLException erro) {
                }
                scrollTable.setViewportView(objetoTabela);
            }
            objetoResultSet.close();
            objetoPreparedStatement.close();
            pedidos.objetoConexaoBD.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Comando SQL inválido!" + erro.toString());
        }
    }

    public void definirEventos() {

        btnOrdemAlfabetica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vSQL = "select * from pedidos order by codcliente";
                carregaTable();
            }
        });
        btnOrdemCodigoPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vSQL = "select * from pedidos order by codped";
                carregaTable();
            }
        });

        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();
                
            }
        });
    }

    public void carregaTable() {

        try {
            pedidos = new PedidoDAO();
            if (!pedidos.objetoConexaoBD.getConnection()) {
                JOptionPane.showMessageDialog(null, "Falha na conexão!");
                System.exit(0);

            }

            //vSQL = "select * from pedido order by Nome";
            objetoPreparedStatement = pedidos.objetoConexaoBD.objetoConnection.prepareStatement(vSQL);
            objetoResultSet = objetoPreparedStatement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();

            int qtcolunas = objetoResultSet.getMetaData().getColumnCount();
            for (int indice = 1; indice <= qtcolunas; indice++) {
                tableModel.addColumn(objetoResultSet.getMetaData().getColumnName(indice));
            }
            objetoTabela = new JTable(tableModel);

            objetoTabela.getColumnModel().getColumn(0).setPreferredWidth(15);
            objetoTabela.getColumnModel().getColumn(1).setPreferredWidth(150);
            objetoTabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            

            while (objetoResultSet.next()) {
                try {
                    String[] dados = new String[qtcolunas];
                    for (int i = 1; i <= qtcolunas; i++) {
                        dados[i - 1] = objetoResultSet.getString(i);
                    }
                    tableModel.addRow(dados);
                    System.out.println();

                } catch (SQLException erro) {

                }
                scrollTable.setViewportView(objetoTabela);

            }
            objetoResultSet.close();
            objetoPreparedStatement.close();
            pedidos.objetoConexaoBD.close();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Comando SQL inválido!" + erro.toString());
        }
    }
}
