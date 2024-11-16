package br.com.acaboumony.account;// src/test/java/br/com/acaboumony/account/model/entity/AccountDTOConversionTest.java

import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.dto.GetAccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountDTOConversionTest {

    @Test
    void ConvertAccountDTOAccount() {
        // Cria um AccountDTO com dados de exemplo
        AccountDTO accountDTO = new AccountDTO(
                "teste@domain.com",
                "senha123",
                "Nome Teste",
                "11987654321",
                "12345678909"
        );

        // Converte o AccountDTO para uma instância de Account
        Account account = new Account(accountDTO);

        // Imprime os valores para verificação
        System.out.println("Account Email: " + account.getEmail());
        System.out.println("Account Senha: " + account.getSenha());
        System.out.println("Account Nome: " + account.getNome());
        System.out.println("Account Telefone: " + account.getTelefone());
        System.out.println("Account CPF: " + account.getCpf());

        // Verifica se os dados foram copiados corretamente
        assertEquals(accountDTO.email(), account.getEmail(), "Email should match");
        assertEquals(accountDTO.senha(), account.getSenha(), "Password should match");
        assertEquals(accountDTO.nome(), account.getNome(), "Name should match");
        assertEquals("(11) 98765-4321", account.getTelefone(), "Phone should be formatted correctly");
        assertEquals("123.456.789-09", account.getCpf(), "CPF should be formatted correctly");
    }

    @Test
    void ConvertAccountToGetAccountDTO() {
        // Cria uma instância de Account com dados de exemplo
        Account account = new Account();
        account.setEmail("teste@domain.com");
        account.setSenha("senha123");
        account.setNome("Nome Teste");
        account.setTelefone("(11) 98765-4321");
        account.setCpf("123.456.789-09");

        // Gera um UUID para simular uma conta completa
        UUID generatedUUID = UUID.randomUUID();
        account.setUuid(generatedUUID);

        // Converte a Account para GetAccountDTO usando o construtor que aceita uma Account
        GetAccountDTO getAccountDTO = new GetAccountDTO(account);

        // Imprime os valores para verificação
        System.out.println("GetAccountDTO UUID: " + getAccountDTO.uuid());
        System.out.println("GetAccountDTO Email: " + getAccountDTO.email());
        System.out.println("GetAccountDTO Nome: " + getAccountDTO.nome());
        System.out.println("GetAccountDTO Telefone: " + getAccountDTO.telefone());
        System.out.println("GetAccountDTO CPF: " + getAccountDTO.cpf());

        // Verifica se os dados foram copiados corretamente
        assertEquals(account.getUuid(), getAccountDTO.uuid(), "UUID should match");
        assertEquals(account.getEmail(), getAccountDTO.email(), "Email should match");
        assertEquals(account.getNome(), getAccountDTO.nome(), "Name should match");
        assertEquals(account.getTelefone(), getAccountDTO.telefone(), "Phone should match");
        assertEquals(account.getCpf(), getAccountDTO.cpf(), "CPF should match");
    }
}
