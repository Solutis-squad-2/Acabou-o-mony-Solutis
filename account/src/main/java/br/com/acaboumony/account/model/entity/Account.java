package br.com.acaboumony.account.model.entity;

import br.com.acaboumony.account.exception.AccountException;
import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.dto.GetAccountDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.Optional.ofNullable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account_tb")
public class Account implements UserDetails {

    @Id
    @GeneratedValue
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    @Size(max = 15, message = "O telefone deve conter no máximo 11 dígitos")
    private String telefone;

    @Column(nullable = false, unique = true)
    @Size(max = 14, message = "O CPF deve conter no máximo 11 dígitos")
    private String cpf;

    public Account(GetAccountDTO g) {
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new AccountException("O email não pode estar em branco");
        }
        if (!Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$", email)) {
            throw new AccountException("O formato do email está incorreto");
        }
    }

    private void validateNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new AccountException("O nome não pode estar em branco");
        }
    }

    private void validateSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            throw new AccountException("Senha deve conter pelo menos 6 caracteres");
        }
    }

    private String formatarCpf(String cpf) {
        if (cpf == null) {
            throw new AccountException("CPF inválido");
        }
        String cpfSomenteNumeros = cpf.replaceAll("\\D", ""); // Remove todos os caracteres não numéricos
        if (cpfSomenteNumeros.length() == 11) {
            return cpfSomenteNumeros.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
        throw new AccountException("CPF inválido");
    }

    private String formatarTelefone(String telefone) {
        if (telefone == null) {
            throw new AccountException("Telefone inválido");
        }
        String telefoneSomenteNumeros = telefone.replaceAll("\\D", "");
        if (telefoneSomenteNumeros.length() == 11) {
            return telefoneSomenteNumeros.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else if (Pattern.matches("\\(\\d{2}\\) \\d{5}-\\d{4}", telefone)) {
            return telefone;
        }
        throw new AccountException("Telefone inválido");
    }

    public Account(AccountDTO accountDTO) {
        validateEmail(accountDTO.email());
        validateNome(accountDTO.nome());
        validateSenha(accountDTO.senha()); // Valida a senha aqui
        this.setEmail(accountDTO.email());
        this.setNome(accountDTO.nome());
        this.setTelefone(this.formatarTelefone(accountDTO.telefone()));
        this.setCpf(this.formatarCpf(accountDTO.cpf()));
        this.setSenha(accountDTO.senha());
    }

    public void patch(GetAccountDTO accountDTO) {
        ofNullable(accountDTO.email()).ifPresent(email -> {
            validateEmail(email);
            this.setEmail(email);
        });
        ofNullable(accountDTO.nome()).ifPresent(nome -> {
            validateNome(nome);
            this.setNome(nome);
        });
        ofNullable(accountDTO.telefone()).ifPresent(telefone -> {
            this.setTelefone(formatarTelefone(telefone));
        });
        ofNullable(accountDTO.cpf()).ifPresent(cpf -> {
            this.setCpf(formatarCpf(cpf));
        });
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
