package br.unitins.comics.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
    @NotBlank(message = "O username nao pode ser vazio.")
    String username,

    @NotBlank(message = "A senha nao pode ser vazia.")
    String senha
) { }

