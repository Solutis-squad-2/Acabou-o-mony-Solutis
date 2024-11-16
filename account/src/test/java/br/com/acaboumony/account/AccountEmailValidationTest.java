package br.com.acaboumony.account; // src/test/java/br/com/acaboumony/account/model/entity/AccountEmailValidationTest.java

import br.com.acaboumony.account.exception.AccountException;
import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountEmailValidationTest {

	@Test
	void ExceptionEmailIsNull() {
		AccountDTO accountDTO = new AccountDTO(null, "senha123", "Nome Teste", "11987654321", "12345678909");

		Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
		System.out.println("Expected Exception Message for null email: " + exception.getMessage());

		assertEquals("O email não pode estar em branco", exception.getMessage());
	}

	@Test
	void ExceptionEmailIsEmpty() {
		AccountDTO accountDTO = new AccountDTO("", "senha123", "Nome Teste", "11987654321", "12345678909");

		Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
		System.out.println("Expected Exception Message for empty email: " + exception.getMessage());

		assertEquals("O email não pode estar em branco", exception.getMessage());
	}

	@Test
	void ExceptionEmailIsInvalid() {
		String invalidEmail = "email_invalido";
		AccountDTO accountDTO = new AccountDTO(invalidEmail, "senha123", "Nome Teste", "11987654321", "12345678909");

		Exception exception = assertThrows(AccountException.class, () -> new Account(accountDTO));
		System.out.println("Expected Exception Message for invalid email: " + exception.getMessage());

		assertEquals("O formato do email está incorreto", exception.getMessage());
	}

	@Test
	void AcceptValidEmail() {
		String validEmail = "teste@domain.com";
		AccountDTO accountDTO = new AccountDTO(validEmail, "senha123", "Nome Teste", "11987654321", "12345678909");

		Account account = new Account(accountDTO);
		System.out.println("Assigned Email: " + account.getEmail());

		assertEquals(validEmail, account.getEmail());
	}
}
