package br.unitins.comics.repository;

import br.unitins.comics.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario findByUsername(String username) {
        if (username == null) {
            return null;
        }
        return find("UPPER(username) = ?1", username.toUpperCase()).firstResult();
    }

    public PanacheQuery<Usuario> findByUsernameLike(String username) {
        if (username == null || username.isBlank()) {
            return find("ORDER BY username");
        }
        return find("UPPER(username) LIKE ?1 ORDER BY username", "%" + username.toUpperCase() + "%");
    }
}
