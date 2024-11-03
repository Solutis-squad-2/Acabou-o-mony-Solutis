package br.com.acaboumony.payment.model.dto;

import br.com.acaboumony.payment.model.enums.FormaDePagamento;
import br.com.acaboumony.payment.model.enums.PaymentStatus;

import java.math.BigDecimal;

public record PaymentResponseMessageDTO(
        String orderId,
        BigDecimal valor,
        FormaDePagamento formaDePagamento,
        PaymentStatus paymentStatus
) {
}
