package br.com.acaboumony.account.model.entity;

import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.dto.GetAccountDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account_tb")
public class Account {

    @Id
    @GeneratedValue
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String cpf;

    public Account(AccountDTO accountDTO) {
        this.setEmail(accountDTO.email());
        this.setNome(accountDTO.nome());
        this.setTelefone(accountDTO.telefone());
        this.setCpf(accountDTO.cpf());
    }
    public void patch(GetAccountDTO accountDTO){
        ofNullable(accountDTO.email()).ifPresent(this::setEmail);
        ofNullable(accountDTO.nome()).ifPresent(this::setNome);
        ofNullable(accountDTO.telefone()).ifPresent(this::setTelefone);
        ofNullable(accountDTO.cpf()).ifPresent(this::setCpf);
    }
}