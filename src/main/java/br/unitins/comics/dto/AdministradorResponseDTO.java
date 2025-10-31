package br.unitins.comics.dto;

import br.unitins.comics.model.Administrador;

public record AdministradorResponseDTO(
    Long id,
    String nome,
    String email,
    String cpf,
    String cargo,
    String nivelAcesso,
    EnderecoResponseDTO endereco,
    TelefoneResponseDTO telefone,
    UsuarioResponseDTO usuario
) {
    public static AdministradorResponseDTO valueOf(Administrador administrador) {
        return new AdministradorResponseDTO(
            administrador.getId(),
            administrador.getNome(),
            administrador.getEmail(),
            administrador.getCpf(),
            administrador.getCargo(),
            administrador.getNivelAcesso(),
            EnderecoResponseDTO.valueOf(administrador.getEndereco()),
            TelefoneResponseDTO.valueOf(administrador.getTelefone()),
            UsuarioResponseDTO.valueOf(administrador)
        );
    }
}

