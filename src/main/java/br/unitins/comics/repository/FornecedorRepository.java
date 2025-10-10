package br.unitins.comics.repository;

import br.unitins.comics.model.Fornecedor;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {
    
    public PanacheQuery<Fornecedor> findByNome(String nome) {
        if (nome == null || nome.isBlank())
            return find("ORDER BY nome");
        return find("UPPER(nome) LIKE ?1 ORDER BY nome", "%" + nome.toUpperCase() + "%");
    }

    public Fornecedor findByNomeCompleto(String nome) {
        return find("UPPER(nome) = ?1",  nome.toUpperCase() ).firstResult();
    }
}
