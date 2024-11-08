package pedidos.demo.dto;

import lombok.Getter;
import lombok.Setter;
import pedidos.demo.model.FormaDePagamento;
import pedidos.demo.model.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EmailDTO {
    private String nome;
    private String email;
    private FormaDePagamento formaDePagamento;
    private BigDecimal valor;
    private LocalDateTime dataCadastro;
    private StatusPagamento statusPagamento;
    private LocalDateTime dataProcessamento;

    public EmailDTO(String nome, String email, FormaDePagamento formaDePagamento, BigDecimal valor, LocalDateTime dataCadastro, StatusPagamento statusPagamento, LocalDateTime dataProcessamento) {
        this.nome = nome;
        this.email = email;
        this.formaDePagamento = formaDePagamento;
        this.valor = valor;
        this.dataCadastro = dataCadastro;
        this.statusPagamento = statusPagamento;
        this.dataProcessamento = dataProcessamento;
    }
}
