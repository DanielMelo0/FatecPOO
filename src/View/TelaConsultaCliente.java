package View;

import DAO.ClienteDAO;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

/**
 *
 * @author William Marques
 */
public class TelaConsultaCliente extends JFrame {

    private ClienteDAO clientes;
    private ResultSet objetoResultSet;
    private JButton btnOrdemAlfabetica, btnOrdemCodigoCliente, btnSair;
    private JScrollPane scrollTable;
    private JTable objetoTabela;
    private PreparedStatement objetoPreparedStatement;
    private String vSQL;

    public TelaConsultaCliente() {
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
        btnOrdemCodigoCliente = new JButton("Ordenar por Código do Cliente");
        btnSair = new JButton("Sair");
        scrollTable = new JScrollPane();

        panel.add(scrollTable);
        panel.add(btnOrdemAlfabetica);
        panel.add(btnOrdemCodigoCliente);
        panel.add(btnSair);
    }

    public void windowOpened() {
        try {
            clientes = new ClienteDAO();
            if (!clientes.objetoConexaoBD.getConnection()) {
                JOptionPane.showMessageDialog(null, "Falha na conexão!");
                System.exit(0);
            }

            vSQL = "select * from clientes";
            objetoPreparedStatement = clientes.objetoConexaoBD.objetoConnection.prepareStatement(vSQL);
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
            objetoTabela.getColumnModel().getColumn(3).setPreferredWidth(60);
            objetoTabela.getColumnModel().getColumn(4).setPreferredWidth(60);
            objetoTabela.getColumnModel().getColumn(5).setPreferredWidth(60);
            objetoTabela.getColumnModel().getColumn(6).setPreferredWidth(35);
            objetoTabela.getColumnModel().getColumn(7).setPreferredWidth(90);
            objetoTabela.getColumnModel().getColumn(8).setPreferredWidth(80);
            objetoTabela.getColumnModel().getColumn(9).setPreferredWidth(80);

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
            clientes.objetoConexaoBD.close();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Comando SQL inválido!" + erro.toString());
        }
    }

    public void definirEventos() {

        btnOrdemAlfabetica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vSQL = "select * from clientes order by Nome";
                carregaTable();
            }
        });
        btnOrdemCodigoCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vSQL = "select * from clientes order by codcli";
                carregaTable();
            }
        });

        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
                //clientes.objetoConexaoBD.close();
                //setVisible(false);
                dispose();
                //Runtime.getRuntime().exit(0);
            }
        });
    }

    public void carregaTable() {

        try {
            clientes = new ClienteDAO();
            if (!clientes.objetoConexaoBD.getConnection()) {
                JOptionPane.showMessageDialog(null, "Falha na conexão!");
                System.exit(0);

            }

            //vSQL = "select * from cliente order by Nome";
            objetoPreparedStatement = clientes.objetoConexaoBD.objetoConnection.prepareStatement(vSQL);
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
            objetoTabela.getColumnModel().getColumn(3).setPreferredWidth(60);
            objetoTabela.getColumnModel().getColumn(4).setPreferredWidth(60);
            objetoTabela.getColumnModel().getColumn(5).setPreferredWidth(60);
            objetoTabela.getColumnModel().getColumn(6).setPreferredWidth(35);
            objetoTabela.getColumnModel().getColumn(7).setPreferredWidth(90);
            objetoTabela.getColumnModel().getColumn(8).setPreferredWidth(80);
            objetoTabela.getColumnModel().getColumn(9).setPreferredWidth(80);

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
            clientes.objetoConexaoBD.close();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Comando SQL inválido!" + erro.toString());
        }
    }
}
