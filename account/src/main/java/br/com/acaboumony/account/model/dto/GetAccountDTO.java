package br.com.acaboumony.account.model.dto;

import br.com.acaboumony.account.model.entity.Account;

import java.util.UUID;

public record GetAccountDTO (
        UUID uuid,
        String email,
        String nome,
        String telefone,
        String cpf
){
    public GetAccountDTO(Account account){
        this(
                account.getUuid(),
                account.getEmail(),
                account.getNome(),
                account.getTelefone(),
                account.getCpf()
        );

    }
}
