package br.com.treinaweb.twprojetos.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.treinaweb.twprojetos.entidades.Funcionario;
import br.com.treinaweb.twprojetos.entidades.Projeto;
import br.com.treinaweb.twprojetos.excecoes.FuncionarioEhLiderDeProjeto;
import br.com.treinaweb.twprojetos.excecoes.FuncionarioNaoEncontradoException;
import br.com.treinaweb.twprojetos.repositorios.FuncionarioRepositorio;
import br.com.treinaweb.twprojetos.repositorios.ProjetoRepositorio;

@Service
public class FuncionarioServico {

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private ProjetoRepositorio projetoRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Funcionario> buscarTodos() {
        return funcionarioRepositorio.findAll();
    }

    public List<Funcionario> buscarLideres() {
        return funcionarioRepositorio.findByCargoNome("Gerente");
    }

    public List<Funcionario> buscarEquipe() {
        return funcionarioRepositorio.findByCargoNomeNot("Gerente");
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepositorio.findById(id)
            .orElseThrow(() -> new FuncionarioNaoEncontradoException(id));
    }

    public Funcionario cadastrar(Funcionario funcionario) {
        String senhaEncriptada = passwordEncoder.encode(funcionario.getSenha());

        funcionario.setSenha(senhaEncriptada);

        return funcionarioRepositorio.save(funcionario);
    }

    public Funcionario atualizar(Funcionario funcionario, Long id) {
        Funcionario funcionarioEncontrado = buscarPorId(id);

        funcionario.setSenha(funcionarioEncontrado.getSenha());

        return funcionarioRepositorio.save(funcionario);
    }

    public void excluirPorId(Long id) {
        Funcionario funcionarioEncontrado = buscarPorId(id);

        if (projetoRepositorio.findByLider(funcionarioEncontrado).isEmpty()) {
            if (!funcionarioEncontrado.getProjetos().isEmpty()) {
                for (Projeto projeto : funcionarioEncontrado.getProjetos()) {
                    projeto.getEquipe().remove(funcionarioEncontrado);
                    projetoRepositorio.save(projeto);
                }
            }

            funcionarioRepositorio.delete(funcionarioEncontrado);
        } else {
            throw new FuncionarioEhLiderDeProjeto(id);
        }
    }

}
