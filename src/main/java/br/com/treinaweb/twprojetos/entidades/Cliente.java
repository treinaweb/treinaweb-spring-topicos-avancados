package br.com.treinaweb.twprojetos.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Cliente extends Pessoa {

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Projeto> projetos;

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }
    
}
