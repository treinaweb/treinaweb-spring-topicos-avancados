package br.com.treinaweb.twprojetos.excecoes;

import javax.persistence.EntityNotFoundException;

public class FuncionarioNaoEncontradoException extends EntityNotFoundException {

    public FuncionarioNaoEncontradoException(Long id) {
        super(String.format("Funcionario com o ID %s não encontrado", id));
    }

}
