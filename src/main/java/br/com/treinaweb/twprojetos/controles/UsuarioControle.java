package br.com.treinaweb.twprojetos.controles;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.twprojetos.dto.AlterarSenhaDTO;
import br.com.treinaweb.twprojetos.entidades.Funcionario;
import br.com.treinaweb.twprojetos.repositorios.FuncionarioRepositorio;
import br.com.treinaweb.twprojetos.utils.SenhaUtils;

@Controller
public class UsuarioControle {

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @GetMapping("/login")
    public String login() {
        return "usuario/login";
    }

    @GetMapping("/perfil")
    public ModelAndView perfil(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("usuario/perfil");

        Funcionario usuario = funcionarioRepositorio.findByEmail(principal.getName()).get();
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("alterarSenhaForm", new AlterarSenhaDTO());

        return modelAndView;
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(AlterarSenhaDTO form, Principal principal) {
        Funcionario usuario = funcionarioRepositorio.findByEmail(principal.getName()).get();

        if (SenhaUtils.matches(form.getSenhaAtual(), usuario.getSenha())) {
            usuario.setSenha(SenhaUtils.encode(form.getNovaSenha()));

            funcionarioRepositorio.save(usuario);
        }

        return "redirect:/perfil";
    }

}
