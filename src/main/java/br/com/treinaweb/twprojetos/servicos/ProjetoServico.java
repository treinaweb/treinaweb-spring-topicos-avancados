package br.com.treinaweb.twprojetos.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.treinaweb.twprojetos.entidades.Projeto;
import br.com.treinaweb.twprojetos.excecoes.ProjetoNaoEncontradoException;
import br.com.treinaweb.twprojetos.repositorios.ProjetoRepositorio;

@Service
public class ProjetoServico {

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    public List<Projeto> buscarTodos() {
        return projetoRepositorio.findAll();
    }

    public Projeto buscarPorId(Long id) {
        return projetoRepositorio.findById(id)
            .orElseThrow(() -> new ProjetoNaoEncontradoException(id));
    }

    public Projeto cadastrar(Projeto projeto) {
        return projetoRepositorio.save(projeto);
    }

    public Projeto atualizar(Projeto projeto, Long id) {
        buscarPorId(id);

        return projetoRepositorio.save(projeto);
    }

    public void excluirPorId(Long id) {
        Projeto projetoEncontrado = buscarPorId(id);

        projetoRepositorio.delete(projetoEncontrado);
    }

}
