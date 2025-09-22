package br.unitins.comics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record FornecedorDTO (
    @NotBlank(message = "A razão social não pode ser nula.")
    String nome, 
    
    String nomeFantasia,

    @NotBlank(message = "O CNPJ não pode ser nulo.")
    @Pattern(regexp = "^[0-9]{14}$", message = "O CNPJ deve conter exatamente 14 dígitos.")
    String cnpj,

    String email,
    
    @NotNull(message = "O telefone não pode ser nulo.")
    TelefoneDTO telefone,
    
    @NotNull(message = "O endereço não pode ser nulo.")
    EnderecoDTO endereco
) { }
