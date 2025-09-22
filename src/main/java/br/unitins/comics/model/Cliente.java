package br.unitins.comics.model;


import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cliente extends PessoaFisica {

    @OneToOne
    @JoinColumn(name = "id_usuario", unique = true)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "cliente_favoritos_quadrinho",
               joinColumns = @JoinColumn(name = "id_cliente"),
               inverseJoinColumns = @JoinColumn(name = "id_quadrinho"))
    private List<Quadrinho> favoritos;

    // Getters e Setters
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