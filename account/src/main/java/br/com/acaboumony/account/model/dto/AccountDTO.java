package br.com.acaboumony.account.model.dto;

public record AccountDTO(
        String email,
        String senha,
        String nome,
        String telefone,
        String cpf
) {
}
