package br.com.treinaweb.twprojetos.controles;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import br.com.treinaweb.twprojetos.validadores.PessoaValidador;

@ControllerAdvice(assignableTypes = {FuncionarioControle.class, ClienteControle.class})
public class PessoaControle {

    @InitBinder(value = {"funcionario", "cliente"})
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new PessoaValidador());
    }

}
