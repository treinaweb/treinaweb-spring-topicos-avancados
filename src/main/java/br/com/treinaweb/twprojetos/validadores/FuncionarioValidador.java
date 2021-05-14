package br.com.treinaweb.twprojetos.validadores;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.treinaweb.twprojetos.entidades.Funcionario;

public class FuncionarioValidador implements Validator {

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

    }

}
