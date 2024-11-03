package br.com.acaboumony.payment.consumer;

import br.com.acaboumony.payment.config.RabbitMQConfig;
import br.com.acaboumony.payment.model.dto.PaymentRequestMessageDTO;
import br.com.acaboumony.payment.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMessageListener {

    private final PaymentService paymentService;

    @Autowired
    public PaymentMessageListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receivePaymentRequest(PaymentRequestMessageDTO requestMessage) {
        paymentService.processPayment(requestMessage);
    }
}
