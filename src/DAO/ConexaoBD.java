package DAO;

import java.sql.*;
/**
 *
 * @author luciana
 */
public class ConexaoBD {
    public Connection objetoConnection = null;
    //adcionado CJ a string de conexão por recomendação da IDE Netbeans
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DBNAME = "vendas";
    private final String URL = "jdbc:mysql://localhost:3306/" + DBNAME;
    private final String LOGIN = "root";
    private final String SENHA = "";
    
    
    //método para conexão com banco de dados
    
    public boolean getConnection(){
        try {
            Class.forName(DRIVER);
            objetoConnection = DriverManager.getConnection(URL,LOGIN, SENHA);
            System.out.println("Conectou");
            return (true);
            
        }
        catch (ClassNotFoundException erro) {
            System.out.println("Driver não encontrado "+ erro.toString());
            return (false);
        }
        catch (SQLException erro){
            System.out.println("Falha ao conectar "+ erro.toString());
            return(false);
        }
    }
    
    public void close(){
        try{
            objetoConnection.close();
            System.out.println("Desconectou ");
        }
        catch (SQLException erro){
    }
}
}