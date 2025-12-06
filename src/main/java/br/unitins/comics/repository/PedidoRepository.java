package br.unitins.comics.repository;

import java.util.List;

import br.unitins.comics.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public List<Pedido> findByCliente(Long idCliente) {
        return find("cliente.id", idCliente).list();
    }

    // --- MÉTODO INTELIGENTE DE BUSCA ---
    public PanacheQuery<Pedido> findByKeyword(String keyword, Sort sort) {
        if (keyword == null || keyword.isBlank()) {
            return findAll(sort);
        }
        
        String likeQuery = "%" + keyword.toUpperCase() + "%";
        
        // Tenta converter para número para buscar por ID
        try {
            Long id = Long.parseLong(keyword);
            // Busca por ID, ou Nome, ou CPF
            return find("(id = ?1 OR UPPER(cliente.nome) LIKE ?2 OR cliente.cpf LIKE ?2)", sort, id, likeQuery);
        } catch (NumberFormatException e) {
            // Se não for número, busca só por Nome ou CPF
            return find("(UPPER(cliente.nome) LIKE ?1 OR cliente.cpf LIKE ?1)", sort, likeQuery);
        }
    }
}