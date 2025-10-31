package br.unitins.comics.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Administrador extends Funcionario {

    @Column(name = "nivel_acesso")
    private String nivelAcesso;

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}

