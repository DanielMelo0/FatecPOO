package View;

import DAO.ItensPedDAO;
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
class TelaCadastroItensPed extends JFrame {

    JLabel labelId, labelPedidos_CodigoPedido, labelProdutos_codigoProduto, labelQuantidade, label3, label4;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btVoltarMenuPrincipal, btSair;
    JButton btPrimeiro, btAnterior, btProximo, btUltimo, btCons;
    JPanel painel, painelBotoes, painelREG;
    JFrame janela;
    static JTextField textFieldId, textFieldPedidos_CodigoPedido, textFieldProdutos_codigoProduto, textFieldQuantidade;
    private ItensPedDAO itensPeds;
    private ResultSet objetoResultSet;
    private TelaConsultaItensPed consultaItensPed;

    public TelaCadastroItensPed() {
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

        setTitle("Cadastro de ItensPeds");
        setSize(750, 300);
        setLocationRelativeTo(null);
        labelId = new JLabel("ID: ");
        textFieldId = new JTextField(4);
        labelPedidos_CodigoPedido = new JLabel("Código do Pedido: ");
        textFieldPedidos_CodigoPedido = new JTextField(4);
        labelProdutos_codigoProduto = new JLabel("Código do Produto: ");
        textFieldProdutos_codigoProduto = new JTextField(80);
        labelQuantidade = new JLabel("Quantidade:");
        textFieldQuantidade = new JTextField(80);
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
        painel.add(labelId);
        painel.add(textFieldId);
        painel.add(labelPedidos_CodigoPedido);
        painel.add(textFieldPedidos_CodigoPedido);
        painel.add(labelProdutos_codigoProduto);
        painel.add(textFieldProdutos_codigoProduto);
        painel.add(labelQuantidade);
        painel.add(textFieldQuantidade);
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
        itensPeds = new ItensPedDAO();
        if (!itensPeds.objetoConexaoBD.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão!");
            System.exit(0);
        }
        tabelaItensPeds();
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
                itensPeds.objetoConexaoBD.close();
                System.exit(0);
            }
        });

        btVoltarMenuPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                itensPeds.objetoConexaoBD.close();
                //estanciar a tela itensPed
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
                textFieldId.setEnabled(false);
                //tfNome.setEnabled(true);
                limparcampos();

                setBotoes(false, false, true, false, false, true);
                textFieldPedidos_CodigoPedido.requestFocus();
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldId.setEnabled(true);
                //tfRA.setEnabled(false);
                //tfNome.setEnabled(false);
                limparcampos();
                tabelaItensPeds();

            }
        });

        btGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (textFieldPedidos_CodigoPedido.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O Codigo do Pedido não pode ser vaziu!");
                    textFieldPedidos_CodigoPedido.requestFocus();
                    return;
                }                
                itensPeds.itensPed.setPedidos_CodigoPedido(Integer.parseInt(textFieldPedidos_CodigoPedido.getText()));
                itensPeds.itensPed.setProdutos_codigoProduto(Integer.parseInt(textFieldProdutos_codigoProduto.getText()));
                itensPeds.itensPed.setQuantidade(Double.parseDouble(textFieldQuantidade.getText()));                

                JOptionPane.showMessageDialog(null, itensPeds.atualizar(ItensPedDAO.INCLUSAO));
                textFieldId.setEnabled(true);
                limparcampos();
                tabelaItensPeds();

            }
        });

        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                itensPeds.itensPed.setPedidos_CodigoPedido(Integer.parseInt(textFieldPedidos_CodigoPedido.getText()));
                itensPeds.itensPed.setProdutos_codigoProduto(Integer.parseInt(textFieldProdutos_codigoProduto.getText()));
                itensPeds.itensPed.setQuantidade(Double.parseDouble(textFieldQuantidade.getText()));
              
                JOptionPane.showMessageDialog(null, itensPeds.atualizar(ItensPedDAO.ALTERACAO));
                limparcampos();
                tabelaItensPeds();
            }
        });

        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                itensPeds.itensPed.setId(Integer.parseInt(textFieldId.getText()));
                itensPeds.localizar();
                int n = JOptionPane.showConfirmDialog(null, itensPeds.itensPed.getPedidos_CodigoPedido(),
                        "Excluir o itensPed?", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, itensPeds.atualizar(ItensPedDAO.EXCLUSAO));
                    limparcampos();
                    tabelaItensPeds();
                }
            }
        });

        btLocalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarCampos();
                tabelaItensPeds();

            }
        });

        btCons.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame janela = new TelaConsultaItensPed();
                janela.setVisible(true);
            }
        });

    }

    public void limparcampos() {
        //textFieldCodigoItensPed.setText("");
        textFieldPedidos_CodigoPedido.setText("");
        textFieldProdutos_codigoProduto.setText("");
        textFieldQuantidade.setText("");        
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizarCampos() {
        itensPeds.itensPed.setId(Integer.parseInt(textFieldId.getText()));
        
        if (itensPeds.localizar()) {
            textFieldPedidos_CodigoPedido.setText(Integer.toString(itensPeds.itensPed.getPedidos_CodigoPedido()));
            textFieldProdutos_codigoProduto.setText(Integer.toString(itensPeds.itensPed.getProdutos_codigoProduto()));
            textFieldQuantidade.setText(Double.toString(itensPeds.itensPed.getQuantidade()));
            
            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Itens Pedidos não econtrado!");
            limparcampos();
        }
    }

    public void tabelaItensPeds() {
        try {
            String sql = "Select * from itens_ped";
            PreparedStatement statement = itensPeds.objetoConexaoBD.objetoConnection.prepareStatement(sql);
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

            textFieldId.setText(objetoResultSet.getString("id"));
            textFieldPedidos_CodigoPedido.setText(objetoResultSet.getString("codped"));
            textFieldProdutos_codigoProduto.setText(objetoResultSet.getString("codprod"));
            textFieldQuantidade.setText(objetoResultSet.getString("qtde"));
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Problemas na conexão!\n" + erro.toString());
        }
    }
}
