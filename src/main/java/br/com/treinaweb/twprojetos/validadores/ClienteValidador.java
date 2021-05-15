package br.com.treinaweb.twprojetos.validadores;

import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.treinaweb.twprojetos.entidades.Cliente;
import br.com.treinaweb.twprojetos.repositorios.ClienteRepositorio;

public class ClienteValidador implements Validator {

    private ClienteRepositorio clienteRepositorio;

    public ClienteValidador(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Cliente.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cliente cliente = (Cliente) target;

        Optional<Cliente> clienteEncontrado = clienteRepositorio.findByEmail(cliente.getEmail());
        if (clienteEncontrado.isPresent() && !clienteEncontrado.get().equals(cliente)) {
            errors.rejectValue("email", "validacao.cliente.email.existente");
        }

        clienteEncontrado = clienteRepositorio.findByCpf(cliente.getCpf());
        if (clienteEncontrado.isPresent() && !clienteEncontrado.get().equals(cliente)) {
            errors.rejectValue("cpf", "validacao.cliente.cpf.existente");
        }

    }

}
