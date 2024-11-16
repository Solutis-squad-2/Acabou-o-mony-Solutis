package br.com.acaboumony.account; // src/test/java/br/com/acaboumony/account/model/entity/AccountCpfValidationTest.java

import br.com.acaboumony.account.exception.AccountException;
import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountCpfValidationTest {

    @Test
    void ExceptionCpfIsNull() {
        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", "Nome Teste", "11987654321", null);

        Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
        System.out.println("Expected Exception Message for null CPF: " + exception.getMessage());

        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    void ExceptionCpfInvalidLength() {
        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", "Nome Teste", "11987654321", "12345");

        Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
        System.out.println("Expected Exception Message for invalid CPF length: " + exception.getMessage());

        assertEquals("CPF inválido", exception.getMessage());
    }

    @Test
    void FormatCpfCorrectly() {
        String validCpf = "12345678909";
        String expectedFormattedCpf = "123.456.789-09";

        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", "Nome Teste", "11987654321", validCpf);
        Account account = new Account(accountDTO);

        System.out.println("Formatted CPF: " + account.getCpf());
        assertEquals(expectedFormattedCpf, account.getCpf());
    }

    @Test
    void AcceptAlreadyFormattedCpf() {
        String formattedCpf = "123.456.789-09";

        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", "Nome Teste", "11987654321", formattedCpf);
        Account account = new Account(accountDTO);

        System.out.println("Formatted CPF (already formatted): " + account.getCpf());
        assertEquals(formattedCpf, account.getCpf());
    }
}
