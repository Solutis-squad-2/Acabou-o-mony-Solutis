package br.com.acaboumony.payment.repository;

import br.com.acaboumony.payment.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
