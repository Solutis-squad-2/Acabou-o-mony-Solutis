package br.com.acaboumony.account;// src/test/java/br/com/acaboumony/account/model/entity/AccountUUIDGenerationTest.java

import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountUUIDGenerationTest {

    @Test
    void GenerateUUIIDAccountCreation() {
        // Cria um AccountDTO com valores válidos para criação da Account
        AccountDTO accountDTO = new AccountDTO(
                "teste@domain.com",
                "senha123",
                "Nome Teste",
                "11987654321",
                "12345678909"
        );

        // Cria uma nova instância de Account
        Account account = new Account(accountDTO);

        // Verifica se o UUID foi gerado
        assertNotNull(account.getUuid(), "UUID should not be null");

        // Verifica se o UUID gerado tem o formato correto
        assertDoesNotThrow(() -> UUID.fromString(account.getUuid().toString()),
                "UUID should have the correct format");

        System.out.println("Generated UUID: " + account.getUuid());
    }
}
