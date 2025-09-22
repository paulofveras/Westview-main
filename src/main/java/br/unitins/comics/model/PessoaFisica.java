package br.unitins.comics.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class PessoaFisica extends Pessoa {

    @Column(nullable = false, unique = true)
    private String cpf;

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
