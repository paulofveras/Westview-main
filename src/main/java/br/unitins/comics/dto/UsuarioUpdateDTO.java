package br.unitins.comics.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateDTO(
    @NotBlank(message = "O username nao pode ser vazio.")
    String username,
    String senha
) { }

