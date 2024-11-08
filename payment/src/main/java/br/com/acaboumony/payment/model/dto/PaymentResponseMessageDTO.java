package br.com.acaboumony.payment.model.dto;

import br.com.acaboumony.payment.model.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponseMessageDTO(
        UUID paymentId,
        LocalDateTime paymentDate,
        PaymentStatus paymentStatus
) {
}
