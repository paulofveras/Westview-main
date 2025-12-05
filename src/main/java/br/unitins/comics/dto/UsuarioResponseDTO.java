package br.unitins.comics.dto;

import br.unitins.comics.model.Cliente;
import br.unitins.comics.model.Funcionario;
import br.unitins.comics.model.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String username,
    String nome,
    String perfil
) {
    public static UsuarioResponseDTO valueOf(Funcionario func) {
        return new UsuarioResponseDTO(
            func.getId(),
            func.getUsuario().getUsername(),
            func.getNome(),
            "Funcionario"
        );
    }
    
    public static UsuarioResponseDTO valueOf(Cliente cli) {
        return new UsuarioResponseDTO(
            cli.getId(),
            cli.getUsuario().getUsername(),
            cli.getNome(),
            "Cliente"
        );
    }
    
    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getUsername(),
            usuario.getUsername(), // Em caso de usuário puro, usamos username como nome
            "Usuario"
        );
    }
}