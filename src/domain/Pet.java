package domain;

public class Pet {
    private String nome;
    private String tipo;
    private char sexo;
    private String endereco;
    private int idade;
    private String raca;

    public Pet(String nome, String tipo, char sexo, String endereco, int idade, String raca) {
        this.nome = nome;
        this.tipo = tipo;
        this.sexo = sexo;
        this.endereco = endereco;
        this.idade = idade;
        this.raca = raca;
    }


    @Override
    public String toString() {
        return "Pet{" +
                "nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", sexo=" + sexo +
                ", endereco='" + endereco + '\'' +
                ", idade=" + idade +
                ", raca='" + raca + '\'' +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
