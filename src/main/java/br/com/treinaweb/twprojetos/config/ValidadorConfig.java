package br.com.treinaweb.twprojetos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.treinaweb.twprojetos.repositorios.ClienteRepositorio;
import br.com.treinaweb.twprojetos.repositorios.FuncionarioRepositorio;
import br.com.treinaweb.twprojetos.validadores.ClienteValidador;
import br.com.treinaweb.twprojetos.validadores.FuncionarioValidador;
import br.com.treinaweb.twprojetos.validadores.PessoaValidador;

@Configuration
public class ValidadorConfig {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Bean
    public ClienteValidador clienteValidador() {
        return new ClienteValidador(clienteRepositorio);
    }

    @Bean
    public FuncionarioValidador funcionarioValidador() {
        return new FuncionarioValidador(funcionarioRepositorio);
    }

    @Bean
    public PessoaValidador pessoaValidador() {
        return new PessoaValidador();
    }

}
