package View;

import DAO.ClienteDAO;
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
class TelaCadastroCliente extends JFrame {

    JLabel labelCodigoCliente, labelNome, labelEndereco, labelBairro, labelCidade, labelCEP, labelUF, labelEmail, labelFone, labelCelular, label3, label4;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btVoltarMenuPrincipal, btSair;
    JButton btPrimeiro, btAnterior, btProximo, btUltimo, btCons;
    JPanel painel, painelBotoes, painelREG;
    JFrame janela;
    static JTextField textFieldCodigoCliente, textFieldNome, textFieldEndereco, textFieldBairro, textFieldCidade, textFieldCEP, textFieldUF, textFieldEmail, textFieldFone, textFieldCelular;
    private ClienteDAO clientes;
    private ResultSet objetoResultSet;
    private TelaConsultaCliente consultaClientes;

    public TelaCadastroCliente() {
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

        setTitle("Cadastro de Clientes");
        setSize(750, 600);
        setLocationRelativeTo(null);
        labelCodigoCliente = new JLabel("Código do Cliente: ");
        textFieldCodigoCliente = new JTextField(4);
        labelNome = new JLabel("Nome: ");
        textFieldNome = new JTextField(80);
        labelEndereco = new JLabel("Endereço:");
        textFieldEndereco = new JTextField(80);
        labelBairro = new JLabel("Bairro:");
        textFieldBairro = new JTextField(80);
        labelCidade = new JLabel("Cidade:");
        textFieldCidade = new JTextField(80);
        labelCEP = new JLabel("CEP:");
        textFieldCEP = new JTextField(80);
        labelUF = new JLabel("UF:");
        textFieldUF = new JTextField(80);
        labelEmail = new JLabel("Email:");
        textFieldEmail = new JTextField(80);
        labelFone = new JLabel("Fone:");
        textFieldFone = new JTextField(80);
        labelCelular = new JLabel("Celular:");
        textFieldCelular = new JTextField(80);
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
        painel.add(labelCodigoCliente);
        painel.add(textFieldCodigoCliente);
        painel.add(labelNome);
        painel.add(textFieldNome);
        painel.add(labelEndereco);
        painel.add(textFieldEndereco);
        painel.add(labelBairro);
        painel.add(textFieldBairro);
        painel.add(labelCidade);
        painel.add(textFieldCidade);
        painel.add(labelCEP);
        painel.add(textFieldCEP);
        painel.add(labelUF);
        painel.add(textFieldUF);
        painel.add(labelEmail);
        painel.add(textFieldEmail);
        painel.add(labelFone);
        painel.add(textFieldFone);
        painel.add(labelCelular);
        painel.add(textFieldCelular);
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
        clientes = new ClienteDAO();
        if (!clientes.objetoConexaoBD.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão!");
            System.exit(0);
        }
        tabelaClientes();
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
                clientes.objetoConexaoBD.close();
                System.exit(0);
            }
        });

        btVoltarMenuPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientes.objetoConexaoBD.close();
                //estanciar a tela cliente
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
                textFieldCodigoCliente.setEnabled(false);
                //tfNome.setEnabled(true);
                limparcampos();

                setBotoes(false, false, true, false, false, true);
                textFieldNome.requestFocus();
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldCodigoCliente.setEnabled(true);
                //tfRA.setEnabled(false);
                //tfNome.setEnabled(false);
                limparcampos();
                tabelaClientes();

            }
        });

        btGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (textFieldNome.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O nome não pode ser vazio!");
                    textFieldNome.requestFocus();
                    return;
                }
                clientes.cliente.setNome(textFieldNome.getText());
                clientes.cliente.setEndereco(textFieldEndereco.getText());
                clientes.cliente.setBairro(textFieldBairro.getText());
                clientes.cliente.setCidade(textFieldCidade.getText());
                clientes.cliente.setCep(textFieldCEP.getText());
                clientes.cliente.setUf(textFieldUF.getText());
                clientes.cliente.setEmail(textFieldEmail.getText());
                clientes.cliente.setFone(textFieldFone.getText());
                clientes.cliente.setCelular(textFieldCelular.getText());

                JOptionPane.showMessageDialog(null, clientes.atualizar(ClienteDAO.INCLUSAO));
                textFieldCodigoCliente.setEnabled(true);
                limparcampos();
                tabelaClientes();

            }
        });

        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                clientes.cliente.setNome(textFieldNome.getText());
                clientes.cliente.setEndereco(textFieldEndereco.getText());
                clientes.cliente.setBairro(textFieldBairro.getText());
                clientes.cliente.setCidade(textFieldCidade.getText());
                clientes.cliente.setCep(textFieldCEP.getText());
                clientes.cliente.setUf(textFieldUF.getText());
                clientes.cliente.setEmail(textFieldEmail.getText());
                clientes.cliente.setFone(textFieldFone.getText());
                clientes.cliente.setCelular(textFieldCelular.getText());

                JOptionPane.showMessageDialog(null, clientes.atualizar(ClienteDAO.ALTERACAO));
                limparcampos();
                tabelaClientes();
            }
        });

        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientes.cliente.setCodigocliente(Integer.parseInt(textFieldCodigoCliente.getText()));
                clientes.localizar();
                int n = JOptionPane.showConfirmDialog(null, clientes.cliente.getNome(),
                        "Excluir o cliente?", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, clientes.atualizar(ClienteDAO.EXCLUSAO));
                    limparcampos();
                    tabelaClientes();
                }
            }
        });

        btLocalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarCampos();
                tabelaClientes();

            }
        });

        btCons.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame janela = new TelaConsultaCliente();
                janela.setVisible(true);
            }
        });

    }

    public void limparcampos() {
        //textFieldCodigoCliente.setText("");
        textFieldNome.setText("");
        textFieldEndereco.setText("");
        textFieldBairro.setText("");
        textFieldCidade.setText("");
        textFieldCEP.setText("");
        textFieldUF.setText("");
        textFieldEmail.setText("");
        textFieldFone.setText("");
        textFieldCelular.setText("");
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizarCampos() {
        clientes.cliente.setCodigocliente(Integer.parseInt(textFieldCodigoCliente.getText()));
        //clientes.cliente.setNome(textFieldNome.getText());
        //clientes.cliente.setEndereco(textFieldEndereco.getText());
        //clientes.cliente.setBairro(textFieldBairro.getText());
        //clientes.cliente.setCidade(textFieldCidade.getText());
        //clientes.cliente.setCep(textFieldCEP.getText());
        //clientes.cliente.setUf(textFieldUF.getText());
        //clientes.cliente.setEmail(textFieldEmail.getText());
        //clientes.cliente.setFone(textFieldFone.getText());
        //clientes.cliente.setCelular(textFieldCelular.getText());
        if (clientes.localizar()) {
            textFieldCodigoCliente.setText(Integer.toString(clientes.cliente.getCodigocliente()));
            textFieldNome.setText(clientes.cliente.getNome());
            textFieldEndereco.setText(clientes.cliente.getEndereco());
            textFieldBairro.setText(clientes.cliente.getBairro());
            textFieldCidade.setText(clientes.cliente.getCidade());
            textFieldCEP.setText(clientes.cliente.getCep());
            textFieldUF.setText(clientes.cliente.getUf());
            textFieldEmail.setText(clientes.cliente.getEmail());
            textFieldFone.setText(clientes.cliente.getFone());
            textFieldCelular.setText(clientes.cliente.getCelular());

            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não econtrado!");
            limparcampos();
        }
    }

    public void tabelaClientes() {
        try {
            String sql = "Select * from clientes";
            PreparedStatement statement = clientes.objetoConexaoBD.objetoConnection.prepareStatement(sql);
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

            textFieldCodigoCliente.setText(objetoResultSet.getString("codcli"));
            textFieldNome.setText(objetoResultSet.getString("nome"));
            textFieldEndereco.setText(objetoResultSet.getString("ender"));
            textFieldBairro.setText(objetoResultSet.getString("bairro"));
            textFieldCidade.setText(objetoResultSet.getString("cidade"));
            textFieldCEP.setText(objetoResultSet.getString("cep"));
            textFieldUF.setText(objetoResultSet.getString("uf"));
            textFieldEmail.setText(objetoResultSet.getString("email"));
            textFieldFone.setText(objetoResultSet.getString("fone"));
            textFieldCelular.setText(objetoResultSet.getString("celular"));
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Problemas na conexão!\n" + erro.toString());
        }
    }
}
