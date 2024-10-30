package br.com.acaboumony.sendemail.dto;

import java.io.Serializable;

public record ConfirmacaoCodigoDTO(
       String email,
       String codigo
){
}
