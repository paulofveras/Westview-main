package br.unitins.comics.service;

import java.util.List;

import br.unitins.comics.dto.FuncionarioDTO;
import br.unitins.comics.dto.FuncionarioResponseDTO;
import br.unitins.comics.dto.UpdatePasswordDTO;
import br.unitins.comics.dto.UpdateUsernameDTO;
import br.unitins.comics.dto.UsuarioResponseDTO;
import br.unitins.comics.model.Endereco;
import br.unitins.comics.model.Funcionario;
import br.unitins.comics.model.Telefone;
import br.unitins.comics.model.Usuario;
import br.unitins.comics.repository.EnderecoRepository;
import br.unitins.comics.repository.FuncionarioRepository;
import br.unitins.comics.repository.TelefoneRepository;
import br.unitins.comics.repository.UsuarioRepository;
import br.unitins.comics.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FuncionarioServiceImpl implements FuncionarioService {

    @Inject
    public FuncionarioRepository funcionarioRepository;
    @Inject
    public EnderecoRepository enderecoRepository;
    @Inject
    public TelefoneRepository telefoneRepository;
    @Inject
    public UsuarioRepository usuarioRepository;
    @Inject
    public HashService hashService;


    @Override
    @Transactional
    public FuncionarioResponseDTO create(@Valid FuncionarioDTO dto) {
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

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());
        funcionario.setCargo(dto.cargo());
        funcionario.setEmail(dto.email());
        funcionario.setUsuario(usuario);
        funcionario.setEndereco(endereco);
        funcionario.setTelefone(telefone);

        funcionarioRepository.persist(funcionario);
        return FuncionarioResponseDTO.valueOf(funcionario);
    }

    public void validarNomeFuncionario(String nome) {
        Funcionario funcionario = funcionarioRepository.findByNomeCompleto(nome);
        if (funcionario != null)
            throw  new ValidationException("nome", "O nome '"+nome+"' já existe.");
    }


     @Override
    @Transactional
    public void update(Long id, FuncionarioDTO dto) {
        Funcionario funcionarioBanco = funcionarioRepository.findById(id);
        if (funcionarioBanco == null) {
            throw new NotFoundException("Funcionário não encontrado.");
        }
        
        funcionarioBanco.setNome(dto.nome());
        funcionarioBanco.setEmail(dto.email());
        funcionarioBanco.setCpf(dto.cpf());
        funcionarioBanco.setCargo(dto.cargo());

        if (dto.endereco() != null) {
            Endereco endereco = funcionarioBanco.getEndereco();
            endereco.setCep(dto.endereco().cep());
            endereco.setRua(dto.endereco().rua());
            endereco.setNumero(dto.endereco().numero());
        }

        if (dto.telefone() != null) {
            Telefone telefone = funcionarioBanco.getTelefone();
            telefone.setCodigoArea(dto.telefone().codigoArea());
            telefone.setNumero(dto.telefone().numero());
        }
    }

     @Override
    @Transactional
    public void updatePassword(Long id, UpdatePasswordDTO dto) {

        Funcionario funcionario = funcionarioRepository.findById(id);
        String hashSenhaAntiga = hashService.getHashSenha(dto.oldPassword());

        if (funcionario != null) {
            if (funcionario.getUsuario().getSenha().equals(hashSenhaAntiga)) {
                String hashNovaSenha = hashService.getHashSenha(dto.newPassword());
                funcionario.getUsuario().setSenha(hashNovaSenha);
            } else {
                throw new ValidationException("ERRO", "Senha antiga nao corresponde");
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional
    public void updateUsername(Long id, UpdateUsernameDTO dto) {

        Funcionario funcionario = funcionarioRepository.findById(id);

        if (funcionario != null) {
            funcionario.getUsuario().setUsername(dto.newUsername());;
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        funcionarioRepository.deleteById(id);
    }

    @Override
    public FuncionarioResponseDTO findById(Long id) {
        return FuncionarioResponseDTO.valueOf(funcionarioRepository.findById(id));
    }

    @Override
    public List<FuncionarioResponseDTO> findAll() {
        return funcionarioRepository
        .listAll()
        .stream()
        .map(e -> FuncionarioResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<FuncionarioResponseDTO> findByNome(String nome) {
        return funcionarioRepository.findByNome(nome).stream()
        .map(e -> FuncionarioResponseDTO.valueOf(e)).toList();
    }


    public UsuarioResponseDTO login(String username, String senha) {
        Funcionario funcionario = funcionarioRepository.findByUsernameAndSenha(username, senha);
        if (funcionario != null){
            return UsuarioResponseDTO.valueOf(funcionario);
        }else{
            return null;
        }
    }


}
