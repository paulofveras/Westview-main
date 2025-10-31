package br.unitins.comics.service;

import java.util.List;

import br.unitins.comics.dto.AdministradorDTO;
import br.unitins.comics.dto.AdministradorResponseDTO;
import br.unitins.comics.dto.UpdatePasswordDTO;
import br.unitins.comics.dto.UpdateUsernameDTO;
import br.unitins.comics.dto.UsuarioResponseDTO;
import br.unitins.comics.util.PageResult;
import jakarta.validation.Valid;

public interface AdministradorService {

    AdministradorResponseDTO create(@Valid AdministradorDTO dto);

    void update(Long id, AdministradorDTO dto);

    void updatePassword(Long id, UpdatePasswordDTO dto);

    void updateUsername(Long id, UpdateUsernameDTO dto);

    void delete(Long id);

    AdministradorResponseDTO findById(Long id);

    List<AdministradorResponseDTO> findAll();

    List<AdministradorResponseDTO> findByNome(String nome);

    PageResult<AdministradorResponseDTO> findPaged(String q, int page, int pageSize);

    long count();

    long countFiltered(String q);

    UsuarioResponseDTO login(String username, String senha);
}

