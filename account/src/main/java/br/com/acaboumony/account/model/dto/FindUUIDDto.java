package br.com.acaboumony.account.model.dto;

import br.com.acaboumony.account.model.entity.Account;

public record FindUUIDDto (
        String uuid
){
    public FindUUIDDto(Account account){
        this(
                account.getUuid().toString()
        );
    }
}
