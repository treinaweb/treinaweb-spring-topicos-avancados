package br.com.treinaweb.twprojetos.controles;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.treinaweb.twprojetos.dto.AlertDTO;
import br.com.treinaweb.twprojetos.dto.AlterarSenhaDTO;
import br.com.treinaweb.twprojetos.entidades.Funcionario;
import br.com.treinaweb.twprojetos.repositorios.FuncionarioRepositorio;

@Controller
public class UsuarioControle {

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public String alterarSenha(AlterarSenhaDTO form, Principal principal, RedirectAttributes attrs) {
        Funcionario usuario = funcionarioRepositorio.findByEmail(principal.getName()).get();

        if (passwordEncoder.matches(form.getSenhaAtual(), usuario.getSenha())) {
            usuario.setSenha(passwordEncoder.encode(form.getNovaSenha()));

            funcionarioRepositorio.save(usuario);

            attrs.addFlashAttribute("alert", new AlertDTO("Senha alterada com sucesso!", "alert-success"));
        } else {
            attrs.addFlashAttribute("alert", new AlertDTO("Senha atual está incorreta!", "alert-danger"));
        }

        return "redirect:/perfil";
    }

}
