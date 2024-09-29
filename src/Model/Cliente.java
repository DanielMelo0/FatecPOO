package Model;
/**
 *
 * @author William Marques
 */
public class Cliente {
    private int codigoCliente;
    private String nome;
    private String endereco;
    private String bairro;
    private String cidade;
    private String cep;
    private String uf;
    private String email;
    private String fone;
    private String celular;
    
    //Getters

    public int getCodigocliente() {
        return codigoCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getCep() {
        return cep;
    }

    public String getUf() {
        return uf;
    }

    public String getEmail() {
        return email;
    }

    public String getFone() {
        return fone;
    }

    public String getCelular() {
        return celular;
    }
    
    //Setters

    public void setCodigocliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }    
}
