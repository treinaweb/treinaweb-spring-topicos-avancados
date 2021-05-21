package br.com.treinaweb.twprojetos.excecoes;

import javax.persistence.EntityNotFoundException;

public class ClienteNaoEncontradoException extends EntityNotFoundException {

    public ClienteNaoEncontradoException(Long id) {
        super(String.format("Cliente com o ID %s não encontrado", id));
    }

}
