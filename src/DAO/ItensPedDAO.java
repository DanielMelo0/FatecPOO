package DAO;
import Model.ItensPed;
import DAO.ConexaoBD;
import java.sql.*;
/**
 *
 * @author William Marques
 */
public class ItensPedDAO {
    public ItensPed itensPed;
    public ConexaoBD objetoConexaoBD;
    private PreparedStatement objetoPreparedStatement;
    private ResultSet objetoResultSet;
    private String mensagem, sql;
    private int numero;
    public static final byte INCLUSAO=1;
    public static final byte ALTERACAO=2;
    public static final byte EXCLUSAO=3;
    
    public ItensPedDAO(){
        objetoConexaoBD = new ConexaoBD();
        itensPed = new ItensPed();
    }  
    
    public boolean localizar(){
        sql = "select * from itens_ped where id = ?" ;
        try{
            objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
            objetoPreparedStatement.setInt(1,itensPed.getId());
            objetoResultSet = objetoPreparedStatement.executeQuery();
            objetoResultSet.next();
            itensPed.setId(Integer.parseInt(objetoResultSet.getString(1)));
            itensPed.setPedidos_CodigoPedido(Integer.parseInt(objetoResultSet.getString(2)));
            itensPed.setProdutos_codigoProduto(Integer.parseInt(objetoResultSet.getString(3)));
            itensPed.setQuantidade(Double.parseDouble(objetoResultSet.getString(4)));                       
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
                sql = "insert into itens_ped (codped,codprod,qtde) values (?,?,?)";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
                //objetoPreparedStatement.setInt(1,(itensPed.getCodigoitensPed()));
                objetoPreparedStatement.setInt(1,itensPed.getPedidos_CodigoPedido());
                objetoPreparedStatement.setInt(2,itensPed.getProdutos_codigoProduto());
                objetoPreparedStatement.setDouble(3,itensPed.getQuantidade());                
            }
            
            else if (operacao==ALTERACAO){
                sql ="update itens_ped set codped=?, codprod=?, qtde=? where id=?";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);                
                objetoPreparedStatement.setInt(1,itensPed.getPedidos_CodigoPedido());
                objetoPreparedStatement.setInt(2,itensPed.getProdutos_codigoProduto());
                objetoPreparedStatement.setDouble(3,itensPed.getQuantidade());
                objetoPreparedStatement.setInt(4,itensPed.getId());
                
            }
            else if (operacao==EXCLUSAO){
                sql ="delete from itens_ped where id = ?";
                objetoPreparedStatement = objetoConexaoBD.objetoConnection.prepareStatement(sql);
                objetoPreparedStatement.setString(1,Integer.toString(itensPed.getId()));
                
            }
            if (objetoPreparedStatement.executeUpdate()==0){
                 mensagem="Falha na operação!!";
            }
        }catch (SQLException erro){
               numero = erro.getErrorCode();
               if (numero==1062)
                   mensagem="Este Codigo de ItensPed já existe!";
               else
               mensagem="Falha na operação"+ erro.toString();
        }
        return mensagem;
    }}

