package br.unitins.comics.service;

import java.util.List;

import br.unitins.comics.dto.FornecedorDTO;
import br.unitins.comics.dto.FornecedorResponseDTO;
import br.unitins.comics.model.Endereco;
import br.unitins.comics.model.Fornecedor;
import br.unitins.comics.model.Telefone;
import br.unitins.comics.repository.EnderecoRepository;
import br.unitins.comics.repository.FornecedorRepository;
import br.unitins.comics.repository.TelefoneRepository;
import br.unitins.comics.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService {

    @Inject
    public FornecedorRepository fornecedorRepository;
    @Inject
    public EnderecoRepository enderecoRepository;
    @Inject
    public TelefoneRepository telefoneRepository;

    @Override
    @Transactional
    public FornecedorResponseDTO create(@Valid FornecedorDTO dto) {

        Endereco endereco = new Endereco();
        endereco.setCep(dto.endereco().cep());
        endereco.setRua(dto.endereco().rua());
        endereco.setNumero(dto.endereco().numero());

        Telefone telefone = new Telefone();
        telefone.setCodigoArea(dto.telefone().codigoArea());
        telefone.setNumero(dto.telefone().numero());

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.nome());
        fornecedor.setNomeFantasia(dto.nomeFantasia());
        fornecedor.setCnpj(dto.cnpj());
        fornecedor.setEmail(dto.email());
        fornecedor.setEndereco(endereco);
        fornecedor.setTelefone(telefone);

        fornecedorRepository.persist(fornecedor);
        return FornecedorResponseDTO.valueOf(fornecedor);
    }

    public void validarNomeFornecedor(String nome) {
        Fornecedor fornecedor = fornecedorRepository.findByNomeCompleto(nome);
        if (fornecedor != null)
            throw  new ValidationException("nome", "O nome '"+nome+"' já existe.");
    }

    @Override
    @Transactional
    public void update(Long id, FornecedorDTO dto) {
        Fornecedor fornecedorBanco = fornecedorRepository.findById(id);
        if (fornecedorBanco == null) {
            throw new NotFoundException("Fornecedor não encontrado.");
        }
        
        fornecedorBanco.setNome(dto.nome());
        fornecedorBanco.setNomeFantasia(dto.nomeFantasia());
        fornecedorBanco.setCnpj(dto.cnpj());
        fornecedorBanco.setEmail(dto.email());

        if (dto.endereco() != null) {
            Endereco endereco = fornecedorBanco.getEndereco();
            endereco.setCep(dto.endereco().cep());
            endereco.setRua(dto.endereco().rua());
            endereco.setNumero(dto.endereco().numero());
        }

        if (dto.telefone() != null) {
            Telefone telefone = fornecedorBanco.getTelefone();
            telefone.setCodigoArea(dto.telefone().codigoArea());
            telefone.setNumero(dto.telefone().numero());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        fornecedorRepository.deleteById(id);
    }

    @Override
    public FornecedorResponseDTO findById(Long id) {
        return FornecedorResponseDTO.valueOf(fornecedorRepository.findById(id));
    }

    @Override
    public List<FornecedorResponseDTO> findAll() {
        return fornecedorRepository
        .listAll()
        .stream()
        .map(e -> FornecedorResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<FornecedorResponseDTO> findByNome(String nome) {
        return fornecedorRepository.findByNome(nome).stream()
        .map(e -> FornecedorResponseDTO.valueOf(e)).toList();
    }

}
