package br.com.acaboumony.account.model.entity;

import br.com.acaboumony.account.exception.AccountException;
import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.dto.FindUUIDDto;
import br.com.acaboumony.account.model.dto.GetAccountDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private UUID uuid;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "O email não pode estar em branco")
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

    private void validateEmail(String email) {
        if (email == null || !Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$", email)) {
            throw new AccountException("O formato do email está incorreto");
        }
    }

    private String formatarCpf(String cpf) {
        if (cpf != null && cpf.length() == 11) {
            return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
        throw new AccountException("CPF inválido");
    }

    private String formatarTelefone(String telefone) {
        if (telefone != null && telefone.length() == 11) {
            return telefone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        }
        throw new AccountException("Telefone inválido");
    }


    public Account(AccountDTO accountDTO) {
        validateEmail(accountDTO.email());
        this.setEmail(accountDTO.email());
        this.setNome(accountDTO.nome());
        this.setTelefone(this.formatarTelefone(accountDTO.telefone()));
        this.setCpf(this.formatarCpf(accountDTO.cpf()));
    }
    public Account(GetAccountDTO accountDTO) {
        this.setEmail(accountDTO.email());
        this.setNome(accountDTO.nome());
        this.setTelefone(accountDTO.telefone());
        this.setCpf(accountDTO.cpf());
    }
    public Account(FindUUIDDto accountDTO) {
    }
    public void patch(GetAccountDTO accountDTO){
        validateEmail(accountDTO.email());
        ofNullable(accountDTO.email()).ifPresent(this::setEmail);
        ofNullable(accountDTO.nome()).ifPresent(this::setNome);
        ofNullable(accountDTO.telefone())
                .map(this::formatarTelefone)
                .ifPresent(this::setTelefone);

        ofNullable(accountDTO.cpf())
                .map(this::formatarCpf)
                .ifPresent(this::setCpf);
    }

    public String getCodigo(String value){
        return value;
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