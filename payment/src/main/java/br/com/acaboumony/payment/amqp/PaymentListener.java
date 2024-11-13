package br.com.acaboumony.payment.amqp;

import br.com.acaboumony.payment.model.dto.PaymentRequestMessageDTO;
import br.com.acaboumony.payment.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    private final PaymentService paymentService;

    @Autowired
    public PaymentListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = "pedido.cadastro") // Nome da fila de pagamento configurada pelo serviço Order
    public void processPaymentRequest(PaymentRequestMessageDTO paymentRequest) {
        paymentService.processPayment(paymentRequest);
    }
}

