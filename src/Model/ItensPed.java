package Model;

/**
 *
 * @author William Marques
 */
public class ItensPed {
    
    int id;
    int pedidos_CodigoPedido;
    int produtos_codigoProduto;
    double quantidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }  

    public int getPedidos_CodigoPedido() {
        return pedidos_CodigoPedido;
    }

    public void setPedidos_CodigoPedido(int pedidos_CodigoPedido) {
        this.pedidos_CodigoPedido = pedidos_CodigoPedido;
    }

    public int getProdutos_codigoProduto() {
        return produtos_codigoProduto;
    }

    public void setProdutos_codigoProduto(int produtos_codigoProduto) {
        this.produtos_codigoProduto = produtos_codigoProduto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }      
}
