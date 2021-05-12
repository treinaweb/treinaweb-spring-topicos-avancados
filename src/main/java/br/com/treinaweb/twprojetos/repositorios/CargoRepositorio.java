package br.com.treinaweb.twprojetos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twprojetos.entidades.Cargo;

public interface CargoRepositorio extends JpaRepository<Cargo, Long> {
    
}
