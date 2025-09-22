package br.unitins.comics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Funcionario extends PessoaFisica {
    
    private String cargo;

    @OneToOne
    @JoinColumn(name = "id_usuario", unique = true)
    private Usuario usuario;

    // Getters e Setters
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
