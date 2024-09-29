package DAO;

import Model.Cliente;
import DAO.ConexaoBD;
import java.sql.*;

/**
 *
 * @author William Marques
 */
public class ClienteDAO {
    public Cliente cliente;
    public ConexaoBD objetoConexaoBD;
    private PreparedStatement objetoPreparedStatement;
    private ResultSet objetoResultSet;
    private String mensagem, sql;
    private int numero;
    public static final byte INCLUSAO=1;
    public static final byte ALTERACAO=2;
    public static final byte EXCLUSAO=3;
    
    public ClienteDAO(){
        objetoConexaoBD = new ConexaoBD();
        cliente = new Cliente();
    }
    
    
    
    
    public boolean localizar(){
        sql = "select * from clientes where codcli = ?" ;
        try{
            objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
            objetoPreparedStatement.setInt(1,cliente.getCodigocliente());
            objetoResultSet = objetoPreparedStatement.executeQuery();
            objetoResultSet.next();
            cliente.setCodigocliente(Integer.parseInt(objetoResultSet.getString(1)));
            cliente.setNome(objetoResultSet.getString(2));
            cliente.setEndereco(objetoResultSet.getString(3));
            cliente.setBairro(objetoResultSet.getString(4));
            cliente.setCidade(objetoResultSet.getString(5));
            cliente.setCep(objetoResultSet.getString(6));
            cliente.setUf(objetoResultSet.getString(7));
            cliente.setEmail(objetoResultSet.getString(8));
            cliente.setFone(objetoResultSet.getString(9));
            cliente.setCelular(objetoResultSet.getString(10));            
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
                sql = "insert into clientes (nome,ender,bairro,cidade,cep,uf,email,fone,celular) values (?,?,?,?,?,?,?,?,?)";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
                //objetoPreparedStatement.setInt(1,(cliente.getCodigocliente()));
                objetoPreparedStatement.setString(1,cliente.getNome());
                objetoPreparedStatement.setString(2,cliente.getEndereco());
                objetoPreparedStatement.setString(3,cliente.getBairro());
                objetoPreparedStatement.setString(4,cliente.getCidade());
                objetoPreparedStatement.setString(5,cliente.getCep());
                objetoPreparedStatement.setString(6,cliente.getUf());
                objetoPreparedStatement.setString(7,cliente.getEmail());
                objetoPreparedStatement.setString(8,cliente.getFone());
                objetoPreparedStatement.setString(9,cliente.getCelular());
            }
            
            else if (operacao==ALTERACAO){
                sql ="update clientes set nome=?, ender=?, bairro=?, cidade=?, cep=?, uf=?, email=?, fone=?, celular=?  where codcli = ?";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);                
                objetoPreparedStatement.setString(1,cliente.getNome());
                objetoPreparedStatement.setString(2,cliente.getEndereco());
                objetoPreparedStatement.setString(3,cliente.getBairro());
                objetoPreparedStatement.setString(4,cliente.getCidade());
                objetoPreparedStatement.setString(5,cliente.getCep());
                objetoPreparedStatement.setString(6,cliente.getUf());
                objetoPreparedStatement.setString(7,cliente.getEmail());
                objetoPreparedStatement.setString(8,cliente.getFone());
                objetoPreparedStatement.setString(9,cliente.getCelular());
                objetoPreparedStatement.setInt(10,(cliente.getCodigocliente()));
                
            }
            else if (operacao==EXCLUSAO){
                sql ="delete from clientes where codcli = ?";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
                objetoPreparedStatement.setString(1,Integer.toString(cliente.getCodigocliente()));
                
            }
            if (objetoPreparedStatement.executeUpdate()==0){
                 mensagem="Falha na operação!!";
            }
        }catch (SQLException erro){
               numero = erro.getErrorCode();
               if (numero==1062)
                   mensagem="Este Codigo de Cliente já existe!";
               else
               mensagem="Falha na operação"+ erro.toString();
        }
        return mensagem;
    }}

