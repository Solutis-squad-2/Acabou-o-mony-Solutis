package br.com.acaboumony.account.model.entity;

import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.dto.GetAccountDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
    public Account(GetAccountDTO accountDTO) {
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