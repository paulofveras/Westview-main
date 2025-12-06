package br.unitins.comics.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.comics.dto.ClienteDTO;
import br.unitins.comics.dto.ClienteResponseDTO;
import br.unitins.comics.dto.UpdatePasswordDTO;
import br.unitins.comics.dto.UpdateUsernameDTO;
import br.unitins.comics.dto.UsuarioResponseDTO;
import br.unitins.comics.model.Cliente;
import br.unitins.comics.model.Endereco;
import br.unitins.comics.model.Usuario;
import br.unitins.comics.repository.ClienteRepository;
import br.unitins.comics.repository.EnderecoRepository;
import br.unitins.comics.repository.QuadrinhoRepository;
import br.unitins.comics.repository.TelefoneRepository;
import br.unitins.comics.repository.UsuarioRepository;
import br.unitins.comics.util.PageResult;
import br.unitins.comics.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import br.unitins.comics.dto.QuadrinhoResponseDTO;
import br.unitins.comics.model.Quadrinho;
import br.unitins.comics.model.Telefone;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    public ClienteRepository clienteRepository;
    @Inject
    public EnderecoRepository enderecoRepository;
    @Inject
    public TelefoneRepository telefoneRepository;
    @Inject
    public UsuarioRepository usuarioRepository;
    @Inject
    public HashService hashService;
    @Inject
    public QuadrinhoRepository quadrinhoRepository;


   @Override
@Transactional
public ClienteResponseDTO create(@Valid ClienteDTO dto) {
    
    Usuario usuario = new Usuario();
    usuario.setUsername(dto.username());
    usuario.setSenha(hashService.getHashSenha(dto.senha()));

    usuarioRepository.persist(usuario);

    Endereco endereco = new Endereco();
    endereco.setCep(dto.endereco().cep());
    endereco.setRua(dto.endereco().rua());
    endereco.setNumero(dto.endereco().numero());
    // O EnderecoRepository não é mais necessário aqui, pois o Endereco será salvo em cascata com o Cliente.

    Telefone telefone = new Telefone();
    telefone.setCodigoArea(dto.telefone().codigoArea());
    telefone.setNumero(dto.telefone().numero());
    // O TelefoneRepository não é mais necessário aqui.

    Cliente cliente = new Cliente();
    cliente.setNome(dto.nome());
    cliente.setCpf(dto.cpf());
    cliente.setEmail(dto.email());
    cliente.setUsuario(usuario);
    cliente.setEndereco(endereco);
    cliente.setTelefone(telefone);

    clienteRepository.persist(cliente);
    return ClienteResponseDTO.valueOf(cliente);
}

    public void validarNomeCliente(String nome) {
        Cliente cliente = clienteRepository.findByNomeCompleto(nome);
        if (cliente != null)
            throw  new ValidationException("nome", "O nome '"+nome+"' já existe.");
    }

