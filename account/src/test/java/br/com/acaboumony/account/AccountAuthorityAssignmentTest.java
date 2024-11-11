package br.com.acaboumony.account;// src/test/java/br/com/acaboumony/account/model/entity/AccountAuthorityAssignmentTest.java

import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class AccountAuthorityAssignmentTest {

    @Test
    void efaultRoleUser() {
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

        // Obtém as autoridades atribuídas
        Collection<? extends GrantedAuthority> authorities = account.getAuthorities();

        // Verifica se a autoridade padrão ROLE_USER está presente
        assertNotNull(authorities, "Authorities should not be null");
        assertTrue(
                authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")),
                "Default role ROLE_USER should be assigned"
        );

        System.out.println("Assigned Authorities: " + authorities);
    }
}
