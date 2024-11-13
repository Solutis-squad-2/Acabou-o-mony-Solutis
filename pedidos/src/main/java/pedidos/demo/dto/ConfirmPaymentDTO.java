package pedidos.demo.dto;

import lombok.Getter;
import lombok.Setter;
import pedidos.demo.model.PaymentStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConfirmPaymentDTO {
    private Long paymentId;
    private LocalDateTime paymentDate;
    private PaymentStatus paymentStatus;

}
