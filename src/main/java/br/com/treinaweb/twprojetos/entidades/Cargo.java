package br.com.treinaweb.twprojetos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Cargo extends Entidade {

    @Column(nullable = false, length = 40, unique = true)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
