package br.com.treinaweb.twprojetos.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.treinaweb.twprojetos.enums.UF;
import br.com.treinaweb.twprojetos.validadores.PessoaValidador;

@ControllerAdvice(assignableTypes = {FuncionarioControle.class, ClienteControle.class})
public class PessoaControle {

    @Autowired
    private PessoaValidador pessoaValidador;

    @InitBinder(value = {"funcionario", "cliente"})
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(pessoaValidador);
    }

    @ModelAttribute("ufs")
    public UF[] getUfs() {
        return UF.values();
    }

}
