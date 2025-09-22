package br.unitins.comics.dto;

import br.unitins.comics.model.Cliente;
import java.util.List;
import java.util.stream.Collectors;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String email,
    String cpf,
    EnderecoResponseDTO endereco,
    TelefoneResponseDTO telefone,
    UsuarioResponseDTO usuario,
    List<QuadrinhoResponseDTO> favoritos
) {
    public static ClienteResponseDTO valueOf(Cliente cliente) {
        List<QuadrinhoResponseDTO> listaFavoritos = cliente.getFavoritos() != null ?
            cliente.getFavoritos().stream()
                .map(QuadrinhoResponseDTO::valueOf)
                .collect(Collectors.toList()) : null;

        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getCpf(),
            EnderecoResponseDTO.valueOf(cliente.getEndereco()),
            TelefoneResponseDTO.valueOf(cliente.getTelefone()),
            cliente.getUsuario() != null ? UsuarioResponseDTO.valueOf(cliente) : null,
            listaFavoritos
        );
    }
}
