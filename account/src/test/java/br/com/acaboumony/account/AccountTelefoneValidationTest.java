package br.com.acaboumony.account; // src/test/java/br/com/acaboumony/account/model/entity/AccountTelefoneValidationTest.java

import br.com.acaboumony.account.exception.AccountException;
import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTelefoneValidationTest {

    @Test
    void FormatPhoneCorrectly() {
        String unformattedPhone = "11987654321";
        String expectedFormattedPhone = "(11) 98765-4321";

        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", "Nome Teste", unformattedPhone, "12345678909");
        Account account = new Account(accountDTO);

        System.out.println("Formatted Phone: " + account.getTelefone());
        assertEquals(expectedFormattedPhone, account.getTelefone());
    }

    @Test
    void AlreadyFormattedPhone() {
        String alreadyFormattedPhone = "(11) 98765-4321";

        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", "Nome Teste", alreadyFormattedPhone, "12345678909");
        Account account = new Account(accountDTO);

        System.out.println("Accepted Formatted Phone: " + account.getTelefone());
        assertEquals(alreadyFormattedPhone, account.getTelefone());
    }

    @Test
    void ExceptionInvalidPhoneLength() {
        String invalidPhone = "12345"; // Less than 11 digits

        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", "Nome Teste", invalidPhone, "12345678909");

        Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
        System.out.println("Expected Exception Message for invalid phone length: " + exception.getMessage());

        assertEquals("Telefone inválido", exception.getMessage());
    }

    @Test
    void ExceptionPhoneExceeds11Digits() {
        String phoneWithMoreThan11Digits = "119876543210"; // 12 digits

        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", "Nome Teste", phoneWithMoreThan11Digits, "12345678909");

        Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
        System.out.println("Expected Exception Message for phone with more than 11 digits: " + exception.getMessage());

        assertEquals("Telefone inválido", exception.getMessage());
    }

    @Test
    void ExceptionPhoneIsNull() {
        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", "Nome Teste", null, "12345678909");

        Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
        System.out.println("Expected Exception Message for null phone: " + exception.getMessage());

        assertEquals("Telefone inválido", exception.getMessage());
    }
}
