package br.unitins.comics.service;

import java.util.List;

import br.unitins.comics.dto.FornecedorDTO;
import br.unitins.comics.dto.FornecedorResponseDTO;
import br.unitins.comics.util.PageResult;
import jakarta.validation.Valid;

public interface FornecedorService {

    public FornecedorResponseDTO create(@Valid FornecedorDTO dto);

    public void update(Long id, FornecedorDTO dto);

    public void delete(Long id);

    public FornecedorResponseDTO findById(Long id);

    // Método adicionado para o formulário de quadrinhos
    public List<FornecedorResponseDTO> findAll();

    // Métodos para paginação
    PageResult<FornecedorResponseDTO> findPaged(String q, int page, int pageSize);

    long count();

    long countFiltered(String q);
}
