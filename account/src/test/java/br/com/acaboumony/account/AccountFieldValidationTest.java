package br.com.acaboumony.account;

import br.com.acaboumony.account.exception.AccountException;
import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountFieldValidationTest {

    @Test
    void ExceptionPasswordIsNull() {
        AccountDTO accountDTO = new AccountDTO("teste@domain.com", null, "Nome Teste", "11987654321", "12345678909");

        Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
        System.out.println("Expected Exception Message for null password: " + exception.getMessage());

        assertEquals("Senha deve conter pelo menos 6 caracteres", exception.getMessage());
    }

    @Test
    void ExceptionPasswordTooShort() {
        String shortPassword = "123"; // Less than assumed minimum length of 6
        AccountDTO accountDTO = new AccountDTO("teste@domain.com", shortPassword, "Nome Teste", "11987654321", "12345678909");

        Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
        System.out.println("Expected Exception Message for short password: " + exception.getMessage());

        assertEquals("Senha deve conter pelo menos 6 caracteres", exception.getMessage());
    }

    @Test
    void AcceptValidPassword() {
        String validPassword = "senha123"; // Meets assumed minimum length
        AccountDTO accountDTO = new AccountDTO("teste@domain.com", validPassword, "Nome Teste", "11987654321", "12345678909");

        Account account = new Account(accountDTO);
        System.out.println("Assigned Password: " + account.getPassword());

        assertEquals(validPassword, account.getPassword());
    }

    // Name Tests

    @Test
    void ExceptionNameNull() {
        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", null, "11987654321", "12345678909");

        Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
        System.out.println("Expected Exception Message for null name: " + exception.getMessage());

        assertEquals("O nome não pode estar em branco", exception.getMessage());
    }

    @Test
    void AcceptValidName() {
        String validName = "Nome Teste";
        AccountDTO accountDTO = new AccountDTO("teste@domain.com", "senha123", validName, "11987654321", "12345678909");

        Account account = new Account(accountDTO);
        System.out.println("Assigned Name: " + account.getNome());

        assertEquals(validName, account.getNome());
    }
}
