package br.unitins.comics.model;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cliente extends DefaultEntity {

    private String nome;
    @OneToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
    @OneToOne
    @JoinColumn(name = "id_telefone")
    private Telefone telefone;
    private String email;
    @OneToOne
    @JoinColumn(name = "id_usuario", unique = true)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "cliente_favoritos_quadrinho",
               joinColumns = @JoinColumn(name = "id_cliente"),
               inverseJoinColumns = @JoinColumn(name = "id_quadrinho"))
    private List<Quadrinho> favoritos;
   

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

        public List<Quadrinho> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Quadrinho> favoritos) {
        this.favoritos = favoritos;
    }

}