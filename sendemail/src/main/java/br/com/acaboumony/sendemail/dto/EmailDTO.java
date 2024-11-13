package br.com.acaboumony.sendemail.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmailDTO {
    private String email;
    private String nome;
    private String cpf;
    private FormaDePagamento formaDePagamento;
    private int parcelas;
    private String descricaoPedido;
    private BigDecimal valor;
    private PaymentStatus paymentStatus;

}
