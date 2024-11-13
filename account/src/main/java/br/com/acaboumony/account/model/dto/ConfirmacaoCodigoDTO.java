package br.com.acaboumony.account.model.dto;

import java.io.Serializable;


public record ConfirmacaoCodigoDTO (
       String email,
       String codigo
)implements Serializable{
    private static final long serialVersionUID = 1L;

}
