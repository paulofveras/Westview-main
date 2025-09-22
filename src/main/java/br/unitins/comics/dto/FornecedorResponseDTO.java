package br.unitins.comics.dto;

import br.unitins.comics.model.Fornecedor;

public record FornecedorResponseDTO (
    Long id,
    String nome, 
    String nomeFantasia,
    String cnpj,
    String email,
    EnderecoResponseDTO endereco, 
    TelefoneResponseDTO telefone
) { 
    public static FornecedorResponseDTO valueOf(Fornecedor fornecedor) {
        return new FornecedorResponseDTO(
            fornecedor.getId(),    
            fornecedor.getNome(),
            fornecedor.getNomeFantasia(),
            fornecedor.getCnpj(),
            fornecedor.getEmail(),
            EnderecoResponseDTO.valueOf(fornecedor.getEndereco()),
            TelefoneResponseDTO.valueOf(fornecedor.getTelefone())
        );
    }
}
