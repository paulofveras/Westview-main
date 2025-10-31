package br.unitins.comics.repository;

import br.unitins.comics.model.Administrador;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdministradorRepository implements PanacheRepository<Administrador> {

    public PanacheQuery<Administrador> queryByNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return find("ORDER BY nome");
        }
        return find("UPPER(nome) LIKE ?1 ORDER BY nome", "%" + nome.toUpperCase() + "%");
    }

    public Administrador findByNomeCompleto(String nome) {
        if (nome == null) {
            return null;
        }
        return find("UPPER(nome) = ?1", nome.toUpperCase()).firstResult();
    }

    public Administrador findByUsernameAndSenha(String username, String senha) {
        return find("usuario.username = ?1 AND usuario.senha = ?2", username, senha).firstResult();
    }

    public Administrador findByUsername(String username) {
        if (username == null) {
            return null;
        }
        return find("UPPER(usuario.username) = ?1", username.toUpperCase()).firstResult();
    }
}

