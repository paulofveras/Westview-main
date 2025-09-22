package br.unitins.comics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteDTO (
    @NotBlank(message = "O nome não pode ser nulo.")
    @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres.")
    String nome,
    
    @NotBlank(message = "O CPF não pode ser nulo.")
    @Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve conter exatamente 11 dígitos.")
    String cpf,
    
    String email,

    @NotBlank(message = "O nome de usuário não pode ser nulo.")
    String username,

    @NotBlank(message = "A senha não pode ser nula.")
    String senha,

    @NotNull(message = "O telefone não pode ser nulo.")
    TelefoneDTO telefone,

    @NotNull(message = "O endereço não pode ser nulo.")
    EnderecoDTO endereco
) { }

