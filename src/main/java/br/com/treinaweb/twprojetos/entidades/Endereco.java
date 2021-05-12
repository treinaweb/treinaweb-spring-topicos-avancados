package br.com.treinaweb.twprojetos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.treinaweb.twprojetos.enums.UF;

@Entity
public class Endereco extends Entidade {

    @NotNull
    @Column(nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    private UF uf;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String cidade;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String bairro;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String logradouro;

    @NotNull
    @Size(min = 9, max = 9)
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Deve estar no formato 99999-999")
    @Column(nullable = false, length = 9)
    private String cep;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String numero;

    private String complemento;

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        StringBuilder enderecoCompleto = new StringBuilder();
        enderecoCompleto.append(logradouro)
                        .append(", n° ")
                        .append(numero)
                        .append(", ")
                        .append(complemento)
                        .append(" - ")
                        .append(bairro)
                        .append(". ")
                        .append(uf.getDescricao())
                        .append(" - ")
                        .append(cidade)
                        .append(". CEP: ")
                        .append(cep);

        return enderecoCompleto.toString();
    }

}
