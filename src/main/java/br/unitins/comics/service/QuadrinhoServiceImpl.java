package br.unitins.comics.service;

import java.util.List;

import br.unitins.comics.dto.QuadrinhoDTO;
import br.unitins.comics.dto.QuadrinhoResponseDTO;
import br.unitins.comics.model.Material;
import br.unitins.comics.model.Quadrinho;
import br.unitins.comics.repository.FornecedorRepository;
import br.unitins.comics.repository.QuadrinhoRepository;
import br.unitins.comics.util.PageResult;
import br.unitins.comics.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class QuadrinhoServiceImpl implements QuadrinhoService {

    @Inject
    public QuadrinhoRepository quadrinhoRepository;

    @Inject
    public FornecedorRepository fornecedorRepository;

    @Override
    @Transactional
    public QuadrinhoResponseDTO create(@Valid QuadrinhoDTO dto) {
        validarNomeQuadrinho(dto.nome());
        Quadrinho quadrinho = new Quadrinho();
        quadrinho.setNome(dto.nome());
        quadrinho.setDescricao(dto.descricao());
        quadrinho.setPreco(dto.preco());
        quadrinho.setQuantPaginas(dto.quantPaginas());
        quadrinho.setMaterial(Material.valueOf(dto.id_material()));
        quadrinho.setFornecedor(fornecedorRepository.findById(dto.id_fornecedor()));
        quadrinho.setEstoque(dto.estoque());
        quadrinhoRepository.persist(quadrinho);
        return QuadrinhoResponseDTO.valueOf(quadrinho);
    }

    public void validarNomeQuadrinho(String nome) {
        Quadrinho quadrinho = quadrinhoRepository.findByNomeCompleto(nome);
        if (quadrinho != null)
            throw new ValidationException("nome", "O nome '" + nome + "' já existe.");
    }

    @Override
    @Transactional
    public void update(Long id, QuadrinhoDTO dto) {
        Quadrinho quadrinhoBanco = quadrinhoRepository.findById(id);
        quadrinhoBanco.setNome(dto.nome());
        quadrinhoBanco.setDescricao(dto.descricao());
        quadrinhoBanco.setPreco(dto.preco());
        quadrinhoBanco.setQuantPaginas(dto.quantPaginas());
        quadrinhoBanco.setMaterial(Material.valueOf(dto.id_material()));
        quadrinhoBanco.setFornecedor(fornecedorRepository.findById(dto.id_fornecedor()));
        quadrinhoBanco.setEstoque(dto.estoque());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        quadrinhoRepository.deleteById(id);
    }

    @Override
    public QuadrinhoResponseDTO findById(Long id) {
        return QuadrinhoResponseDTO.valueOf(quadrinhoRepository.findById(id));
    }

    @Override
    public List<QuadrinhoResponseDTO> findAll() {
        return quadrinhoRepository.listAll()
                .stream().map(QuadrinhoResponseDTO::valueOf).toList();
    }

    @Override
    public List<QuadrinhoResponseDTO> findByNome(String nome) {
        return quadrinhoRepository.findByNome(nome)
                .stream().map(QuadrinhoResponseDTO::valueOf).toList();
    }

    // --------- NOVOS MÉTODOS (slide + atividade) ---------

    @Override
    public List<QuadrinhoResponseDTO> findAll(int page, int pageSize) {
        return quadrinhoRepository.findAll()
                .page(page, pageSize)
                .list()
                .stream().map(QuadrinhoResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return quadrinhoRepository.count();
    }

    @Override
    public PageResult<QuadrinhoResponseDTO> findPaged(String q, int page, int pageSize) {
        PanacheQuery<Quadrinho> query = quadrinhoRepository.findByNomeOuDescricao(q);
        long total = quadrinhoRepository.count();                   // total geral
        long filtered = (q == null || q.isBlank()) ? total : query.count();

        var pageData = query.page(page, pageSize).list()
                .stream().map(QuadrinhoResponseDTO::valueOf).toList();

        return new PageResult<>(page, pageSize, total, filtered, pageData);
    }

    @Override
    public long countFiltered(String q) {
        return (q == null || q.isBlank())
                ? quadrinhoRepository.count()
                : quadrinhoRepository.findByNomeOuDescricao(q).count();
    }
}

