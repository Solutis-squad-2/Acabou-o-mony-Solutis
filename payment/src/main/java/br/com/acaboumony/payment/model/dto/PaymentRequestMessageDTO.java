package br.com.acaboumony.payment.model.dto;

import br.com.acaboumony.payment.model.enums.FormaDePagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequestMessageDTO(
        Long orderid,
        BigDecimal valor,
        String cpf,
        FormaDePagamento formaDePagamento,
        String numeroCartao,
        String codigoCartao,
        Integer parcelas,
        String chavePix,
        LocalDateTime dataCadastro
) {}
