package DAO;
import Model.Pedido;
import DAO.ConexaoBD;
import java.sql.*;
/**
 *
 * @author William Marques
 */
public class PedidoDAO {
    public Pedido pedido;
    public ConexaoBD objetoConexaoBD;
    private PreparedStatement objetoPreparedStatement;
    private ResultSet objetoResultSet;
    private String mensagem, sql;
    private int numero;
    public static final byte INCLUSAO=1;
    public static final byte ALTERACAO=2;
    public static final byte EXCLUSAO=3;
    
    public PedidoDAO(){
        objetoConexaoBD = new ConexaoBD();
        pedido = new Pedido();
    }  
    
    public boolean localizar(){
        sql = "select * from pedidos where codped = ?" ;
        try{
            objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
            objetoPreparedStatement.setInt(1,pedido.getCodigoPedido());
            objetoResultSet = objetoPreparedStatement.executeQuery();
            objetoResultSet.next();
            pedido.setCodigoPedido(Integer.parseInt(objetoResultSet.getString(1)));
            pedido.setCliente_codigoCliente(Integer.parseInt(objetoResultSet.getString(2)));
            pedido.setData(objetoResultSet.getString(3));                       
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
                sql = "insert into pedidos (codcliente,data) values (?,?)";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
                //objetoPreparedStatement.setInt(1,(pedido.getCodigopedido()));                
                objetoPreparedStatement.setInt(1,pedido.getCliente_codigoCliente());
                objetoPreparedStatement.setString(2,pedido.getData());                
            }
            
            else if (operacao==ALTERACAO){
                sql ="update pedidos set codcliente=?, data=? where codped=?";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);                
                objetoPreparedStatement.setInt(1,pedido.getCliente_codigoCliente());
                objetoPreparedStatement.setString(2,pedido.getData());                
                objetoPreparedStatement.setInt(3,(pedido.getCodigoPedido()));
                
            }
            else if (operacao==EXCLUSAO){
                sql ="delete from pedidos where codcli = ?";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
                objetoPreparedStatement.setString(1,Integer.toString(pedido.getCodigoPedido()));
                
            }
            if (objetoPreparedStatement.executeUpdate()==0){
                 mensagem="Falha na operação!!";
            }
        }catch (SQLException erro){
               numero = erro.getErrorCode();
               if (numero==1062)
                   mensagem="Este Codigo de Pedido já existe!";
               else
               mensagem="Falha na operação"+ erro.toString();
        }
        return mensagem;
    }}

