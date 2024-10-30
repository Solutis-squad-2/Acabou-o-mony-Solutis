package br.com.acaboumony.account.model.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public record ConfirmacaoCodigoDTO (
       String email,
       String codigo
)implements Serializable{
    private static final long serialVersionUID = 1L;

}
