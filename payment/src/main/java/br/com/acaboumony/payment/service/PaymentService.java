package br.com.acaboumony.payment.service;

import br.com.acaboumony.payment.amqp.RabbitMQConfig;
import br.com.acaboumony.payment.model.enums.PaymentStatus;
import br.com.acaboumony.payment.model.dto.PaymentRequestMessageDTO;
import br.com.acaboumony.payment.model.dto.PaymentResponseMessageDTO;
import br.com.acaboumony.payment.model.entity.Payment;
import br.com.acaboumony.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final RabbitTemplate rabbitTemplate;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(RabbitTemplate rabbitTemplate, PaymentRepository paymentRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void processPayment(PaymentRequestMessageDTO requestMessage) {
        Payment payment = new Payment();
        payment.setOrderId(requestMessage.orderId());
        payment.setValor(requestMessage.valor());
        payment.setCpf(requestMessage.cpf());
        payment.setTransactionDate(requestMessage.dataCadastro());
        payment.setPaymentDate(LocalDateTime.now());

        // Lógica de validação do pagamento
        boolean isPaymentSuccessful = switch (requestMessage.formaDePagamento()) {
            case CREDITO -> validateCreditCard(requestMessage);
            case DEBITO -> validateDebitCard(requestMessage);
            case PIX -> validatePix(requestMessage);
        };

        // Definindo o status do pagamento
        PaymentStatus status = isPaymentSuccessful ? PaymentStatus.CONFIRMADO : PaymentStatus.CANCELADO;
        payment.setPaymentStatus(status);

        paymentRepository.save(payment);

        sendPaymentStatus(payment.getUuid(), payment.getPaymentDate(), status);
    }

    private boolean validateCreditCard(PaymentRequestMessageDTO requestMessage) {
        return requestMessage.numeroCartao() != null &&
                requestMessage.codigoCartao() != null &&
                requestMessage.parcelas() != null;
    }

    private boolean validateDebitCard(PaymentRequestMessageDTO requestMessage) {
        return requestMessage.numeroCartao() != null &&
                requestMessage.codigoCartao() != null;
    }

    private boolean validatePix(PaymentRequestMessageDTO requestMessage) {
        return "1a1cd635-e894-477c-9ae0-c50b5c1bee53".equals(requestMessage.chavePix());
    }

    public void sendPaymentStatus(UUID paymentId, LocalDateTime paymentDate, PaymentStatus paymentStatus) {
        PaymentResponseMessageDTO message = new PaymentResponseMessageDTO(paymentId, paymentDate, paymentStatus);
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message);
    }
}