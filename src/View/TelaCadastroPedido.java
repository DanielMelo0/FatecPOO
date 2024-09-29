package View;

import DAO.PedidoDAO;
import View.TelaPrincipal;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luciana
 */
class TelaCadastroPedido extends JFrame {

    JLabel labelCodigoPedido, labelcliente_codigoCliente, labelData, label3, label4;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btVoltarMenuPrincipal, btSair;
    JButton btPrimeiro, btAnterior, btProximo, btUltimo, btCons;
    JPanel painel, painelBotoes, painelREG;
    JFrame janela;
    static JTextField textFieldCodigoPedido, textFieldCliente_codigoCliente, textFieldData;
    private PedidoDAO pedidos;
    private ResultSet objetoResultSet;
    private TelaConsultaPedido consultaPedidos;

    public TelaCadastroPedido() {
        inicializacomponentes();
        definirEventos();
    }

    public void inicializacomponentes() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());     //define layout da janela

        painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.PAGE_AXIS));    //define layout do painel
        this.add(painel, BorderLayout.NORTH);

        painelBotoes = new JPanel(new FlowLayout()); //define layout do painelBotoes    
        this.add(painelBotoes, BorderLayout.CENTER);

        painelREG = new JPanel(new FlowLayout());  //define layout do painelREG
        this.add(painelREG, BorderLayout.SOUTH);

        setTitle("Cadastro de Pedidos");
        setSize(750, 300);
        setLocationRelativeTo(null);
        labelCodigoPedido = new JLabel("Código do Pedido: ");
        textFieldCodigoPedido = new JTextField(4);
        labelcliente_codigoCliente = new JLabel("Código do Cliente: ");
        textFieldCliente_codigoCliente = new JTextField(80);
        labelData = new JLabel("Data:");
        textFieldData = new JTextField(80);
        label3 = new JLabel("Movimentação de Registros");
        btGravar = new JButton("Gravar");
        btAlterar = new JButton("Alterar");
        btExcluir = new JButton("Excluir");
        btLocalizar = new JButton("Localizar");
        btNovo = new JButton("Novo");
        btCancelar = new JButton("Cancelar");
        btCons = new JButton("Consultar");
        btVoltarMenuPrincipal = new JButton("Menu Principal");
        btSair = new JButton("Sair");
        btPrimeiro = new JButton("<<");
        btPrimeiro.setToolTipText("Primeiro");
        btAnterior = new JButton("<");
        btAnterior.setToolTipText("Anterior");
        btProximo = new JButton(">");
        btProximo.setToolTipText("Próximo");
        btUltimo = new JButton(">>");
        btUltimo.setToolTipText("Ultimo");
        painel.add(labelCodigoPedido);
        painel.add(textFieldCodigoPedido);
        painel.add(labelcliente_codigoCliente);
        painel.add(textFieldCliente_codigoCliente);
        painel.add(labelData);
        painel.add(textFieldData);
        painelBotoes.add(btNovo);
        painelBotoes.add(btLocalizar);
        painelBotoes.add(btGravar);
        painelBotoes.add(btAlterar);
        painelBotoes.add(btExcluir);
        painelBotoes.add(btCancelar);
        painelBotoes.add(btCons);
        painelBotoes.add(btVoltarMenuPrincipal);
        painelBotoes.add(btSair);
        painelREG.add(label3);
        painelREG.add(btPrimeiro);
        painelREG.add(btAnterior);
        painelREG.add(btProximo);
        painelREG.add(btUltimo);
        setResizable(true);

        setBotoes(true, true, false, false, false, false);
        pedidos = new PedidoDAO();
        if (!pedidos.objetoConexaoBD.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão!");
            System.exit(0);
        }
        tabelaPedidos();
        //tfRA.setEnabled(false);
        //tfNome.setEnabled(false);
        //carregaDados();
    }

    public void setBotoes(boolean bNovo, boolean bLocalizar, boolean bGravar,
            boolean bAlterar, boolean bExcluir, boolean bCancelar) {

        btNovo.setEnabled(bNovo);
        btLocalizar.setEnabled(bLocalizar);
        btGravar.setEnabled(bGravar);
        btAlterar.setEnabled(bAlterar);
        btExcluir.setEnabled(bExcluir);
        btCancelar.setEnabled(bCancelar);
    }

    public void definirEventos() {
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pedidos.objetoConexaoBD.close();
                System.exit(0);
            }
        });

        btVoltarMenuPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pedidos.objetoConexaoBD.close();
                //estanciar a tela pedido
                TelaPrincipal objetofrmPrincipalVIEW = new TelaPrincipal();
                //exibir o objeto tela principal
                objetofrmPrincipalVIEW.setVisible(true);
                //fechar a tela 
                dispose();
            }
        });

        btProximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    objetoResultSet.next();
                    carregaDados();
                } catch (SQLException erro) {

                }
            }
        });

        btAnterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    objetoResultSet.previous();
                    carregaDados();
                } catch (SQLException erro) {

                }
            }
        });

        btPrimeiro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    objetoResultSet.first();
                    carregaDados();
                } catch (SQLException erro) {

                }
            }
        });

        btUltimo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    objetoResultSet.last();
                    carregaDados();
                } catch (SQLException erro) {

                }
            }
        });

        btNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldCodigoPedido.setEnabled(false);
                //tfNome.setEnabled(true);
                limparcampos();

                setBotoes(false, false, true, false, false, true);
                textFieldCliente_codigoCliente.requestFocus();
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldCodigoPedido.setEnabled(true);
                //tfRA.setEnabled(false);
                //tfNome.setEnabled(false);
                limparcampos();
                tabelaPedidos();

            }
        });

        btGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (textFieldCliente_codigoCliente.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O nome não pode ser vazio!");
                    textFieldCliente_codigoCliente.requestFocus();
                    return;
                }                
                pedidos.pedido.setCliente_codigoCliente(Integer.parseInt(textFieldCliente_codigoCliente.getText()));
                pedidos.pedido.setData(textFieldData.getText());                

                JOptionPane.showMessageDialog(null, pedidos.atualizar(PedidoDAO.INCLUSAO));
                textFieldCodigoPedido.setEnabled(true);
                limparcampos();
                tabelaPedidos();

            }
        });

        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                pedidos.pedido.setCliente_codigoCliente(Integer.parseInt(textFieldCliente_codigoCliente.getText()));
                pedidos.pedido.setData(textFieldData.getText());
              
                JOptionPane.showMessageDialog(null, pedidos.atualizar(PedidoDAO.ALTERACAO));
                limparcampos();
                tabelaPedidos();
            }
        });

        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pedidos.pedido.setCodigoPedido(Integer.parseInt(textFieldCodigoPedido.getText()));
                pedidos.localizar();
                int n = JOptionPane.showConfirmDialog(null, pedidos.pedido.getCliente_codigoCliente(),
                        "Excluir o pedido?", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, pedidos.atualizar(PedidoDAO.EXCLUSAO));
                    limparcampos();
                    tabelaPedidos();
                }
            }
        });

        btLocalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarCampos();
                tabelaPedidos();

            }
        });

        btCons.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame janela = new TelaConsultaPedido();
                janela.setVisible(true);
            }
        });

    }

    public void limparcampos() {
        //textFieldCodigoPedido.setText("");
        textFieldCliente_codigoCliente.setText("");
        textFieldData.setText("");        
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizarCampos() {
        pedidos.pedido.setCodigoPedido(Integer.parseInt(textFieldCodigoPedido.getText()));
        
        if (pedidos.localizar()) {
            textFieldCodigoPedido.setText(Integer.toString(pedidos.pedido.getCodigoPedido()));
            textFieldCliente_codigoCliente.setText(Integer.toString(pedidos.pedido.getCliente_codigoCliente()));
            textFieldData.setText(pedidos.pedido.getData());
            
            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Pedido não econtrado!");
            limparcampos();
        }
    }

    public void tabelaPedidos() {
        try {
            String sql = "Select * from pedidos";
            PreparedStatement statement = pedidos.objetoConexaoBD.objetoConnection.prepareStatement(sql);
            objetoResultSet = statement.executeQuery();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Problemas na conexão!\n" + erro.toString());
        }

    }

    public void carregaDados() {
        try {
            //String vazio = " ";
            if (objetoResultSet.isAfterLast()) {
                objetoResultSet.last();
            }
            if (objetoResultSet.isBeforeFirst()) {
                objetoResultSet.first();
            }

            textFieldCodigoPedido.setText(objetoResultSet.getString("codped"));
            textFieldCliente_codigoCliente.setText(objetoResultSet.getString("codcliente"));
            textFieldData.setText(objetoResultSet.getString("data"));
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Problemas na conexão!\n" + erro.toString());
        }
    }
}
