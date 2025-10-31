package br.unitins.comics.service;

import java.util.List;

import br.unitins.comics.dto.AdministradorDTO;
import br.unitins.comics.dto.AdministradorResponseDTO;
import br.unitins.comics.dto.UpdatePasswordDTO;
import br.unitins.comics.dto.UpdateUsernameDTO;
import br.unitins.comics.dto.UsuarioResponseDTO;
import br.unitins.comics.model.Administrador;
import br.unitins.comics.model.Endereco;
import br.unitins.comics.model.Telefone;
import br.unitins.comics.model.Usuario;
import br.unitins.comics.repository.AdministradorRepository;
import br.unitins.comics.repository.EnderecoRepository;
import br.unitins.comics.repository.TelefoneRepository;
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
public class AdministradorServiceImpl implements AdministradorService {

    @Inject
    AdministradorRepository administradorRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashService hashService;

    @Override
    @Transactional
    public AdministradorResponseDTO create(@Valid AdministradorDTO dto) {
        validarUsername(dto.username(), null);

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuarioRepository.persist(usuario);

        Endereco endereco = new Endereco();
        endereco.setCep(dto.endereco().cep());
        endereco.setRua(dto.endereco().rua());
        endereco.setNumero(dto.endereco().numero());

        Telefone telefone = new Telefone();
        telefone.setCodigoArea(dto.telefone().codigoArea());
        telefone.setNumero(dto.telefone().numero());

        Administrador administrador = new Administrador();
        administrador.setNome(dto.nome());
        administrador.setCpf(dto.cpf());
        administrador.setCargo(obterCargo(dto));
        administrador.setNivelAcesso(dto.nivelAcesso());
        administrador.setEmail(dto.email());
        administrador.setUsuario(usuario);
        administrador.setEndereco(endereco);
        administrador.setTelefone(telefone);

        administradorRepository.persist(administrador);
        return AdministradorResponseDTO.valueOf(administrador);
    }

    private String obterCargo(AdministradorDTO dto) {
        return (dto.cargo() == null || dto.cargo().isBlank()) ? "Administrador" : dto.cargo();
    }

    private void validarUsername(String username, Long ignoreId) {
        Usuario existente = usuarioRepository.findByUsername(username);
        if (existente != null && (ignoreId == null || !existente.getId().equals(ignoreId))) {
            throw new ValidationException("username", "O username '" + username + "' ja esta em uso.");
        }
    }

    @Override
    @Transactional
    public void update(Long id, AdministradorDTO dto) {
        Administrador administradorBanco = administradorRepository.findById(id);
        if (administradorBanco == null) {
            throw new NotFoundException("Administrador nao encontrado.");
        }

        validarUsername(dto.username(), administradorBanco.getUsuario().getId());

        administradorBanco.setNome(dto.nome());
        administradorBanco.setEmail(dto.email());
        administradorBanco.setCpf(dto.cpf());
        administradorBanco.setCargo(obterCargo(dto));
        administradorBanco.setNivelAcesso(dto.nivelAcesso());

        if (dto.endereco() != null) {
            Endereco endereco = administradorBanco.getEndereco();
            endereco.setCep(dto.endereco().cep());
            endereco.setRua(dto.endereco().rua());
            endereco.setNumero(dto.endereco().numero());
        }

        if (dto.telefone() != null) {
            Telefone telefone = administradorBanco.getTelefone();
            telefone.setCodigoArea(dto.telefone().codigoArea());
            telefone.setNumero(dto.telefone().numero());
        }
    }

    @Override
    @Transactional
    public void updatePassword(Long id, UpdatePasswordDTO dto) {
        Administrador administrador = administradorRepository.findById(id);
        if (administrador == null) {
            throw new NotFoundException();
        }

        String hashSenhaAntiga = hashService.getHashSenha(dto.oldPassword());
        if (!administrador.getUsuario().getSenha().equals(hashSenhaAntiga)) {
            throw new ValidationException("senha", "Senha antiga nao confere.");
        }

        administrador.getUsuario().setSenha(hashService.getHashSenha(dto.newPassword()));
    }

    @Override
    @Transactional
    public void updateUsername(Long id, UpdateUsernameDTO dto) {
        Administrador administrador = administradorRepository.findById(id);
        if (administrador == null) {
            throw new NotFoundException();
        }

        validarUsername(dto.newUsername(), administrador.getUsuario().getId());
        administrador.getUsuario().setUsername(dto.newUsername());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        administradorRepository.deleteById(id);
    }

    @Override
    public AdministradorResponseDTO findById(Long id) {
        Administrador administrador = administradorRepository.findById(id);
        if (administrador == null) {
            throw new NotFoundException("Administrador nao encontrado.");
        }
        return AdministradorResponseDTO.valueOf(administrador);
    }

    @Override
    public List<AdministradorResponseDTO> findAll() {
        return administradorRepository.listAll().stream()
            .map(AdministradorResponseDTO::valueOf)
            .toList();
    }

    @Override
    public List<AdministradorResponseDTO> findByNome(String nome) {
        return administradorRepository.queryByNome(nome).list().stream()
            .map(AdministradorResponseDTO::valueOf)
            .toList();
    }

    @Override
    public PageResult<AdministradorResponseDTO> findPaged(String q, int page, int pageSize) {
        PanacheQuery<Administrador> query = administradorRepository.queryByNome(q);
        long total = administradorRepository.count();
        long filtered = (q == null || q.isBlank()) ? total : query.count();

        var pageData = query.page(page, pageSize).list().stream()
            .map(AdministradorResponseDTO::valueOf)
            .toList();

        return new PageResult<>(page, pageSize, total, filtered, pageData);
    }

    @Override
    public long count() {
        return administradorRepository.count();
    }

    @Override
    public long countFiltered(String q) {
        return (q == null || q.isBlank())
            ? administradorRepository.count()
            : administradorRepository.queryByNome(q).count();
    }

    @Override
    public UsuarioResponseDTO login(String username, String senha) {
        Administrador administrador = administradorRepository.findByUsernameAndSenha(username, senha);
        if (administrador == null) {
            return null;
        }
        return UsuarioResponseDTO.valueOf(administrador);
    }
}

