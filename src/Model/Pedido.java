package Model;

/**
 *
 * @author William Marques
 */
public class Pedido {
    
    int codigoPedido;
    int cliente_codigoCliente;
    String data;

    public int getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public int getCliente_codigoCliente() {
        return cliente_codigoCliente;
    }

    public void setCliente_codigoCliente(int cliente_codigoCliente) {
        this.cliente_codigoCliente = cliente_codigoCliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }      
}
