package br.com.acaboumony.payment.model.entity;

import br.com.acaboumony.payment.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "payment_tb")
public class Payment {

    @Id
    @GeneratedValue
    private UUID uuid = UUID.randomUUID();

    private String orderId;

    private BigDecimal valor;

    private String cpf;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;

    private LocalDateTime transactionDate;

}