@Override
@Transactional
public void update(Long id, ClienteDTO dto) {
    // 1. Encontre o cliente existente no banco de dados.
    Cliente clienteBanco = clienteRepository.findById(id);

    if (clienteBanco == null) {
        throw new NotFoundException("Cliente não encontrado.");
    }
    
    // 2. Atualize os dados da pessoa física.
    clienteBanco.setNome(dto.nome());
    clienteBanco.setEmail(dto.email());
    clienteBanco.setCpf(dto.cpf()); // Adicionado o CPF que agora faz parte do DTO

    // 3. Atualize os dados do endereço diretamente no objeto existente.
    if (dto.endereco() != null) {
        Endereco endereco = clienteBanco.getEndereco();
        endereco.setCep(dto.endereco().cep());
        endereco.setRua(dto.endereco().rua());
        endereco.setNumero(dto.endereco().numero());
    }

    // 4. Atualize os dados do telefone diretamente no objeto existente.
    if (dto.telefone() != null) {
        Telefone telefone = clienteBanco.getTelefone();
        telefone.setCodigoArea(dto.telefone().codigoArea());
        telefone.setNumero(dto.telefone().numero());
    }
    
    // Não é necessário chamar clienteRepository.persist(clienteBanco);
    // O Hibernate gerencia a atualização da entidade dentro de uma transação.
}

    @Override
    @Transactional
    public void updatePassword(Long id, UpdatePasswordDTO dto) {

        Cliente cliente = clienteRepository.findById(id);
        String hashSenhaAntiga = hashService.getHashSenha(dto.oldPassword());

        if (cliente != null) {
            if (cliente.getUsuario().getSenha().equals(hashSenhaAntiga)) {
                String hashNovaSenha = hashService.getHashSenha(dto.newPassword());
                cliente.getUsuario().setSenha(hashNovaSenha);
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

        Cliente cliente = clienteRepository.findById(id);

        if (cliente != null) {
            cliente.getUsuario().setUsername(dto.newUsername());;
        } else {
            throw new NotFoundException();
        }
    }


@Override
    @Transactional
    public void delete(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            // Captura o erro de chave estrangeira (ConstraintViolation)
            throw new ValidationException("id", "Não é possível excluir este cliente pois ele possui pedidos ou outros registros vinculados.");
        }
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            // Em vez de retornar null e causar erro depois, lançamos 404 agora
            throw new NotFoundException("Cliente não encontrado.");
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public List<ClienteResponseDTO> findAll() {
        return clienteRepository
        .listAll()
        .stream()
        .map(e -> ClienteResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<ClienteResponseDTO> findByNome(String nome) {
        return clienteRepository.findByNome(nome).stream()
        .map(e -> ClienteResponseDTO.valueOf(e)).toList();
    }

    @Override
    public PageResult<ClienteResponseDTO> findPaged(String q, int page, int pageSize) {
        PanacheQuery<Cliente> query = clienteRepository.queryByNome(q);
        long total = clienteRepository.count();
        long filtered = (q == null || q.isBlank()) ? total : query.count();

        var pageData = query.page(page, pageSize).list().stream()
            .map(ClienteResponseDTO::valueOf)
            .toList();

        return new PageResult<>(page, pageSize, total, filtered, pageData);
    }

    @Override
    public long count() {
        return clienteRepository.count();
    }

    @Override
    public long countFiltered(String q) {
        return (q == null || q.isBlank())
            ? clienteRepository.count()
            : clienteRepository.queryByNome(q).count();
    }

    public UsuarioResponseDTO login(String username, String senha) {
        Cliente cliente = clienteRepository.findByUsernameAndSenha(username, senha);

        if (cliente !=null){
            return UsuarioResponseDTO.valueOf(cliente);
        }else{
            return null;
        }

    }

    @Override
    @Transactional
    public void adicionarFavorito(Long idCliente, Long idQuadrinho) {
        Cliente cliente = clienteRepository.findById(idCliente);
        Quadrinho quadrinho = quadrinhoRepository.findById(idQuadrinho);
        if (cliente != null && quadrinho != null) {
            cliente.getFavoritos().add(quadrinho);
        } else {
            throw new NotFoundException("Cliente ou Quadrinho não encontrado.");
        }
    }

    @Override
    @Transactional
    public void removerFavorito(Long idCliente, Long idQuadrinho) {
        Cliente cliente = clienteRepository.findById(idCliente);
        Quadrinho quadrinho = quadrinhoRepository.findById(idQuadrinho);
        if (cliente != null && quadrinho != null) {
            cliente.getFavoritos().remove(quadrinho);
        } else {
            throw new NotFoundException("Cliente ou Quadrinho não encontrado.");
        }
    }

    @Override
    public List<QuadrinhoResponseDTO> getFavoritos(Long idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente);
        if (cliente != null) {
            return cliente.getFavoritos().stream()
                .map(QuadrinhoResponseDTO::valueOf)
                .collect(Collectors.toList());
        }
        throw new NotFoundException("Cliente não encontrado.");
    }

}
