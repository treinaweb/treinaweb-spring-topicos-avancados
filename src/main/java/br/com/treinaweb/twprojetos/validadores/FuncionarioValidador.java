package br.com.treinaweb.twprojetos.validadores;

import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.treinaweb.twprojetos.entidades.Funcionario;
import br.com.treinaweb.twprojetos.repositorios.FuncionarioRepositorio;

public class FuncionarioValidador implements Validator {

    private FuncionarioRepositorio funcionarioRepositorio;

    public FuncionarioValidador(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Funcionario.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Funcionario funcionario = (Funcionario) target;

        if (funcionario.getDataAdmissao() != null && funcionario.getDataDemissao() != null) {
            if (funcionario.getDataDemissao().isBefore(funcionario.getDataAdmissao())) {
                errors.rejectValue("dataDemissao", "validacao.funcionario.dataAdmissao.posterior.dataDemissao");
            }
        }

        Optional<Funcionario> funcionarioEncontrado = funcionarioRepositorio.findByEmail(funcionario.getEmail());
        if (funcionarioEncontrado.isPresent() && !funcionarioEncontrado.get().equals(funcionario)) {
            errors.rejectValue("email", "validacao.funcionario.email.existente");
        }

        funcionarioEncontrado = funcionarioRepositorio.findByCpf(funcionario.getCpf());
        if (funcionarioEncontrado.isPresent() && !funcionarioEncontrado.get().equals(funcionario)) {
            errors.rejectValue("cpf", "validacao.funcionario.cpf.existente");
        }

    }

}
