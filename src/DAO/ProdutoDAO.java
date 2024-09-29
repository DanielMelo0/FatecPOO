package DAO;

import Model.Produto;
import DAO.ConexaoBD;
import java.sql.*;

/**
 *
 * @author William Marques
 */
public class ProdutoDAO {
    public Produto produto;
    public ConexaoBD objetoConexaoBD;
    private PreparedStatement objetoPreparedStatement;
    private ResultSet objetoResultSet;
    private String mensagem, sql;
    private int numero;
    public static final byte INCLUSAO=1;
    public static final byte ALTERACAO=2;
    public static final byte EXCLUSAO=3;
    
    public ProdutoDAO(){
        objetoConexaoBD = new ConexaoBD();
        produto = new Produto();
    }
    
    
    
    
    public boolean localizar(){
        sql = "select * from produtos where codprod = ?" ;
        try{
            objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
            objetoPreparedStatement.setInt(1,produto.getCodigoProduto());
            objetoResultSet = objetoPreparedStatement.executeQuery();
            objetoResultSet.next();
            produto.setCodigoProduto(Integer.parseInt(objetoResultSet.getString(1)));
            produto.setDescricao(objetoResultSet.getString(2));
            produto.setPreco(Double.parseDouble(objetoResultSet.getString(3)));
            produto.setUnidade(objetoResultSet.getString(4));
            produto.setQuantidadeInicial(Double.parseDouble(objetoResultSet.getString(5)));
            produto.setDataCadastro(objetoResultSet.getString(6));
            produto.setQuantidadeAtual(Double.parseDouble(objetoResultSet.getString(7)));                        
            return true;
              
        }
        catch (SQLException erro)
        {
            return false;
        }
        
    }
    public String atualizar(int operacao)
    {
        mensagem="Operação realizada com sucesso!!!";
        try{
            if (operacao==INCLUSAO){
                sql = "insert into produtos (descricao,preco,unidade,qtde_inicial,data_cad,qtde_atual) values (?,?,?,?,?,?)";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
                //objetoPreparedStatement.setInt(1,(produto.getCodigoproduto()));
                objetoPreparedStatement.setString(1,produto.getDescricao());
                objetoPreparedStatement.setDouble(2,produto.getPreco());
                objetoPreparedStatement.setString(3,produto.getUnidade());
                objetoPreparedStatement.setDouble(4,produto.getQuantidadeInicial());
                objetoPreparedStatement.setString(5,produto.getDataCadastro());
                objetoPreparedStatement.setDouble(6,produto.getQuantidadeAtual());                
            }
            
            else if (operacao==ALTERACAO){
                sql ="update produtos set descricao=?, preco=?, unidade=?, qtde_inicial=?, data_cada=?, qtde_atual=?  where codprod = ?";
                objetoPreparedStatement.setString(1,produto.getDescricao());
                objetoPreparedStatement.setDouble(2,produto.getPreco());
                objetoPreparedStatement.setString(3,produto.getUnidade());
                objetoPreparedStatement.setDouble(4,produto.getQuantidadeInicial());
                objetoPreparedStatement.setString(5,produto.getDataCadastro());
                objetoPreparedStatement.setDouble(6,produto.getQuantidadeAtual());
                objetoPreparedStatement.setInt(7,(produto.getCodigoProduto()));
                
            }
            else if (operacao==EXCLUSAO){
                sql ="delete from produtos where codprod = ?";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
                objetoPreparedStatement.setString(1,Integer.toString(produto.getCodigoProduto()));
                
            }
            if (objetoPreparedStatement.executeUpdate()==0){
                 mensagem="Falha na operação!!";
            }
        }catch (SQLException erro){
               numero = erro.getErrorCode();
               if (numero==1062)
                   mensagem="Este Codigo de Produto já existe!";
               else
               mensagem="Falha na operação"+ erro.toString();
        }
        return mensagem;
    }}

