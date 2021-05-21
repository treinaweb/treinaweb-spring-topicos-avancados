package br.com.treinaweb.twprojetos.excecoes;

public class FuncionarioEhLiderDeProjeto extends RuntimeException {

    public FuncionarioEhLiderDeProjeto(Long id) {
        super(String.format("Funcionario com ID %d é lider de projeto(s)", id));
    }

}
