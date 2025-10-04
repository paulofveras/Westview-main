package br.unitins.comics.repository;

import java.util.List;

import br.unitins.comics.model.Quadrinho;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuadrinhoRepository implements PanacheRepository<Quadrinho> {

    public List<Quadrinho> findByNome(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

    public Quadrinho findByNomeCompleto(String nome) {
        return find("UPPER(nome) = ?1", nome.toUpperCase()).firstResult();
    }

    // >>> NOVO: método filtrável e paginável (nome OU descricao)
    public PanacheQuery<Quadrinho> findByNomeOuDescricao(String q) {
        if (q == null || q.trim().isEmpty()) {
            return findAll();
        }
        String like = "%" + q.trim().toUpperCase() + "%";
        return find("UPPER(nome) LIKE ?1 OR UPPER(descricao) LIKE ?1", like);
    }
}

