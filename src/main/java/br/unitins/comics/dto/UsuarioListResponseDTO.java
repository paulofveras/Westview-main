package br.unitins.comics.dto;

import br.unitins.comics.model.Usuario;

public record UsuarioListResponseDTO(
    Long id,
    String username
) {
    public static UsuarioListResponseDTO valueOf(Usuario usuario) {
        return new UsuarioListResponseDTO(
            usuario.getId(),
            usuario.getUsername()
        );
    }
}

