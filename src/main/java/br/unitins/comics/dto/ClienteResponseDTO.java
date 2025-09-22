package br.unitins.comics.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.comics.model.Cliente;

public record ClienteResponseDTO(
    Long id, 
    String nome, 
    EnderecoResponseDTO endereco, 
    TelefoneResponseDTO telefone,
    String email,
    UsuarioResponseDTO usuario,
    List<QuadrinhoResponseDTO> favoritos
    ) {
    public static ClienteResponseDTO valueOf(Cliente cliente) {
        List<QuadrinhoResponseDTO> favoritos = cliente.getFavoritos() != null ?
            cliente.getFavoritos().stream()
                .map(QuadrinhoResponseDTO::valueOf)
                .collect(Collectors.toList()) : null;
        return new ClienteResponseDTO( 
            cliente.getId(),
            cliente.getNome(),
            EnderecoResponseDTO.valueOf(cliente.getEndereco()),
            TelefoneResponseDTO.valueOf(cliente.getTelefone()),
            cliente.getEmail(),
            cliente.getUsuario() != null ? UsuarioResponseDTO.valueOf(cliente) : null, favoritos
        );
    }
    
}
