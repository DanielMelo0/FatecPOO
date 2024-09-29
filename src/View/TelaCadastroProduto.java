package View;

import DAO.ProdutoDAO;
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
class TelaCadastroProduto extends JFrame {

    JLabel labelCodigoProduto, labelDescricao, labelPreco, labelUnidade, labelQuantidadeInicial, labelDataCadastro, labelQuantidadeAtual, label3, label4;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btVoltarMenuPrincipal, btSair;
    JButton btPrimeiro, btAnterior, btProximo, btUltimo, btCons;
    JPanel painel, painelBotoes, painelREG;
    JFrame janela;
    static JTextField textFieldCodigoProduto, textFieldDescricao, textFieldPreco, textFieldUnidade, textFieldQuantidadeInicial, textFieldDataCadastro, textFieldQuantidadeAtual;
    private ProdutoDAO produtos;
    private ResultSet objetoResultSet;
    private TelaConsultaProduto consultaProdutos;

    public TelaCadastroProduto() {
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

        setTitle("Cadastro de Produtos");
        setSize(750, 450);
        setLocationRelativeTo(null);
        labelCodigoProduto = new JLabel("Código do Produto: ");
        textFieldCodigoProduto = new JTextField(4);
        labelDescricao = new JLabel("Descrição: ");
        textFieldDescricao = new JTextField(80);
        labelPreco = new JLabel("Preço:");
        textFieldPreco = new JTextField(80);
        labelUnidade = new JLabel("Unidade:");
        textFieldUnidade = new JTextField(80);
        labelQuantidadeInicial = new JLabel("Quantidade Inicial:");
        textFieldQuantidadeInicial = new JTextField(80);
        labelDataCadastro = new JLabel("Data de Cadastro:");
        textFieldDataCadastro = new JTextField(80);
        labelQuantidadeAtual = new JLabel("Quantidade Atual:");
        textFieldQuantidadeAtual = new JTextField(80);        
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
        painel.add(labelCodigoProduto);
        painel.add(textFieldCodigoProduto);
        painel.add(labelDescricao);
        painel.add(textFieldDescricao);
        painel.add(labelPreco);
        painel.add(textFieldPreco);
        painel.add(labelUnidade);
        painel.add(textFieldUnidade);
        painel.add(labelQuantidadeInicial);
        painel.add(textFieldQuantidadeInicial);
        painel.add(labelDataCadastro);
        painel.add(textFieldDataCadastro);
        painel.add(labelQuantidadeAtual);
        painel.add(textFieldQuantidadeAtual);        
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
        produtos = new ProdutoDAO();
        if (!produtos.objetoConexaoBD.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão!");
            System.exit(0);
        }
        tabelaProdutos();
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
                produtos.objetoConexaoBD.close();
                System.exit(0);
            }
        });

        btVoltarMenuPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                produtos.objetoConexaoBD.close();
                //estanciar a tela produto
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
                textFieldCodigoProduto.setEnabled(false);
                //tfNome.setEnabled(true);
                limparcampos();

                setBotoes(false, false, true, false, false, true);
                textFieldDescricao.requestFocus();
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldCodigoProduto.setEnabled(true);
                //tfRA.setEnabled(false);
                //tfNome.setEnabled(false);
                limparcampos();
                tabelaProdutos();

            }
        });

        btGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (textFieldDescricao.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O nome não pode ser vazio!");
                    textFieldDescricao.requestFocus();
                    return;
                }
                produtos.produto.setDescricao(textFieldDescricao.getText());
                produtos.produto.setPreco(Double.parseDouble(textFieldPreco.getText()));
                produtos.produto.setUnidade(textFieldUnidade.getText());
                produtos.produto.setQuantidadeInicial(Double.parseDouble(textFieldQuantidadeInicial.getText()));
                produtos.produto.setDataCadastro(textFieldDataCadastro.getText());
                produtos.produto.setQuantidadeAtual(Double.parseDouble(textFieldQuantidadeAtual.getText()));
                

                JOptionPane.showMessageDialog(null, produtos.atualizar(ProdutoDAO.INCLUSAO));
                textFieldCodigoProduto.setEnabled(true);
                limparcampos();
                tabelaProdutos();

            }
        });

        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                produtos.produto.setDescricao(textFieldDescricao.getText());
                produtos.produto.setPreco(Double.parseDouble(textFieldPreco.getText()));
                produtos.produto.setUnidade(textFieldUnidade.getText());
                produtos.produto.setQuantidadeInicial(Double.parseDouble(textFieldQuantidadeInicial.getText()));
                produtos.produto.setDataCadastro(textFieldDataCadastro.getText());
                produtos.produto.setQuantidadeAtual(Double.parseDouble(textFieldQuantidadeAtual.getText()));
                
                JOptionPane.showMessageDialog(null, produtos.atualizar(ProdutoDAO.ALTERACAO));
                limparcampos();
                tabelaProdutos();
            }
        });

        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                produtos.produto.setCodigoProduto(Integer.parseInt(textFieldCodigoProduto.getText()));
                produtos.localizar();
                int n = JOptionPane.showConfirmDialog(null, produtos.produto.getDescricao(),
                        "Excluir o produto?", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, produtos.atualizar(ProdutoDAO.EXCLUSAO));
                    limparcampos();
                    tabelaProdutos();
                }
            }
        });

        btLocalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarCampos();
                tabelaProdutos();

            }
        });

        btCons.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame janela = new TelaConsultaProduto();
                janela.setVisible(true);
            }
        });

    }

    public void limparcampos() {
        //textFieldCodigoProduto.setText("");
        textFieldDescricao.setText("");
        textFieldPreco.setText("");
        textFieldUnidade.setText("");
        textFieldQuantidadeInicial.setText("");
        textFieldDataCadastro.setText("");
        textFieldQuantidadeAtual.setText("");        
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizarCampos() {
        produtos.produto.setCodigoProduto(Integer.parseInt(textFieldCodigoProduto.getText()));
        
        if (produtos.localizar()) {
            textFieldCodigoProduto.setText(Integer.toString(produtos.produto.getCodigoProduto()));
            textFieldDescricao.setText(produtos.produto.getDescricao());
            textFieldPreco.setText(Double.toString(produtos.produto.getPreco()));
            textFieldUnidade.setText(produtos.produto.getUnidade());
            textFieldQuantidadeInicial.setText(Double.toString(produtos.produto.getQuantidadeInicial()));
            textFieldDataCadastro.setText(produtos.produto.getDataCadastro());
            textFieldQuantidadeAtual.setText(Double.toString(produtos.produto.getQuantidadeAtual()));
            
            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Produto não econtrado!");
            limparcampos();
        }
    }

    public void tabelaProdutos() {
        try {
            String sql = "Select * from produtos";
            PreparedStatement statement = produtos.objetoConexaoBD.objetoConnection.prepareStatement(sql);
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

            textFieldCodigoProduto.setText(objetoResultSet.getString("codprod"));
            textFieldDescricao.setText(objetoResultSet.getString("descricao"));
            textFieldPreco.setText(objetoResultSet.getString("preco"));
            textFieldUnidade.setText(objetoResultSet.getString("unidade"));
            textFieldQuantidadeInicial.setText(objetoResultSet.getString("qtde_inicial"));
            textFieldDataCadastro.setText(objetoResultSet.getString("data_cad"));
            textFieldQuantidadeAtual.setText(objetoResultSet.getString("qtde_atual"));            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Problemas na conexão!\n" + erro.toString());
        }
    }
}
