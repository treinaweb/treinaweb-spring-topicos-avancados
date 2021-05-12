package br.com.treinaweb.twprojetos.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.twprojetos.entidades.Cargo;
import br.com.treinaweb.twprojetos.repositorios.CargoRepositorio;

@Controller
@RequestMapping("/cargos")
public class CargoControle {

    @Autowired
    private CargoRepositorio cargoRepositorio;

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("cargo/home");

        modelAndView.addObject("cargos", cargoRepositorio.findAll());

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("cargo/formulario");

        modelAndView.addObject("cargo", new Cargo());

        return modelAndView;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cargo/formulario");

        modelAndView.addObject("cargo", cargoRepositorio.getOne(id));

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String salvar(Cargo cargo) {
        cargoRepositorio.save(cargo);

        return "redirect:/cargos";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        cargoRepositorio.deleteById(id);

        return "redirect:/cargos";
    }
    
}
