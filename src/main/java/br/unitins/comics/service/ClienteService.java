package br.unitins.comics.service;

import java.util.List;

import br.unitins.comics.dto.ClienteDTO;
import br.unitins.comics.dto.ClienteResponseDTO;
import br.unitins.comics.dto.QuadrinhoResponseDTO;
import br.unitins.comics.dto.UpdatePasswordDTO;
import br.unitins.comics.dto.UpdateUsernameDTO;
import br.unitins.comics.dto.UsuarioResponseDTO;
import br.unitins.comics.util.PageResult;
import jakarta.validation.Valid;

public interface ClienteService {

    public ClienteResponseDTO create(@Valid ClienteDTO dto);

    public void update(Long id, ClienteDTO dto);

    public void updatePassword(Long id, UpdatePasswordDTO dto);

    public void updateUsername(Long id, UpdateUsernameDTO dto);

    public void delete(Long id);

    public ClienteResponseDTO findById(Long id);

    public List<ClienteResponseDTO> findAll();

    public List<ClienteResponseDTO> findByNome(String nome);

    PageResult<ClienteResponseDTO> findPaged(String q, int page, int pageSize);

    long count();

    long countFiltered(String q);

    public UsuarioResponseDTO login(String username, String senha);

    public void adicionarFavorito(Long idCliente, Long idQuadrinho);

    public void removerFavorito(Long idCliente, Long idQuadrinho);

    public List<QuadrinhoResponseDTO> getFavoritos(Long idCliente);

}
