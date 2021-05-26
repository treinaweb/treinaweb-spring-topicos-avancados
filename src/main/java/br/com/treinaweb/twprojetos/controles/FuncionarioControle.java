package br.com.treinaweb.twprojetos.controles;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.treinaweb.twprojetos.dto.AlertDTO;
import br.com.treinaweb.twprojetos.entidades.Funcionario;
import br.com.treinaweb.twprojetos.excecoes.FuncionarioEhLiderDeProjeto;
import br.com.treinaweb.twprojetos.servicos.CargoServico;
import br.com.treinaweb.twprojetos.servicos.FuncionarioServico;
import br.com.treinaweb.twprojetos.validadores.FuncionarioValidador;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioControle {

    @Autowired
    private CargoServico cargoServico;

    @Autowired
    private FuncionarioServico funcionarioServico;

    @Autowired
    private FuncionarioValidador funcionarioValidador;

    @InitBinder("funcionario")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(funcionarioValidador);
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("funcionario/home");

        modelAndView.addObject("funcionarios", funcionarioServico.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/detalhes");

        modelAndView.addObject("funcionario", funcionarioServico.buscarPorId(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("funcionario/formulario");

        modelAndView.addObject("funcionario", new Funcionario());
        modelAndView.addObject("cargos", cargoServico.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/formulario");

        modelAndView.addObject("funcionario", funcionarioServico.buscarPorId(id));
        modelAndView.addObject("cargos", cargoServico.buscarTodos());

        return modelAndView;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Funcionario funcionario, BindingResult resultado, ModelMap model, RedirectAttributes attrs) {
        if (resultado.hasErrors()) {
            model.addAttribute("cargos", cargoServico.buscarTodos());

            return "funcionario/formulario";
        }

        funcionarioServico.cadastrar(funcionario);
        attrs.addFlashAttribute("alert", new AlertDTO("Funcionario cadastrado com sucesso!", "alert-success"));

        return "redirect:/funcionarios";
    }

    @PostMapping("/{id}/editar")
    public String editar(@Valid Funcionario funcionario, BindingResult resultado, @PathVariable Long id, ModelMap model, RedirectAttributes attrs) {
        if (resultado.hasErrors()) {
            model.addAttribute("cargos", cargoServico.buscarTodos());

            return "funcionario/formulario";
        }

        funcionarioServico.atualizar(funcionario, id);
        attrs.addFlashAttribute("alert", new AlertDTO("Funcionario editado com sucesso!", "alert-success"));

        return "redirect:/funcionarios";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            funcionarioServico.excluirPorId(id);
            attrs.addFlashAttribute("alert", new AlertDTO("Funcionario excluido com sucesso!", "alert-success"));
        } catch (FuncionarioEhLiderDeProjeto e) {
            attrs.addFlashAttribute("alert", new AlertDTO("Funcionario não pode ser excluido, pois é lider de algum projeto!", "alert-danger"));
        }

        return "redirect:/funcionarios";
    }

}
