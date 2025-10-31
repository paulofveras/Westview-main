package br.unitins.comics.service;

import java.util.List;

import br.unitins.comics.dto.UsuarioDTO;
import br.unitins.comics.dto.UsuarioListResponseDTO;
import br.unitins.comics.dto.UsuarioUpdateDTO;
import br.unitins.comics.util.PageResult;
import jakarta.validation.Valid;

public interface UsuarioService {

    UsuarioListResponseDTO create(@Valid UsuarioDTO dto);

    void update(Long id, UsuarioUpdateDTO dto);

    void delete(Long id);

    UsuarioListResponseDTO findById(Long id);

    List<UsuarioListResponseDTO> findAll();

    PageResult<UsuarioListResponseDTO> findPaged(String q, int page, int pageSize);

    long count();

    long countFiltered(String q);
}

