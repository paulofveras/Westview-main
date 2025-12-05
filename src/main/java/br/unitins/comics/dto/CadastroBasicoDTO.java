package br.unitins.comics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroBasicoDTO(
    @NotBlank(message = "O nome não pode ser nulo ou vazio")
    @Size(min = 4, max = 60, message = "O tamanho do nome deve ser entre 2 e 60 caracteres.")
    String nome,
    @NotBlank(message = "O CPF é obrigatório")
    String cpf, // <--- NOVO CAMPO
    EnderecoDTO endereco, 
    TelefoneDTO telefone,
    String email,
    String username,
    String senha
) {
    
}
