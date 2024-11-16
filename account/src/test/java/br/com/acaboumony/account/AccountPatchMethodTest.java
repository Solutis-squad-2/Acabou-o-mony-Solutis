package br.com.acaboumony.account;

import br.com.acaboumony.account.exception.AccountException;
import br.com.acaboumony.account.model.dto.GetAccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountPatchMethodTest {

    private Account account;

    @BeforeEach
    void setUp() {
        // Inicializa uma instância de Account com valores padrão
        account = new Account();
        account.setEmail("original@domain.com");
        account.setNome("Nome Original");
        account.setTelefone("(11) 98765-4321");
        account.setCpf("123.456.789-09");
    }

    @Test
    void UpdateEmailOnly() {
        // Cria um GetAccountDTO apenas com o novo email
        GetAccountDTO patchDTO = new GetAccountDTO(null, "novo@domain.com", null, null, null);

        // Aplica o patch
        account.patch(patchDTO);

        // Imprime os valores atualizados
        System.out.println("Updated Account: " + account);

        // Verifica que o email foi atualizado
        assertEquals("novo@domain.com", account.getEmail(), "Email should be updated");

        // Verifica que os demais campos não foram alterados
        assertEquals("Nome Original", account.getNome(), "Name should remain the same");
        assertEquals("(11) 98765-4321", account.getTelefone(), "Phone should remain the same");
        assertEquals("123.456.789-09", account.getCpf(), "CPF should remain the same");
    }

    @Test
    void UpdateNameOnly() {
        // Cria um GetAccountDTO apenas com o novo nome
        GetAccountDTO patchDTO = new GetAccountDTO(null, null, "Novo Nome", null, null);

        // Aplica o patch
        account.patch(patchDTO);

        // Imprime os valores atualizados
        System.out.println("Updated Account: " + account);

        // Verifica que o nome foi atualizado
        assertEquals("Novo Nome", account.getNome(), "Name should be updated");

        // Verifica que os demais campos não foram alterados
        assertEquals("original@domain.com", account.getEmail(), "Email should remain the same");
        assertEquals("(11) 98765-4321", account.getTelefone(), "Phone should remain the same");
        assertEquals("123.456.789-09", account.getCpf(), "CPF should remain the same");
    }

    @Test
    void UpdatePhoneOnly() {
        // Cria um GetAccountDTO apenas com o novo telefone
        GetAccountDTO patchDTO = new GetAccountDTO(null, null, null, "11987654322", null);

        // Aplica o patch
        account.patch(patchDTO);

        // Imprime os valores atualizados
        System.out.println("Updated Account: " + account);

        // Verifica que o telefone foi atualizado e formatado corretamente
        assertEquals("(11) 98765-4322", account.getTelefone(), "Phone should be updated and formatted");

        // Verifica que os demais campos não foram alterados
        assertEquals("original@domain.com", account.getEmail(), "Email should remain the same");
        assertEquals("Nome Original", account.getNome(), "Name should remain the same");
        assertEquals("123.456.789-09", account.getCpf(), "CPF should remain the same");
    }

    @Test
    void UpdateCpfOnly() {
        // Cria um GetAccountDTO apenas com o novo CPF
        GetAccountDTO patchDTO = new GetAccountDTO(null, null, null, null, "98765432100");

        // Aplica o patch
        account.patch(patchDTO);

        // Imprime os valores atualizados
        System.out.println("Updated Account: " + account);

        // Verifica que o CPF foi atualizado e formatado corretamente
        assertEquals("987.654.321-00", account.getCpf(), "CPF should be updated and formatted");

        // Verifica que os demais campos não foram alterados
        assertEquals("original@domain.com", account.getEmail(), "Email should remain the same");
        assertEquals("Nome Original", account.getNome(), "Name should remain the same");
        assertEquals("(11) 98765-4321", account.getTelefone(), "Phone should remain the same");
    }

    @Test
    void UpdateMultipleFields() {
        // Cria um GetAccountDTO com novos valores para email, nome e telefone
        GetAccountDTO patchDTO = new GetAccountDTO(null, "novo@domain.com", "Novo Nome", "11987654322", null);

        // Aplica o patch
        account.patch(patchDTO);

        // Imprime os valores atualizados
        System.out.println("Updated Account: " + account);

        // Verifica que os campos foram atualizados
        assertEquals("novo@domain.com", account.getEmail(), "Email should be updated");
        assertEquals("Novo Nome", account.getNome(), "Name should be updated");
        assertEquals("(11) 98765-4322", account.getTelefone(), "Phone should be updated and formatted");

        // Verifica que o CPF permaneceu o mesmo
        assertEquals("123.456.789-09", account.getCpf(), "CPF should remain the same");
    }
}
