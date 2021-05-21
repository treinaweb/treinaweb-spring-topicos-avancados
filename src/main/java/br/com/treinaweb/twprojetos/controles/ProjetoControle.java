package br.com.treinaweb.twprojetos.controles;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.treinaweb.twprojetos.dto.AlertDTO;
import br.com.treinaweb.twprojetos.entidades.Projeto;
import br.com.treinaweb.twprojetos.servicos.ClienteServico;
import br.com.treinaweb.twprojetos.servicos.FuncionarioServico;
import br.com.treinaweb.twprojetos.servicos.ProjetoServico;

@Controller
@RequestMapping("/projetos")
public class ProjetoControle {

    @Autowired
    private ProjetoServico projetoServico;

    @Autowired
    private ClienteServico clienteServico;

    @Autowired
    private FuncionarioServico funcionarioServico;

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("projeto/home");

        modelAndView.addObject("projetos", projetoServico.buscarTodos());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projeto/detalhes");

        modelAndView.addObject("projeto", projetoServico.buscarPorId(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("projeto/formulario");

        modelAndView.addObject("projeto", new Projeto());
        popularFormulario(modelAndView);

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projeto/formulario");

        modelAndView.addObject("projeto", projetoServico.buscarPorId(id));
        popularFormulario(modelAndView);

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String salvar(@Valid Projeto projeto, BindingResult resultado, ModelMap model, RedirectAttributes attrs, @PathVariable(required = false) Long id) {
        if (resultado.hasErrors()) {
            popularFormulario(model);

            return "projeto/formulario";
        }

        if (projeto.getId() == null) {
            projetoServico.cadastrar(projeto);
            attrs.addFlashAttribute("alert", new AlertDTO("Projeto cadastrado com sucesso!", "alert-success"));
        } else {
            projetoServico.atualizar(projeto, id);
            attrs.addFlashAttribute("alert", new AlertDTO("Projeto editado com sucesso!", "alert-success"));
        }

        return "redirect:/projetos";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {
        projetoServico.excluirPorId(id);
        attrs.addFlashAttribute("alert", new AlertDTO("Projeto excluido com sucesso!", "alert-success"));

        return "redirect:/projetos";
    }

    private void popularFormulario(ModelAndView modelAndView) {
        modelAndView.addObject("clientes", clienteServico.buscarTodos());
        modelAndView.addObject("lideres", funcionarioServico.buscarLideres());
        modelAndView.addObject("funcionarios", funcionarioServico.buscarEquipe());
    }

    private void popularFormulario(ModelMap model) {
        model.addAttribute("clientes", clienteServico.buscarTodos());
        model.addAttribute("lideres", funcionarioServico.buscarLideres());
        model.addAttribute("funcionarios", funcionarioServico.buscarEquipe());
    }

}
