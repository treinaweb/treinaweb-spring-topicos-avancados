package br.com.treinaweb.twprojetos.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twprojetos.entidades.Projeto;

public interface ProjetoRepositorio extends JpaRepository<Projeto, Long> {

    @EntityGraph(attributePaths = {"cliente", "lider"})
    List<Projeto> findAll();
    
}
