package br.unitins.comics.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class PessoaJuridica extends Pessoa {

    @Column(nullable = false, unique = true)
    private String cnpj;

    private String nomeFantasia;

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
}
