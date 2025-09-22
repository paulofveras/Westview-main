package br.unitins.comics.dto;

import br.unitins.comics.model.Funcionario;

public record FuncionarioResponseDTO (
    Long id,
    String nome,
    String email,
    String cpf,
    String cargo, 
    EnderecoResponseDTO endereco, 
    TelefoneResponseDTO telefone, 
    UsuarioResponseDTO usuario
) { 
    public static FuncionarioResponseDTO valueOf(Funcionario funcionario) {
        return new FuncionarioResponseDTO(
            funcionario.getId(),
            funcionario.getNome(),
            funcionario.getEmail(),
            funcionario.getCpf(),
            funcionario.getCargo(),
            EnderecoResponseDTO.valueOf(funcionario.getEndereco()),
            TelefoneResponseDTO.valueOf(funcionario.getTelefone()),
            UsuarioResponseDTO.valueOf(funcionario)
        );
    }
}
