package br.unitins.comics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdministradorDTO(
    @NotBlank(message = "O nome nao pode ser nulo.")
    @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres.")
    String nome,

    @NotBlank(message = "O CPF nao pode ser nulo.")
    @Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve conter exatamente 11 digitos.")
    String cpf,

    @NotBlank(message = "O cargo nao pode ser nulo ou vazio.")
    String cargo,

    @NotBlank(message = "O nivel de acesso nao pode ser nulo ou vazio.")
    String nivelAcesso,

    String email,

    @NotBlank(message = "O nome de usuario nao pode ser nulo.")
    String username,

    @NotBlank(message = "A senha nao pode ser nula.")
    String senha,

    @NotNull(message = "O telefone nao pode ser nulo.")
    TelefoneDTO telefone,

    @NotNull(message = "O endereco nao pode ser nulo.")
    EnderecoDTO endereco
) { }
