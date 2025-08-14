package domain;

import java.util.Set;

public class  Petshop {
    private String nome;
    private Set<Pet> pets;

    public Petshop(String nome, Set<Pet> pets) {
        this.nome = nome;
        this.pets = pets;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }
}
