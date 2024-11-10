package br.com.acaboumony.payment.model.dto;

import br.com.acaboumony.payment.model.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponseMessageDTO(
        Long paymentId,
        LocalDateTime paymentDate,
        PaymentStatus paymentStatus
) {
}
