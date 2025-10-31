package br.unitins.comics.service;

import java.util.List;

import br.unitins.comics.dto.UsuarioDTO;
import br.unitins.comics.dto.UsuarioListResponseDTO;
import br.unitins.comics.dto.UsuarioUpdateDTO;
import br.unitins.comics.model.Usuario;
import br.unitins.comics.repository.UsuarioRepository;
import br.unitins.comics.util.PageResult;
import br.unitins.comics.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashService hashService;

    @Override
    @Transactional
    public UsuarioListResponseDTO create(@Valid UsuarioDTO dto) {
        validarUsername(dto.username(), null);

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));

        usuarioRepository.persist(usuario);
        return UsuarioListResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public void update(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new NotFoundException("Usuario nao encontrado.");
        }

        validarUsername(dto.username(), id);
        usuario.setUsername(dto.username());

        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuario.setSenha(hashService.getHashSenha(dto.senha()));
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioListResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new NotFoundException("Usuario nao encontrado.");
        }
        return UsuarioListResponseDTO.valueOf(usuario);
    }

    @Override
    public List<UsuarioListResponseDTO> findAll() {
        return usuarioRepository.listAll().stream()
            .map(UsuarioListResponseDTO::valueOf)
            .toList();
    }

    @Override
    public PageResult<UsuarioListResponseDTO> findPaged(String q, int page, int pageSize) {
        PanacheQuery<Usuario> query = usuarioRepository.findByUsernameLike(q);
        long total = usuarioRepository.count();
        long filtered = (q == null || q.isBlank()) ? total : query.count();

        var pageData = query.page(page, pageSize).list().stream()
            .map(UsuarioListResponseDTO::valueOf)
            .toList();

        return new PageResult<>(page, pageSize, total, filtered, pageData);
    }

    @Override
    public long count() {
        return usuarioRepository.count();
    }

    @Override
    public long countFiltered(String q) {
        return (q == null || q.isBlank())
            ? usuarioRepository.count()
            : usuarioRepository.findByUsernameLike(q).count();
    }

    private void validarUsername(String username, Long ignoreId) {
        Usuario existente = usuarioRepository.findByUsername(username);
        if (existente != null && (ignoreId == null || !existente.getId().equals(ignoreId))) {
            throw new ValidationException("username", "O username '" + username + "' ja esta em uso.");
        }
    }
}

